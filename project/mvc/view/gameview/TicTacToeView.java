package project.mvc.view.gameview;

import project.mvc.view.GameBoard;
import project.mvc.view.GameBoardView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.mvc.view.gameboard.TicTacToeBoard;

public class TicTacToeView extends GameBoardView {

    private String mode = "";

    public TicTacToeView(Stage window) {
        super(window);

        FlowPane centerLayout = new FlowPane();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setStyle("-fx-background-color: #524D4E;");
        centerLayout.setMaxSize(450, 450);

        Label gameMode = new Label("");
        gameMode.setTextFill(Color.WHITE);
        Label wins = new Label("Wins: 1");
        wins.setTextFill(Color.WHITE);

        GameBoard board = new TicTacToeBoard();
        Button exitGame = new Button("Exit Tic Tac Toe");
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
