package project.mvc.view.gameview;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.mvc.view.GameBoard;
import project.mvc.view.GameBoardView;
import project.mvc.view.gameboard.ReversiBoard;

public class ReversiView extends GameBoardView {

    private String mode = "";

    public ReversiView(Stage window, int boardWidth) {
        super(window);

        FlowPane centerLayout = new FlowPane();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setStyle("-fx-background-color: #b5651e;");
        // Reversi view moet geen vaste breedte heben
        centerLayout.setMaxWidth(boardWidth);

        Label gameMode = new Label("");
        gameMode.setTextFill(Color.WHITE);
        Label playerWins = new Label("Player Wins: ");
        Label computerWins = new Label("Computer Wins: ");
        playerWins.setTextFill(Color.WHITE);
        computerWins.setTextFill(Color.WHITE);

        GameBoard board = new ReversiBoard();
        Button[] gameBoardButtons = board.getTiles();
        for(int i = 0; i < board.getTiles().length; i++) {
            gameBoardButtons[i].setStyle("-fx-background-color: #2DAE52; -fx-border-color: #000000; -fx-border-width: 2px;");
        }
        Button exitGame = new Button("Exit Reversi");
        super.getGameButtons().put(exitGame.getText(), exitGame);

        super.getSidebar().getChildren().addAll(playerWins, computerWins, exitGame);
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