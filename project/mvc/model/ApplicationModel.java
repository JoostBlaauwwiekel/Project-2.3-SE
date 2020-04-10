package project.mvc.model;

import project.gamedata.GameData;
import project.gamedata.Observer;
import project.mvc.view.ObserverView;

import java.util.ArrayList;

public class ApplicationModel implements ModelInterface, Observer{

    private ArrayList<ObserverView> modelObservers;
    private GameData gameData;

    public ApplicationModel(){
        modelObservers = new ArrayList<>();
        gameData = new GameData();
        gameData.registerObserver(this);
    }

    public GameData getGameData(){
        return gameData;
    }

    public void update(){
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
