package project.mvc.model;

import project.mvc.view.ObserverView;

public interface ModelInterface {

    public void registerObserver(ObserverView o);

    public void removeObserver(ObserverView o);

    public void notifyObservers();
}
