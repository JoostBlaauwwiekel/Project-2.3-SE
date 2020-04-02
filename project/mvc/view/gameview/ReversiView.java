package project.mvc.view.gameview;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.mvc.view.GameBoard;
import project.mvc.view.GameBoardView;
import project.mvc.view.gameboard.ReversiBoard;

public class ReversiView extends GameBoardView {

    private String mode = "";

    public ReversiView(Stage window) {
        super(window);

        FlowPane centerLayout = new FlowPane();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setStyle("-fx-background-color: #3bbe3b;");
        centerLayout.setMaxSize(450, 450);

        Label gameMode = new Label("");
        gameMode.setTextFill(Color.WHITE);
        Label wins = new Label("Wins: 1");
        wins.setTextFill(Color.WHITE);

        GameBoard board = new ReversiBoard();
        Button exitGame = new Button("Exit Reversi");
        super.getGameButtons().put(exitGame.getText(), exitGame);

        super.getSidebar().getChildren().addAll(wins, exitGame);
        super.getTopBar().getChildren().addAll(gameMode);
        centerLayout.getChildren().addAll(board.getTiles());

        setCenter(centerLayout);
        setLeft(super.getSidebar());
        setTop(super.getTopBar());
    }

    public void setMode(String mode){
        this.mode = mode;
    }
}