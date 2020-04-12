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
    private Label turn;

    private Label player1;
    private Label player2;

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
        //
        turn = makeLabel("Turn: ", 300, 50, "left");
        turn.setPadding(new Insets(0, 0, 0, 10));

        player1 = makeLabel("Player 1", 150, 75, "center");
        Label versus = makeLabel("VS", 50, 75, "center");
        player2 = makeLabel("Player 2", 150, 75, "center");
        HBox players = new HBox();
        players.setAlignment(Pos.CENTER);
        players.getChildren().addAll(player1, versus, player2);

        Label score1 = makeLabel("0", 150, 75, "center");
        Label between = makeLabel("-", 50, 75, "center");
        Label score2 = makeLabel("0", 150, 75, "center");
        HBox scores = new HBox();
        scores.setAlignment(Pos.CENTER);
        scores.getChildren().addAll(score1, between, score2);

        VBox turnandwin = new VBox(turn, players, scores);
        buttons.setStyle("-fx-border-width: 2px;");
        topBar.getChildren().addAll(buttons, turnandwin);
    }

    private Label makeLabel(String text, int width, int height, String allignment){
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

    public Label getPlayer1(){
        return player1;
    }

    public Label getPlayer2(){
        return player2;
    }

    public Label getTurn() {
        return turn;
    }

    public Stage getWindow(){
        return window;
    }

    public void setTurn(String playername){ turn.setText(playername); }

    public HashMap<String, Button> getGameButtons(){
        return gameButtons;
    }

    public abstract void setMode(String mode);

    public abstract GameBoard getGameBoard();

    public abstract void setRestartButton(boolean bool);
}
