package project.gamedata;

/**
 * This is the interface for the GameData class. This is the abstract subject in the observer pattern.
 */
public interface GameDataSubject {

    /**
     * This method should register a new observer.
     * @param o the incoming observer.
     */
    public void registerObserver(Observer o);

    /**
     * This method should remove an observer which is currently subscribed to this subject.
     * @param o the observer which needs to be removed.
     */
    public void removeObserver(Observer o);

    /**
     * This method should notify all observers when there is a change.
     */
    public void notifyObservers();

}