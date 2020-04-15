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
     * @param text
     * @param width
     * @param height
     * @param allignment
     * @return
     */
    public static Label makeLabel(String text, int width, int height, String allignment){
        Label label = new Label(text);
        label.setPrefSize(width, height);
        switch(allignment){
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
     * @return
     */
    protected VBox getButtons(){
        return buttons;
    }

    /**
     * This method returns the topbar.
     * @return
     */
    protected HBox getTopBar(){
        return topBar;
    }

    /**
     * This method returns the window.
     * @return
     */
    public Stage getWindow(){
        return window;
    }

    /**
     * This method is used to return the gameButtons.
     * @return
     */
    public HashMap<String, Button> getGameButtons(){
        return gameButtons;
    }

    public abstract void setMode(String mode);

    public abstract GameBoard getGameBoard();

    public abstract void setRestartButton(boolean bool);

    /**
     * This method should enable or disable the left and right pane depending on whether the current window
     * is in full screen or not.
     *
     * @param set true or false. Enable or disable.
     */
    public abstract void setLeftRightPane(boolean set);
}
