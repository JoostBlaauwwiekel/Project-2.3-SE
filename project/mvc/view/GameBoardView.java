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

    private void intializeSideBarAndTopBar(){
        topBar = new HBox();
        topBar.setPadding(new Insets(0,0,0,0));
        topBar.setStyle("-fx-background-color: #FFFFFF;");
        topBar.setPrefSize(500, 200);

        buttons = new VBox();
        buttons.setPrefSize(150, 200);

        buttons.setStyle("-fx-border-width: 2px;");
        topBar.getChildren().addAll(buttons);
    }

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

    protected VBox getButtons(){
        return buttons;
    }

    protected HBox getTopBar(){
        return topBar;
    }

    public Stage getWindow(){
        return window;
    }

    public HashMap<String, Button> getGameButtons(){
        return gameButtons;
    }

    public abstract void setMode(String mode);

    public abstract GameBoard getGameBoard();

    public abstract void setRestartButton(boolean bool);
}
