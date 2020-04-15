package project.mvc.model;

import project.mvc.view.ObserverView;

/**
 * This interface defines the method a subject needs in order to notify its observers.
 */
public interface ModelInterface {

    /**
     * This method should register a new observer.
     * @param o the incoming observer.
     */
    public void registerObserver(ObserverView o);

    /**
     * This method should remove an observer which is currently subscribed to this subject.
     * @param o the observer which needs to be removed.
     */
    public void removeObserver(ObserverView o);

    /**
     * This method should notify all observers when there is a change.
     */
    public void notifyObservers();
}
