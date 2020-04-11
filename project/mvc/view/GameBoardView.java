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
    private HBox players;
    private HBox scores;

    private HashMap<String, Button> gameButtons = new HashMap<>();
    private Label turn;

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

        turn = makeLabel("Turn: ", 300, 50, "left");
        turn.setPadding(new Insets(0, 0, 0, 10));

        players = new HBox();
        players.setAlignment(Pos.CENTER);

        scores = new HBox();
        scores.setAlignment(Pos.CENTER);

        VBox turnandwin = new VBox(turn, players, scores);
        buttons.setStyle("-fx-border-width: 2px;");
        topBar.getChildren().addAll(buttons, turnandwin);
    }

    protected Label makeLabel(String text, int width, int height, String allignment){
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

    public Label getTurn(){
        return turn;
    }

    public HBox getPlayers(){
        return players;
    };

    public HashMap<String, Button> getGameButtons(){
        return gameButtons;
    }

    public abstract void setMode(String mode);

    public void setTurn(String player){
        getTurn().setText("Turn: " + player);
    }
    protected void setPlayers(String player1, String player2){
        Label player1Label = makeLabel(player1, 150, 75, "center");
        Label versus = makeLabel("VS", 50, 75, "center");
        Label player2Label = makeLabel(player2, 150, 75, "center");
        players.getChildren().clear();
        players.getChildren().addAll(player1Label, versus, player2Label);
    }
    protected void setScores(int scorePlayer1, int scorePlayer2){
        Label score1 = makeLabel(Integer.toString(scorePlayer1), 150, 75, "center");
        Label between = makeLabel("-", 50, 75, "center");
        Label score2 = makeLabel(Integer.toString(scorePlayer2), 150, 75, "center");
        scores.getChildren().clear();
        scores.getChildren().addAll(score1, between, score2);
    }
    public abstract GameBoard getGameBoard();
}
