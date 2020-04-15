package project.mvc.model;

import project.gamedata.GameData;
import project.gamedata.Observer;
import project.mvc.view.ObserverView;

import java.util.ArrayList;

/**
 * This class contains some of the game data, but most of the game data necessary to update the UI.
 */
public class ApplicationModel implements ModelInterface, Observer{

    private ArrayList<ObserverView> modelObservers;
    private GameData gameData;

    private int currentMove;
    private int turn;
    private String currentChallenger;
    private int currentChallengerNr;
    private int gameStatus;

    /**
     * This is de default constructor for the ApplicationModel class.
     */
    public ApplicationModel(){
        modelObservers = new ArrayList<>();
        gameData = new GameData();
        gameData.registerObserver(this);
    }

    /**
     * This method registers a new observer.
     * @param o the incoming observer.
     */
    public void registerObserver(ObserverView o){
        modelObservers.add(o);
    }

    /**
     * This method removes an observer which is currently subscribed to this subject.
     * @param o the observer which needs to be removed.
     */
    public void removeObserver(ObserverView o){
        modelObservers.remove(o);
    }

    /**
     * This method should notifies all observers whenever a change has occurred.
     */
    public void notifyObservers(){
        for(ObserverView observer : modelObservers){
            observer.update();
        }
    }

    /**
     * This method returns the boolean isOffline in the gameData class.
     *
     * @return true if the game is offline, false if the game is online.
     */
    public boolean isOffline(){
        return gameData.getOffline();
    }

    /**
     * This method returns the current move either done by the first player or the opponent.
     *
     * @return the current move.
     */
    public int getCurrentMove(){
        return currentMove;
    }

    /**
     * This method returns the game currently being played or the game that has been loaded.
     *
     * @return the game being played or the game that has been loaded.
     */
    public String getCurrentGame(){
        return gameData.getCurrentGame();
    }

    /**
     * This method returns the current turn.
     *
     * @return the current turn.
     */
    public int getCurrentTurn(){
        return turn;
    }

    /**
     * This method returns the GameData class.
     *
     * @return GameData class.
     */
    public GameData getGameData(){
        return gameData;
    }

    /**
     * This method returns the current status the game. There are 6 possible options which are documented in the
     * documentation.
     *
     * @return the current game status.
     */
    public int getGameStatus(){
        return gameStatus;
    }

    /**
     * This method returns the current challenger.
     *
     * @return the current challenger.
     */
    public String getCurrentChallenger(){
        return currentChallenger;
    }

    /**
     * This method returns the current challenge nr.
     *
     * @return the current challenge nr.
     */
    public int getCurrentChallengerNr(){
        return currentChallengerNr;
    }

    /**
     * This method updates all the variables which store game information/ game state information which are used to
     * update the UI.
     *
     * @param currentMove the current move either performed by the player or the opponent.
     * @param turn the current turn.
     * @param currentChallenger the current challenger.
     * @param currentChallengerNr the current challenge number.
     * @param gameStatus the current game status.
     */
    public void update(int currentMove, int turn, String currentChallenger, int currentChallengerNr, int gameStatus){
        this.currentMove = currentMove;
        this.turn = turn;
        this.currentChallenger = currentChallenger;
        this.currentChallengerNr = currentChallengerNr;
        this.gameStatus = gameStatus;
        notifyObservers();
    }
}
