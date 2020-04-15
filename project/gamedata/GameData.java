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

/**
 * This class contains all the necessary data in order to play and facilitate offline and online games.
 */
public class GameData implements GameDataSubject{

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

    private int challengeNr;
    private String currentChallenger;

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
    private int gameStatus;

    private int currentChallengeNr = -1;

    private int aiDifficulty = 2;
    private float timeOut = 10;

    private boolean isInGame = false;

    private String message = "";

    /**
     * This is the default constructor for the GameData class.
     */
    public GameData(){
        communicationChannel = new GameCommunicationChannel();
        communicationChannel.setUsername(username);
        observers = new ArrayList<>();
        challenges = new HashMap<>();
        gameMode = "idle";
        gameResult = "";
        inGame = false;
        gameStatus = 0;
        currentOpponent = "the opponent";
    }

    /**
     * This method registers a new observer.
     * @param o the incoming observer.
     */
    public void registerObserver(Observer o){
        observers.add(o);
    }

    /**
     * This method removes an observer which is currently subscribed to this subject.
     * @param o the observer which needs to be removed.
     */
    public void removeObserver(Observer o){
        observers.remove(o);
    }

    /**
     * This method should notifies all observers whenever a change has occurred.
     */
    public void notifyObservers(){
        for(Observer observer : observers){
            observer.update(currentMove, turn, currentChallenger, challengeNr, gameStatus);
        }
    }

    /**
     * This method sets the username, ip address and port of a communication channel. If the username and ip address do
     * not equal an empty string and if the port number is greater than 0 then all values will be updated and set in
     * the corresponding communication channel.
     *
     * @param username the username to be set.
     * @param ipAddress the ip address to be set.
     * @param portNumber the port number to be set.
     */
    public void setUsernameIpAddressAndPort(String username, String ipAddress, int portNumber){
        if(!username.equals(""))
            this.username = username;
            communicationChannel.setUsername(username);
        if(!ipAddress.equals(""))
            communicationChannel.setIpAddress(ipAddress);
        if(portNumber > 0)
            communicationChannel.setPort(portNumber);
    }

    /**
     * This method lets you set the difficulty of the AI.
     *
     * @param aiDifficulty the corresponding difficulty.
     */
    public void setAiDifficulty(int aiDifficulty){
        this.aiDifficulty = aiDifficulty;
    }

    /**
     * This method lets you set the AI time out.
     *
     * @param timeOut the corresponding time out.
     */
    public void setTimeOut(float timeOut){
        this.timeOut = timeOut;
    }

    /**
     * This method will try to establish a connection with the server and fill the necessary lists with data acquired
     * from the server.
     *
     * @return true if the connection has been established successfully, false if the connection couldn't be established.
     */
    public boolean registerToServer(){
        return communicationChannel.startServerAndPrepareLists();
    }

    /**
     * This method lets you set the offline variable. This variable indicates whether the game is being/ the game to be
     * played is played/ going to be played online or offline.
     *
     * @param bool whether the game is offline or online
     */
    public void setOffline(boolean bool){
        this.offline = bool;
    }

    /**
     * This method lets you set the inGame variable, this variable indicates whether a game is being played or not.
     *
     * @param inGame whether the game is being played or not.
     */
    public void setInGame(boolean inGame){
        this.inGame = inGame;
    }

    /**
     * This method lets you set the inLobby variable, this variable indicates whether the player is currently in a
     * lobby or not.
     *
     * @param inLobby whether the player is in a lobby or not.
     */
    public void setInLobby(boolean inLobby){
        this.inLobby = inLobby;
    }

    /**
     * This method lets you set the boardInitialized variable, which indicates whether the board has been initialized.
     *
     * @param boardInitialized whether the board has been initialized.
     */
    public void setBoardInitialized(boolean boardInitialized){
        this.boardInitialized = boardInitialized;
    }

    /**
     * This method lets you set the gameMode variable, which is the game of the game currently being played or the game
     * which is going to be played. (A.k.a the game that is loaded into this class)
     *
     * @param gameMode
     */
    public void setGameMode(String gameMode){
        this.gameMode = gameMode;
    }

    /**
     * This method indicates whether the game is a tournament or not.
     * @param inTournament whether the game is in a tournament/ in the tournament lobby.
     */
    public void setInTournament(boolean inTournament){
        this.inTournament = inTournament;
    }

    /**
     * This methd lets you set the current challenge number.
     * @param currentChallengeNr the current challenge number.
     */
    public void setCurrentChallengeNr(int currentChallengeNr){
        this.currentChallengeNr = currentChallengeNr;
    }

    /**
     * This method lets you set the game AI.
     *
     * @param gameAI the corresponding game AI.
     */
    private void setGameAI(GameAI gameAI){
        this.gameAI = gameAI;
    }

    /**
     * This method lets you set the game logic.
     *
     * @param gameLogic the corresponding game logic.
     */
    private void setGameLogic(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    /**
     * This method lets you set the game board logic of the game that is going to be played.
     *
     * @param gameBoardLogic the game board logic of the corresponding game.
     */
    private void setGameBoardLogic(GameBoardLogic gameBoardLogic){
        this.gameBoardLogic = gameBoardLogic;
        gameLogic.setBoard(gameBoardLogic);
    }

    /**
     * This method lets log out from the server.
     */
    public void logoutFromServer(){
        communicationChannel.logout();
    }

    /**
     * This method returns the whether the game is played offline or online.
     *
     * @return true if offline, false if online
     */
    public boolean getOffline(){
        return offline;
    }

    /**
     * This method lets you get the current game.
     *
     * @return the current game.
     */
    public String getCurrentGame(){
        return currentGame;
    }

    /**
     * This method returns the username, with which the player logins.
     *
     * @return the username.
     */
    public String getUsername(){
        return username;
    }

    /**
     * This method returns the number of wins.
     *
     * @return the number of wins.
     */
    public int getWins(){
        return wins;
    }

    /**
     * This method lets you return the number of losses.
     *
     * @return the number of losses.
     */
    public int getLosses(){
        return losses;
    }

    /**
     * This method lets you return the number of draws.
     *
     * @return the number of draws.
     */
    public int getDraws(){
        return draws;
    }

    /**
     * This method returns whether the game is currently in a tournament.
     *
     * @return whether the game is in a tournament or not.
     */
    public boolean getInTournament(){
        return inTournament;
    }

    /**
     * This method returns the current game mode.
     *
     * @return the current game mode.
     */
    public String getGameMode(){
        return gameMode;
    }

    /**
     * This method returns the current game logic of the game being played/ that is going to be played.
     *
     * @return the corresponding game logic.
     */
    public GameLogic getGameLogic(){
        return gameLogic;
    }

    /**
     * This method returns whether the board is initialized or not.
     *
     * @return whether the board is initialized or not.
     */
    public boolean getBoardInitialized(){
        return boardInitialized;
    }

    /**
     * This method returns the current set of players whom are online on the server.
     *
     * @return the player which contains all players currently online.
     */
    public HashSet<String> getPlayerSet(){
        return communicationChannel.getPlayerSet();
    }

    /**
     * This method formats the game result.
     *
     * @return a win, lose, or draw formatted message.
     */
    public String getFormattedGameResult(){
        if (gameResult.contains("WIN")){
            return "You just won a game of " + currentGame + " against " + currentOpponent + "!";
        }
        else if(gameResult.contains("LOSE")){
            return "You just lost a game of " + currentGame + " against " + currentOpponent + "!";
        }
        else if(gameResult.contains("DRAW")){
            return "It's a draw! You played against " + currentOpponent + "!";
        }
        else{
            return "";
        }
    }

    /**
     * This method lets you challenge a player.
     *
     * @param player the play the user wants to challenge.
     */
    public void challengePlayer(String player){
        communicationChannel.challenge(player, currentGame);
    }

    /**
     * This method lets you subscribe to the game that is currently loaded.
     */
    public void subscribeToGame(){
        communicationChannel.subscribe(currentGame);
    }

    /**
     * This method lets you notify the observers with a status.
     *
     * @param status one of the status defined in the documentation.
     */
    private void notifyObserversGameStatus(int status){
        gameStatus = status;
        notifyObservers();
        gameStatus = 0;
    }

    /**
     * This method is called once you connect with the server.
     */
    public void sitInServerLobby(){
        AntiTimeout antiTimeout = new AntiTimeout(communicationChannel, isInGame);
        Thread thread = new Thread(antiTimeout);
        thread.start();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                gameMode = "idle";
                while (inLobby) {
                    if(communicationChannel.getInputReady() && !communicationChannel.getAcquiringACertainSet() && !inGame && !inTournament) {
                        message = communicationChannel.readFormattedLine();
                        if(message.contains(currentGame) && message.contains("challenged you")) {
                            currentChallenger = message.substring(message.lastIndexOf('{') + 1, message.indexOf('}'));
                            challengeNr = Integer.parseInt(message.substring(message.indexOf('*') + 1, message.lastIndexOf('*')));
                            challenges.put(challengeNr, currentChallenger + ". Challenge number is: " + challengeNr);
                            notifyObserversGameStatus(4);
                        }
                        else if(message.contains("CHALLENGE CANCELLED")){
                            challengeNr = Integer.parseInt(message.substring(52));
                            notifyObserversGameStatus(6);
                            challenges.remove(challengeNr);
                        }
                    }
                    if((gameMode.contains("challenged a player") && message.contains("PLAYER TO START") || (gameMode.contains("subscribed") && message.contains("PLAYER TO START")))){
                        inGame = true;
                        currentMove = -1;
                        notifyObserversGameStatus(1);
                        while(inGame) {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            playWithOnlineGameLogic();
                        }
                        boardInitialized = false;
                        gameMode = "Idle";
                        message = "";
                    }
                    else if(gameMode.contains("got challenged")){
                        communicationChannel.challengeAccept(currentChallengeNr);
                        inGame = true;
                        currentMove = -1;
                        notifyObserversGameStatus(1);
                        System.out.println("here");
                        while(inGame) {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            playWithOnlineGameLogic();
                        }
                        challenges.remove(currentChallengeNr);
                        notifyObserversGameStatus(6);
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

    /**
     * This method lets you start an online game.
     */
    public void startOnlineGame() {
        Thread t = new Thread(new Runnable(){
            @Override
            public void run(){
                gameBoardLogic.resetBoard();
                notifyObserversGameStatus(1);
                while(inTournament){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    playWithOnlineGameLogic();
                }
                notifyObserversGameStatus(2);
            }
        });
        t.start();
    }

    /**
     * This method is the method used for playing online games.
     *
     * @return true when a turn has been completed.
     */
    private synchronized boolean playWithOnlineGameLogic(){
        if(communicationChannel.getInputReady() && !inTournament)
            message = communicationChannel.readFormattedLine();
        else if(communicationChannel.getInputReady()){
            message = communicationChannel.readFormattedLine();
        }

        if(message.isBlank() || message.isEmpty()){
            return true;
        } else {
            System.out.println(message);
        }

        if (message.contains("PLAYER TO START")){
            isInGame = true;
            currentOpponent = message.substring(message.indexOf("[") + 1, message.lastIndexOf( "]"));
            System.out.println(currentOpponent);
            if(message.contains("OPPONENT")){
                player = 1;
            } else {
                player = 2;
            }
        } else if (message.contains("YOUR TURN")) {
            System.out.println("Our turn");
            isInGame = true;
            // For some reason the server sometimes doesn't send the message which player starts the game this only
            // seems to happen when our player is the one that starts, so we can set the player to 2 if that happens.
            if(player == 0){
                player = 2;
            }
            turn = player;
            int move = gameAI.getBestMove(gameBoardLogic, player);
            communicationChannel.move(move);
            gameLogic.doMove(move, player);
            currentMove = move;
            notifyObservers();
            System.out.println("We made move : " + move + " and we are player " + player);
        } else if (message.contains("previous move") && !message.contains("YOU")) {
            if(player == 0){
                player = 1;
            }
            System.out.println("Opponents turn");
            turn = 3 - player;
            int opponentHisMove;
            String s;
            try {
                s = message.substring(message.length() - 2);
                opponentHisMove = Integer.parseInt(s);
            } catch (java.lang.NumberFormatException e) {
                System.out.print("");
                s = message.substring(message.length() - 1);
                opponentHisMove = Integer.parseInt(s);
            }
            currentMove = opponentHisMove;
            System.out.println("Opponent's move: " + opponentHisMove);
            gameLogic.doMove(opponentHisMove, 3 - player);
            notifyObservers();
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
            isInGame = false;
            currentMove = -1;
            gameBoardLogic.resetBoard();
            player = 0;
            gameResult = message;
            if(!inTournament) {
                inGame = false;
                notifyObserversGameStatus(2);
            }
            else{
                notifyObserversGameStatus(3);
            }
        }
        return true;
    }

    /**
     * This method initializes and loads a game into this class.
     *
     * @param gameName the game to be loaded.
     */
    public void initializeGame(String gameName){
        this.currentGame = gameName;
        if(currentGame.equals("Tic-tac-toe")){
            initializeTicTacToe();
        }
        else if(currentGame.equals("Reversi")){
            initializeReversi();
        }
        gameAI.setMaxTime(timeOut);
        gameAI.setDifficulty(aiDifficulty);
    }

    /**
     * This method is used to play an offline game.
     *
     * @param move the current player move.
     * @param turn the current turn.
     * @return true if the player move is correct, false if the move is invalid.
     */
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

    /**
     * This method gets the best move from the AI.
     *
     * @return true if either the player has set the last move or when the current move is valid.
     */
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

    /**
     * This method initializes and loads Reversi into this class.
     */
    private void initializeReversi(){
        currentGame = "Reversi";
        setGameLogic(new ReversiGameLogic());
        setGameBoardLogic(new ReversiBoardLogic());
        setGameAI(new ReversiMinimaxStrategy());
        currentMove = -1;
        notifyObservers();
    }

    /**
     * This method initializes and loads Tic Tac Toe into this class.
     */
    private void initializeTicTacToe(){
        currentGame = "Tic-tac-toe";
        setGameLogic(new TicTacToeGameLogic());
        setGameBoardLogic(new TicTacToeBoardLogic());
        setGameAI(new TicTacToeMinimaxStrategy());
        currentMove = -1;
        notifyObservers();
    }
}
