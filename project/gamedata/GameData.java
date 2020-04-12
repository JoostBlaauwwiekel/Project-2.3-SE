package project.gamedata;

import javafx.application.Platform;
import project.gameframework.CommunicationChannel;
import project.gameframework.GameAI;
import project.gameframework.GameBoardLogic;
import project.gameframework.GameLogic;
import project.gamemodules.reversigame.ReversiBoardLogic;
import project.gamemodules.reversigame.ReversiGameLogic;
import project.gamemodules.reversigame.ReversiMinimaxStrategy;
import project.gamemodules.tictactoegame.TicTacToeBoardLogic;
import project.gamemodules.tictactoegame.TicTacToeGameLogic;
import project.gamemodules.tictactoegame.TicTacToeMinimaxStrategy;

import java.util.*;

public class GameData implements GameDataSubject{

    private static final String SERVER_IP_ADDRESS = "145.33.225.170";
    private String currentPlayer;

    private ArrayList<Observer> observers;
    private HashMap<Integer, String> challenges;

    private CommunicationChannel communicationChannel;
    private GameAI gameAI;
    private GameLogic gameLogic;
    private GameBoardLogic gameBoardLogic;

    private String currentGame;
    private int currentMove;
    private int turn;
    private int player;

    private boolean inTournament;
    private boolean offline;
    private boolean inGame;
    private boolean inLobby;

    private boolean challengeAccepted;
    private boolean challengedPlayer;
    private boolean subscribed;
    private int currentChallengeNr = -1;
    private int reached = 0;

    public GameData(){
        communicationChannel = new GameCommunicationChannel();
        observers = new ArrayList<>();
        challenges = new HashMap<>();
    }

    public void registerObserver(Observer o){
        observers.add(o);
    }

    public void removeObserver(Observer o){
        observers.remove(o);
    }

    public void notifyObservers(){
        for(Observer observer : observers){
            observer.update(currentMove, turn);
        }
    }

    public void registerToServer(String username, String ipAddress){
        currentPlayer = username;
        communicationChannel.setUsername(username);
        communicationChannel.setIpAddress("localhost");
        communicationChannel.startServerAndPrepareLists();
    }

    public void setOffline(boolean bool){
        this.offline = bool;
    }

    public void setInGame(boolean inGame){
        this.inGame = inGame;
    }

    public void setInLobby(boolean inLobby){
        this.inLobby = inLobby;
    }

    public void setSubscribed(boolean subscribed){
        this.subscribed = subscribed;
    }

    public void setInTournament(boolean inTournament){
        this.inTournament = inTournament;
    }

    public void setChallengedPlayer(boolean challengedPlayer){
        this.challengedPlayer = challengedPlayer;
    }

    public void setChallengeAccepted(boolean challengeAccepted){
        this.challengeAccepted = challengeAccepted;
    }

    public void setCurrentChallengeNr(int currentChallengeNr){
        this.currentChallengeNr = currentChallengeNr;
    }

    public void logoutFromServer(){
        communicationChannel.logout();
    }

    public boolean getOffline(){
        return offline;
    }

    public String getCurrentGame(){
        return currentGame;
    }

    public String getCurrentPlayer(){
        return currentPlayer;
    }

    private void setGameAI(GameAI gameAI){
        this.gameAI = gameAI;
    }

    private void setGameLogic(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    public GameAI getGameAIStrategy(){
        return gameAI;
    }

    private void setGameBoardLogic(GameBoardLogic gameBoardLogic){
        this.gameBoardLogic = gameBoardLogic;
        gameLogic.setBoard(gameBoardLogic);
    }

    public void challengePlayer(String player){
        communicationChannel.challenge(player, currentGame);
    }

    public void subscribeToGame(){
        communicationChannel.subscribe(currentGame);
    }

    public GameLogic getGameLogic(){
        return gameLogic;
    }

    public void sitInServerLobby(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                reached++;
                String message;
                String currentChallenger;
                int challengeNr;
                System.out.println("reached is: " + reached);
                while (inLobby) {
                    if(communicationChannel.getInputReady() && !communicationChannel.getAcquiringACertainSet() && !inGame) {
                        message = communicationChannel.readFormattedLine();
                        if(message.contains(currentGame) && message.contains("challenged you")) {
                            currentChallenger = message.substring(message.lastIndexOf('{') + 1, message.indexOf('}'));
                            challengeNr = Integer.parseInt(message.substring(message.indexOf('*') + 1, message.lastIndexOf('*')));
                            challenges.put(challengeNr, currentChallenger + ". Challenge number is: " + challengeNr);
                            notifyObservers();
                        }
                        else if(message.contains("CHALLENGE CANCELLED")){
                            challengeNr = Integer.parseInt(message.substring(52));
                            challenges.remove(challengeNr);
                            notifyObservers();
                        }
                    }
                    if(inGame){
                        while(inGame){
                            try{
                                Thread.sleep(1000);
                            }
                            catch(InterruptedException ie){

                            }
                        }
                    }
                    if(challengeAccepted && currentChallengeNr > 0){
                        communicationChannel.challengeAccept(currentChallengeNr);
                        inGame = true;
                        startOnlineGame();
                        challenges.remove(currentChallengeNr);
                        challengeAccepted = false;
                        currentChallengeNr = -1;
                    }
                    else if(challengedPlayer && !challengeAccepted){
                        inGame = true;
                        startOnlineGame();
                    }
                    else if(subscribed && !challengeAccepted){
                        inGame = true;
                        startOnlineGame();
                    }
                }
            }
        });
        Platform.runLater(() -> t.start());
    }

    public synchronized void startOnlineGame() {
        Thread t = new Thread(new Runnable(){
            @Override
            public void run(){
                String message = "";
                gameBoardLogic.resetBoard();
                while(inGame){
                    if(communicationChannel.getInputReady())
                        message = communicationChannel.readFormattedLine();
                    if (message.contains("PLAYER TO START")){
                        if(message.contains("OPPONENT")){
                            player = 1;
                        } else {
                            player = 2;
                        }
                    } else if (message.contains("YOUR TURN")) {
                        turn = player;
                        int move = gameAI.getBestMove(gameBoardLogic, player);
                        communicationChannel.move(move);
                        gameLogic.doMove(move, player);
                        currentMove = move;
                        notifyObservers();
                        System.out.println("HERE Our move " + move);
                        gameBoardLogic.printBoard();
                    } else if (message.contains("previous move") && !message.contains("YOU")) {
                        turn = 3 - player;
                        System.out.println("3 - player: " + (3 - player));
                        int opponentHisMove;
                        String s;
                        try {
                            s = message.substring(message.length() - 2);
                            opponentHisMove = Integer.parseInt(s);
                        } catch (java.lang.NumberFormatException e) {
                            // Else get the last char of the string
                            System.out.print("");
                            s = message.substring(message.length() - 1);
                            opponentHisMove = Integer.parseInt(s);
                        }
                        currentMove = opponentHisMove;
                        notifyObservers();
                        System.out.print("");
                        System.out.println("Opponent's move: " + opponentHisMove);
                        gameLogic.doMove(opponentHisMove, 3 - player);
                        System.out.println("print current board: ");
                        gameBoardLogic.printBoard();
                    } else if (message.contains("LOSE") || message.contains("WIN") || message.contains("DRAW")) {
                        System.out.println(message);
                        gameBoardLogic.resetBoard();
                        if(!inTournament)
                            inGame = false;
                    }
                }
            }
        });
        t.start();
    }

    public void initializeGame(String gameName){
        this.currentGame = gameName;
        if(currentGame.equals("Tic-tac-toe")){
            intitializeTicTacToe();
        }
        else if(currentGame.equals("Reversi")){
            initializeReversi();
        }
    }

    public boolean playOfflineGame(int move, int turn){
        if(gameLogic.isValid(move, turn) && gameLogic.gameOver() == 0){
            if(turn == 1){
                gameLogic.doMove(move, turn);
            }
            return getAndDoBestAIMove();
        }
        else if(move == -1){
            return getAndDoBestAIMove();
        }
        else{
            return false;
        }
    }

    private boolean getAndDoBestAIMove(){
        int pos = gameAI.getBestMove(gameBoardLogic, 2);
        if(pos < 0){
            return true;
        }
        gameLogic.doMove(pos, 2);
        currentMove = pos;
        notifyObservers();
        return true;
    }

    private void initializeReversi(){
        currentGame = "Reversi";
        setGameLogic(new ReversiGameLogic());
        setGameBoardLogic(new ReversiBoardLogic());
        setGameAI(new ReversiMinimaxStrategy());
        currentMove = -1;
        notifyObservers();
    }

    private void intitializeTicTacToe(){
        currentGame = "Tic-tac-toe";
        setGameLogic(new TicTacToeGameLogic());
        setGameBoardLogic(new TicTacToeBoardLogic());
        setGameAI(new TicTacToeMinimaxStrategy());
        currentMove = -1;
        notifyObservers();
    }

    public HashSet<String> getPlayerSet(){
        return communicationChannel.getPlayerSet();
    }

    public HashMap<Integer, String> getChallenges(){
        return challenges;
    }

}
