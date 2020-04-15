package project.mvc.view;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import project.mvc.controller.ApplicationController;
import project.mvc.view.mainscreen.ConfirmBox;

import java.util.HashMap;

/**
 * This is the abstract class for options views.
 */
public abstract class ScreenBorderPaneView extends BorderPane {

    private Stage window;
    private ApplicationController controller;

    private Label eventLabel;
    private Label scoreLabel;

    private HashMap<String, Button> buttons;
    private HashMap<String, ListView<String>> listViews;
    private HashMap<String, TextField> textFields;

    /**
     * This is the constructor for the ScreenView class, the primary stage of a scene is given as a parameter.
     *
     * @param window the stage of a particular scene.
     */
    public ScreenBorderPaneView(Stage window, ApplicationController controller){
        this.window = window;
        this.controller = controller;
        buttons = new HashMap<>();
        listViews = new HashMap<>();
        textFields = new HashMap<>();
    }

    protected void setEventLabel(Label eventLabel){
        this.eventLabel = eventLabel;
    }

    protected void setScoreLabel(Label scoreLabel){
        this.scoreLabel = scoreLabel;
    }

    public Label getEventLabel(){
        return eventLabel;
    }

    public Label getScoreLabel(){
        return scoreLabel;
    }

    /**
     * This method should close the application when called.
     *
     * @param window the stage which will be closed.
     */
    protected void closeApplication(Stage window) {
        boolean answer = ConfirmBox.display("Are you sure?", "Are you sure you want to close the application?");
        if(answer) window.close();
    }

    /**
     * This method returns the current primary stage of the scene.
     *
     * @return the current stage.
     */
    public Stage getWindow(){
        return window;
    }

    /**
     * This method returns the buttons hashmap.
     *
     * @return the hashmap, buttons.
     */
    public HashMap<String,Button> getButtons(){
        return buttons;
    }

    /**
     * This method returns the listviews hashmap.
     *
     * @return the hashmap, listview.
     */
    public HashMap<String, ListView<String>> getListViews() { return listViews; }

    /**
     * This method returns the MVC controller.
     *
     * @return controller.
     */
    protected ApplicationController getController(){
        return controller;
    }

    /**
     * This method returns all the text fields in a hashmap.
     *
     * @return all text fields in a hash map, accessible by the textFields name.
     */
    public HashMap<String, TextField> getTextFields() { return textFields; }

    /**
     * This method should be overriden in de subclasses. If not then a slider doesn't exist in the subclass and null
     * will be returned.
     *
     * @return either the slider or null.
     */
    public Slider getSlider(){
        return null;
    }

}
