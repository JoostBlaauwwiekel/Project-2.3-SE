package project.mvc.view.gameview;

import javafx.geometry.Insets;
import project.mvc.controller.ApplicationController;
import project.mvc.view.GameBoard;
import project.mvc.view.GameBoardView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import project.mvc.view.gameboard.TicTacToeBoard;

public class TicTacToeView extends GameBoardView {

    private String mode = "";
    private GameBoard ticTacToeBoard;
    private Button restart;

    /**
     * This method makes a TicTacToeView with the given params.
     * @param window
     * @param boardWidth
     * @param controller
     */
    public TicTacToeView(Stage window, int boardWidth, ApplicationController controller) {
        super(window);
        GridPane centerLayout = new GridPane();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setStyle("-fx-background-color: #E5E2E2;");
        centerLayout.setMaxWidth(boardWidth);

        double tileSize = boardWidth/3;
        ticTacToeBoard = new TicTacToeBoard(tileSize, tileSize, centerLayout, super.getTopBar(), controller);

        Button[] gameBoardButtons = ticTacToeBoard.getTiles();
        for(int i = 0; i < ticTacToeBoard.getTiles().length; i++) {
            gameBoardButtons[i].setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000; -fx-border-width: 2px; ");
        }

        restart = new Button("Restart Tic Tac Toe");
        Button exitGame = new Button("Exit Tic Tac Toe");

        exitGame.setStyle("-fx-background-color: #F14141;  -fx-font-size: 11px; -fx-background-radius: 10;");
        restart.setStyle("-fx-background-color: #808080;  -fx-font-size: 11px; -fx-background-radius: 10;");
        exitGame.setPrefSize(110, 70);
        restart.setPrefSize(110, 70);

        super.getGameButtons().put("Exit Tic-tac-toe", exitGame);
        super.getGameButtons().put("Restart Tic-tac-toe", restart);
        super.getButtons().getChildren().addAll(restart, exitGame);

        setCenter(centerLayout);
        setTop(super.getTopBar());
    }

    /**
     * This method sets whether or not the button is clickable accordingly to the given param.
     * @param bool
     */
    public void setRestartButton(boolean bool){
        restart.setDisable(bool);
    }

    /**
     * This method sets the mode.
     * @param mode
     */
    public void setMode(String mode){
        this.mode = mode;
    }

    /**
     * This method returns the tictactoeboard.
     * @return
     */
    public GameBoard getGameBoard(){
        return ticTacToeBoard;
    }

}
