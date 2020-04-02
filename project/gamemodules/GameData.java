package project.gamemodules;

import project.gameframework.CommunicationChannel;
import project.gameframework.GameBoardLogic;
import project.gameframework.GameLogic;
import project.gamemodules.reversigame.ReversiGameLogic;
import project.gamemodules.tictactoegame.TicTacToeGameLogic;

import java.io.IOException;
import java.util.*;

public class GameData {

    private static final String SERVER_IP_ADDRESS = "145.33.225.170";
    private Map<String, GameLogic> gameMap;
    private CommunicationChannel communicationChannel;

    public GameData(){
        initializeGameLogicList();
        communicationChannel = new GameCommunicationChannel();
    }

    private void initializeGameLogicList(){
        this.gameMap = new HashMap<>();
        this.gameMap.put("Tic-tac-toe", new TicTacToeGameLogic());
        this.gameMap.put("Reversi", new ReversiGameLogic());
    }

    private void registerToServer(String username, boolean playLocal){
        communicationChannel.setUsername(username);
        if(playLocal)
            communicationChannel.setIpAddress("localhost");
        else
            communicationChannel.setIpAddress(SERVER_IP_ADDRESS);
    }

    public GameLogic getGame(String gameName){
        return gameMap.get(gameName);
    }

    public void playGame(String username, String gameName, boolean playLocal){
        GameLogic game = gameMap.get(gameName);
        GameBoardLogic board = game.getBoard();

        registerToServer(username, playLocal);
        communicationChannel.startServerAndPrepareLists();

        System.out.println("Wait for the server to respond.... (don't forgot to subscribe with a puTTy client!)");
        System.out.println("Wait until 'move' shows up.");
        try {
            while (game.gameOver() < 0) {
                if (communicationChannel.readLines().contains("YOURTURN")) {
                    //TODO implement the game logic, ensure that the server receives a move.

                    // use: communicationChannel.move();
                }
            }
        }
        catch(IOException ie){
            ie.printStackTrace();
        }
    }
}
