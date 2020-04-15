package project.mvc.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * This is the abstract class for all game board views.
 */
public abstract class GameBoardView extends BorderPane {

    private Stage window;
    private VBox buttons;
    private HBox topBar;

    private HashMap<String, Button> gameButtons = new HashMap<>();

    protected GameBoardView(Stage window){
        this.window = window;
        intializeSideBarAndTopBar();
    }

    /**
     * This method initializes the layout in the window.
     */
    private void intializeSideBarAndTopBar(){
        topBar = new HBox();
        topBar.setPadding(new Insets(0,0,0,0));
        topBar.setStyle("-fx-background-color: #E5E2E2;");
        topBar.setPrefSize(500, 200);

        buttons = new VBox();
        buttons.setPrefSize(150, 200);
        buttons.setPadding(new Insets(20,20,20,20));
        buttons.setSpacing(20);

        buttons.setStyle("-fx-border-width: 2px;");
        topBar.getChildren().addAll(buttons);
    }

    /**
     * This method is used to create Labels with given params.
     *
     * @param text the text to be set.
     * @param width the width of the label.
     * @param height the height of the label.
     * @param alignment the alignment necessary to set the alignment.
     * @return the created label.
     */
    public static Label makeLabel(String text, int width, int height, String alignment){
        Label label = new Label(text);
        label.setPrefSize(width, height);
        switch(alignment){
            case "center":
                label.setAlignment(Pos.CENTER);
                break;
            case "left":
                label.setAlignment(Pos.BASELINE_LEFT);
                break;
            case "right":
                label.setAlignment(Pos.BASELINE_RIGHT);
                break;
            default:
        }
        return label;
    }

    /**
     * This method returns the buttons exit and restart.
     *
     * @return the current VBox where the buttons are positioned.
     */
    protected VBox getButtons(){
        return buttons;
    }

    /**
     * This method returns the topbar.
     *
     * @return the current topbar HBox.
     */
    protected HBox getTopBar(){
        return topBar;
    }

    /**
     * This method returns the window.
     * @return the current stage: window.
     */
    public Stage getWindow(){
        return window;
    }

    /**
     * This method is used to return the gameButtons.
     *
     * @return the game buttons hashmap.
     */
    public HashMap<String, Button> getGameButtons(){
        return gameButtons;
    }

    /**
     * This method should let you set the current mode.
     *
     * @param mode the corresponding mode.
     */
    public abstract void setMode(String mode);

    /**
     * This method should return the corresponding game board.
     *
     * @return the corresponding game board.
     */
    public abstract GameBoard getGameBoard();

    /**
     * This method should disable or enable the restart button.
     *
     * @param bool true for enabling and false for disabling.
     */
    public abstract void setRestartButton(boolean bool);

    /**
     * This method should enable or disable the left and right pane depending on whether the current window
     * is in full screen or not.
     *
     * @param set true or false. Enable or disable.
     */
    public abstract void setLeftRightPane(boolean set);
}
