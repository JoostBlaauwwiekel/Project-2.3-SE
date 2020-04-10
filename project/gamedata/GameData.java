package project.gamedata;

import project.gameframework.CommunicationChannel;
import project.gameframework.GameAI;
import project.gameframework.GameBoardLogic;
import project.gameframework.GameLogic;

import java.io.IOException;
import java.util.*;

public class GameData implements GameDataSubject{

    private static final String SERVER_IP_ADDRESS = "145.33.225.170";

    private ArrayList<Observer> observers;

    private CommunicationChannel communicationChannel;
    private GameAI gameAIStrategy;
    private GameLogic gameLogic;

    public GameData(){
        communicationChannel = new GameCommunicationChannel();
        observers = new ArrayList<>();
    }

    public void registerObserver(Observer o){
        observers.add(o);
    }

    public void removeObserver(Observer o){
        observers.remove(o);
    }

    public void notifyObservers(){
        for(Observer observer : observers){
            observer.update();
        }
    }

    public void registerToServer(String username, boolean playLocal){
        communicationChannel.setUsername(username);
        if(playLocal)
            communicationChannel.setIpAddress("localhost");
        else
            communicationChannel.setIpAddress(SERVER_IP_ADDRESS);
    }

    public void setGameAIStrategy(GameAI gameAIStrategy){
        this.gameAIStrategy = gameAIStrategy;
    }

    public void setGameLogic(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    public GameAI getGameAIStrategy(){
        return gameAIStrategy;
    }

    public GameLogic getGameLogic(){
        return gameLogic;
    }

    public void playGame(String username, String gameName, boolean playLocal){
        GameBoardLogic board = gameLogic.getBoard();

        registerToServer(username, playLocal);
        communicationChannel.startServerAndPrepareLists();

        System.out.println("Wait for the server to respond.... (don't forgot to subscribe with a puTTy client!)");
        System.out.println("Wait until 'move' shows up.");
        try {
            while (gameLogic.gameOver() < 0) {
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
