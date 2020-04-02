package project.gameframework;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.HashMap;

public abstract class GameBoardView extends BorderPane {

    private Stage window;
    private HashMap<String, Button> gameButtons = new HashMap<>();

    protected GameBoardView(Stage window){
        this.window = window;
    }

    public Stage getWindow(){
        return window;
    }

    public HashMap<String, Button> getGameButtons(){
        return gameButtons;
    }
}
