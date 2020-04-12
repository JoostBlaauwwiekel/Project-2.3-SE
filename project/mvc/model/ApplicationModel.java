package project.mvc.model;

import project.gamedata.GameData;
import project.gamedata.Observer;
import project.mvc.view.ObserverView;

import java.util.ArrayList;
import java.util.HashMap;

public class ApplicationModel implements ModelInterface, Observer{

    private ArrayList<ObserverView> modelObservers;
    private GameData gameData;

    private int currentMove;
    private int turn;

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

    public void update(int currentMove, int turn){
        this.currentMove = currentMove;
        this.turn = turn;
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
