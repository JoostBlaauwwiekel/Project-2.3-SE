package project.mvc.view.gameview;

import project.gameframework.GameLogic;
import project.gamemodules.GameData;
import project.gamemodules.tictactoegame.TicTacToeGameLogic;
import project.mvc.controller.ApplicationController;
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
    private GameData gameData;
    private GameLogic ticTacToeGameLogic;

    // Heb deze variable toegevoegd de breedte mee te kunnen geven. Zo komen de blokken netjes naast elkaar. Als hij namelijk de breed is heb je geen blok van 9x9
    public TicTacToeView(Stage window, int boardWidth, ApplicationController applicationController) {
        super(window);

        this.gameData = applicationController.getApplicationModel().getGameData();
        this.ticTacToeGameLogic = this.gameData.getGame("Tic-tac-toe");

        GridPane centerLayout = new GridPane();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setStyle("-fx-background-color: #524D4E;");
        centerLayout.setMaxWidth(boardWidth);

        double tileSize = boardWidth/3;
        GameBoard board = new TicTacToeBoard(tileSize, tileSize, gameData, centerLayout);
        Button[] gameBoardButtons = board.getTiles();
        for(int i = 0; i < board.getTiles().length; i++) {
            gameBoardButtons[i].setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000; -fx-border-width: 2px;");
        }

        Button exitGame = new Button("Exit Tic Tac Toe");
        super.getGameButtons().put(exitGame.getText(), exitGame);
        super.getTopBar();

        setCenter(centerLayout);
        setTop(super.getTopBar());
    }

    public void setMode(String mode){
        this.mode = mode;
    }

}
