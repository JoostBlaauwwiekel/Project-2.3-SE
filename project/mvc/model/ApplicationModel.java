package project.mvc.model;

import project.gamedata.GameData;
import project.gamedata.Observer;
import project.mvc.view.ObserverView;

import java.util.ArrayList;

public class ApplicationModel implements ModelInterface, Observer{

    private ArrayList<ObserverView> modelObservers;
    private GameData gameData;

    private int currentMove;
    private int turn;
    private String currentChallenger;
    private int currentChallengerNr;
    private int gameStatus;

    public ApplicationModel(){
        modelObservers = new ArrayList<>();
        gameData = new GameData();
        gameData.registerObserver(this);
    }

    public boolean isOffline(){
        return gameData.getOffline();
    }

    public int getCurrentMove(){
        return currentMove;
    }

    public String getCurrentGame(){
        return gameData.getCurrentGame();
    }

    public int getCurrentTurn(){
        return turn;
    }

    public GameData getGameData(){
        return gameData;
    }

    public int getGameStatus(){
        return gameStatus;
    }

    public String getCurrentChallenger(){
        return currentChallenger;
    }

    public int getCurrentChallengerNr(){
        return currentChallengerNr;
    }

    public void update(int currentMove, int turn, String currentChallenger, int currentChallengerNr, int gameStatus){
        this.currentMove = currentMove;
        this.turn = turn;
        this.currentChallenger = currentChallenger;
        this.currentChallengerNr = currentChallengerNr;
        this.gameStatus = gameStatus;
        notifyObservers();
    }

    public void registerObserver(ObserverView o){
        modelObservers.add(o);
    }

    public void removeObserver(ObserverView o){
        modelObservers.remove(o);
    }

    public void notifyObservers(){
        for(ObserverView observer : modelObservers){
            observer.update();
        }
    }

}
