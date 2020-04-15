package project.mvc.view;

/**
 * This is the interface for the view observers.
 */
public interface ObserverView {

    /**
     * This method is called once the subject notifies each and every observer.
     * The observers will be notified that a change has occurred in the subject class.
     */
    public void update();

}
