package project.mvc.view.gameview;

import project.mvc.view.GameBoard;
import project.mvc.view.GameBoardView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.mvc.view.gameboard.ReversiBoard;
import project.mvc.view.gameboard.TicTacToeBoard;

public class ReversiView extends GameBoardView {

    private String mode = "";

    // Heb deze variable toegevoegd de breedte mee te kunnen geven. Zo komen de blokken netjes naast elkaar. Als hij namelijk de breed is heb je geen blok van 9x9
    public ReversiView(Stage window, int boardWidth) {
        super(window);

        FlowPane centerLayout = new FlowPane();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setStyle("-fx-background-color: #524D4E;");
        centerLayout.setMaxWidth(boardWidth);

        double tileSize = boardWidth/8;
        GameBoard board = new ReversiBoard(tileSize, tileSize);
        Button[] gameBoardButtons = board.getTiles();
        for(int i = 0; i < board.getTiles().length; i++) {
            gameBoardButtons[i].setStyle("-fx-background-color: #2DAE52; -fx-border-color: #000000; -fx-border-width: 2px;");
        }

        Button exitGame = new Button("Exit Reversi");
        super.getGameButtons().put(exitGame.getText(), exitGame);
        super.getTopBar();

        centerLayout.getChildren().addAll(board.getTiles());

        setCenter(centerLayout);
        setTop(super.getTopBar());
    }

    public void setMode(String mode){
        this.mode = mode;
    }

}
