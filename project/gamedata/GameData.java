package project.gamedata;

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

    private String serverIpAddress = "145.33.225.170";
    private String username = "BITM";

    private ArrayList<Observer> observers;
    private HashMap<Integer, String> challenges;

    private CommunicationChannel communicationChannel;
    private GameAI gameAI;
    private GameLogic gameLogic;
    private GameBoardLogic gameBoardLogic;

    private String gameResult;

    private int wins;
    private int losses;
    private int draws;

    private String currentOpponent;
    private String currentGame;
    private int currentMove;
    private int turn;
    private int player;

    private String gameMode;

    private boolean inTournament;
    private boolean offline;
    private boolean inGame;
    private boolean inLobby;
    private boolean boardInitialized = false;
    private int gameFinished;

    private int currentChallengeNr = -1;

    public GameData(){
        communicationChannel = new GameCommunicationChannel();
        communicationChannel.setUsername(username);
        observers = new ArrayList<>();
        challenges = new HashMap<>();
        gameMode = "idle";
        inGame = false;
        gameFinished = 0;
    }

    public void registerObserver(Observer o){
        observers.add(o);
    }

    public void removeObserver(Observer o){
        observers.remove(o);
    }

    public void notifyObservers(){
        for(Observer observer : observers){
            observer.update(currentMove, turn, gameFinished);
        }
    }

    public void setUsernameIpAddressAndPort(String username, String ipAddress, int portnumber){
        if(!username.equals(""))
            this.username = username;
            communicationChannel.setUsername(username);
        if(!ipAddress.equals(""))
            communicationChannel.setIpAddress(ipAddress);
        if(portnumber > 0)
            communicationChannel.setPort(portnumber);
    }

    public boolean registerToServer(){
        return communicationChannel.startServerAndPrepareLists();
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

    public void setBoardInitialized(boolean boardInitialized){
        this.boardInitialized = boardInitialized;
    }

    public void setGameMode(String gameMode){
        this.gameMode = gameMode;
    }

    public void setInTournament(boolean inTournament){
        this.inTournament = inTournament;
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

    public String getUsername(){
        return username;
    }

    public String getGameResult(){
        return gameResult;
    }

    public int getWins(){
        return wins;
    }

    public int getLosses(){
        return losses;
    }

    public int getDraws(){
        return draws;
    }

    private void setGameAI(GameAI gameAI){
        this.gameAI = gameAI;
    }

    private void setGameLogic(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    public String getGameMode(){
        return gameMode;
    }

    private void setGameBoardLogic(GameBoardLogic gameBoardLogic){
        this.gameBoardLogic = gameBoardLogic;
        gameLogic.setBoard(gameBoardLogic);
    }

    public String getFormattedGameResult(){
        if (gameResult.contains("WIN")){
            return "You just won a game of " + currentGame + " against " + currentOpponent + "!";
        }
        else if(gameResult.contains("LOSE")){
            return "You just lost a game of " + currentGame + " against " + currentOpponent + "!";
        }
        else if(gameResult.contains("DRAW")){
            return "It's a draw! You played against " + currentOpponent;
        }
        else{
            return "";
        }
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

    public boolean getBoardInitialized(){
        return boardInitialized;
    }

    public void sitInServerLobby(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String message = "";
                String currentChallenger;
                int challengeNr;
                gameMode = "idle";
                while (inLobby) {
                    if(communicationChannel.getInputReady() && !communicationChannel.getAcquiringACertainSet() && !inGame && !inTournament) {
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
                    if((gameMode.contains("challenged a player") && message.contains("PLAYER TO START") || (gameMode.contains("subscribed") && message.contains("PLAYER TO START")))){
                        inGame = true;
                        currentMove = -1;
                        gameFinished = 1;
                        notifyObservers();
                        gameFinished = 0;
                        while(inGame) {
                            playWithOnlineGameLogic(message);
                        }
                        boardInitialized = false;
                        gameMode = "Idle";
                        message = "";
                    }
                    else if(gameMode.contains("got challenged")){
                        communicationChannel.challengeAccept(currentChallengeNr);
                        inGame = true;
                        currentMove = -1;
                        gameFinished = 1;
                        notifyObservers();
                        gameFinished = 0;
                        while(inGame) {
                            playWithOnlineGameLogic(message);
                        }
                        challenges.remove(currentChallengeNr);
                        currentChallengeNr = -1;
                        boardInitialized = false;
                        gameMode = "Idle";
                        message = "";
                    }
                }
            }
        });
        t.start();
    }

    public void startOnlineGame() {
        Thread t = new Thread(new Runnable(){
            @Override
            public void run(){
                String message = "";
                gameBoardLogic.resetBoard();
                while(inTournament){
                    playWithOnlineGameLogic(message);
                }
                gameFinished = 2;
                notifyObservers();
                gameFinished = 0;
            }
        });
        t.start();
    }

    private void playWithOnlineGameLogic(String message){
        if(communicationChannel.getInputReady() && !inTournament)
            message = communicationChannel.readFormattedLine();
        else{
            message = communicationChannel.readFormattedLine();
        }
        if (message.contains("PLAYER TO START")){
            currentOpponent = message.substring(message.indexOf("[") + 1, message.lastIndexOf( "]"));
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
            for(int moveeee : gameLogic.getMoves(player)){
                System.out.println("move: " + moveeee);
            }
            currentMove = move;
            System.out.println("move " + move);
            //gameBoardLogic.printBoard();
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
            System.out.print("");
            System.out.println("Opponent's move: " + opponentHisMove);
            gameLogic.doMove(opponentHisMove, 3 - player);
            System.out.println("print current board: ");
            //gameBoardLogic.printBoard();
        } else if (message.contains("LOSE") || message.contains("WIN") || message.contains("DRAW")) {
            if(message.contains("LOSE")){
                losses++;
            }
            else if(message.contains("WIN")){
                wins++;
            }
            else{
                draws++;
            }
            currentMove = -1;
            gameBoardLogic.resetBoard();
            System.out.println(inTournament);
            gameResult = message;
            if(!inTournament) {
                inGame = false;
                gameFinished = 2;
                notifyObservers();
                gameFinished = 0;
            }
            else{
                gameFinished = 3;
                notifyObservers();
                gameFinished = 0;
            }
        }
        notifyObservers();
    }

    public void initializeGame(String gameName){
        this.currentGame = gameName;
        if(currentGame.equals("Tic-tac-toe")){
            initializeTicTacToe();
        }
        else if(currentGame.equals("Reversi")){
            initializeReversi();
        }
    }

    public boolean playOfflineGame(int move, int turn){
        if(gameLogic.isValid(move, turn) && gameLogic.gameOver() == 0){
            if(turn == 1){
                gameLogic.doMove(move, turn);
                notifyObservers();
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

    private void initializeTicTacToe(){
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