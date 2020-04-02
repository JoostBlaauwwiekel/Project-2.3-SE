package project.mvc.view;

import project.mvc.view.mainscreen.ConfirmBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.HashMap;

public abstract class ScreenView extends VBox {

    private Stage window;
    private HashMap<String,Button> buttons = new HashMap<>();
    private HashMap<String, Scene> gameScenes = new HashMap<>();
    private HashMap<String, GameBoardView> gameBoardViews = new HashMap<>();

    /**
     * This is the constructor for the ScreenView class, the primary stage of a scene is given as a parameter.
     *
     * @param window the stage of a particular scene.
     */
    protected ScreenView(Stage window){
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
     * This method returns the gameScenes hashmap.
     *
     * @return the hashmap, gameScenes.
     */
    public HashMap<String, Scene> getGameScenes(){
        return gameScenes;
    }

    /**
     * This method returns the gameBoardViews hashmap.
     *
     * @return the hashmap, gameBoardViews.
     */
    public HashMap<String, GameBoardView> getGameBoardViews(){
        return gameBoardViews;
    }

    /**
     * This method should return the underlying scene. A.k.a the scene beneath the current scene. In addition, it should
     * return null when this method is implemented in the most underlying scene.
     *
     * @return the underlying scene or null.
     */
    public abstract Scene getSceneUnderneath();

    /**
     * This method should return the underlying view. A.k.a the view beneath the current view. In addition, it should
     * return null when this method is implemented in the most underlying view.
     *
     * @return the underlying view or null.
     */
    public abstract ScreenView getViewUnderneath();
}
