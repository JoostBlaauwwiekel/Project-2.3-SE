package project.mvc.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;

public abstract class GameBoardView extends BorderPane {

    private Stage window;
    private VBox sideBar;
    private HBox topBar;

    private HashMap<String, Button> gameButtons = new HashMap<>();

    protected GameBoardView(Stage window){
        this.window = window;
        intializeSideBarAndTopBar();
    }

    private void intializeSideBarAndTopBar(){
        sideBar = new VBox(20);
        sideBar.setPadding(new Insets(20,20,20,20));
        sideBar.setStyle("-fx-background-color: #524D4E;");

        topBar = new HBox(20);
        topBar.setPadding(new Insets(20,20,20,20));
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color: #000000;");
    }

    protected VBox getSidebar(){
        return sideBar;
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
}
