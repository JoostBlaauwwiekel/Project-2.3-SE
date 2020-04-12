package project.mvc.view.gameview;

import javafx.scene.control.Label;
import project.gamemodules.GameData;
import project.mvc.controller.ApplicationController;

import project.mvc.model.ApplicationModel;
import project.mvc.view.GameBoard;
import project.mvc.view.GameBoardView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import project.mvc.view.gameboard.TicTacToeBoard;


public class TicTacToeView extends GameBoardView {

    private String mode = "";
    private GameBoard ticTacToeBoard;

    public TicTacToeView(Stage window, int boardWidth, ApplicationModel model) {
        super(window);

        GridPane centerLayout = new GridPane();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setStyle("-fx-background-color: #524D4E;");
        centerLayout.setMaxWidth(boardWidth);

        double tileSize = boardWidth/3;

        ticTacToeBoard = new TicTacToeBoard(tileSize, tileSize, model, centerLayout, super.getTopBar());
        Button[] gameBoardButtons = ticTacToeBoard.getTiles();
        for(int i = 0; i < ticTacToeBoard.getTiles().length; i++) {
            gameBoardButtons[i].setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000; -fx-border-width: 2px;");
        }

        Button restart = new Button("Restart Tic Tac Toe");
        Button exitGame = new Button("Exit Tic Tac Toe");

        exitGame.setStyle("-fx-background-color: #FF0000; -fx-border-color: #000000;");
        restart.setStyle("-fx-background-color: #808080; -fx-border-color: #000000;");
        exitGame.setPrefSize(150, 100);
        restart.setPrefSize(150, 100);

        super.getGameButtons().put(exitGame.getText(), exitGame);
        super.getGameButtons().put(restart.getText(), restart);
        super.getButtons().getChildren().addAll(restart, exitGame);

        //setPlayers("test", "test");
        //setScores(0,0);
        //setTurn("");
        setCenter(centerLayout);
        setTop(super.getTopBar());
    }
    public void setMode(String mode){
        this.mode = mode;
    }

    public GameBoard getGameBoard(){
        return ticTacToeBoard;
    }



}
