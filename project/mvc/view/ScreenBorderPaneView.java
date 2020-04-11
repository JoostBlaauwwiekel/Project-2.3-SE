package project.mvc.view;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import project.mvc.view.mainscreen.ConfirmBox;

import java.util.HashMap;

public abstract class ScreenBorderPaneView extends BorderPane {

    private Stage window;
    private HashMap<String, Button> buttons = new HashMap<>();
    private HashMap<String, ListView<String>> listViews = new HashMap<>();

    /**
     * This is the constructor for the ScreenView class, the primary stage of a scene is given as a parameter.
     *
     * @param window the stage of a particular scene.
     */
    protected ScreenBorderPaneView(Stage window){
        this.window = window;
    }


    /**
     * This method should close the application when called.
     *
     * @param window the stage which will be closed.
     */
    protected void closeApplication(Stage window) {
        boolean answer = ConfirmBox.display("Are you sure?", "Are you sure you want to close the application?");
        if(answer)
            window.close();
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

}
