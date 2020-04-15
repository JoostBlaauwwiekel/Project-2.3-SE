package project.mvc.view.gameview;

import javafx.geometry.Insets;
import project.mvc.controller.ApplicationController;
import project.mvc.view.GameBoard;
import project.mvc.view.GameBoardView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import project.mvc.view.gameboard.ReversiBoard;

/**
 * This is the class for the Reversi View.
 */
public class ReversiView extends GameBoardView {

    private String mode = "";
    private GameBoard reversiGameBoard;
    private Button restart;
    private boolean enabled;

    /**
     * This is the default constructor for the ReversiView class.
     *
     * @param window the current window/ stage.
     * @param boardWidth the width of the gameboard.
     * @param controller the corresponding MVC controller.
     */
    public ReversiView(Stage window, int boardWidth, ApplicationController controller) {
        super(window);

        GridPane centerLayout = new GridPane();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setStyle("-fx-background-color: #000000;");
        centerLayout.setMaxWidth(boardWidth);

        double tileSize = boardWidth/8;
        reversiGameBoard = new ReversiBoard(window, tileSize - 1, tileSize - 1, centerLayout, super.getTopBar(), controller);

        Button[] gameBoardButtons = reversiGameBoard.getTiles();
        for(int i = 0; i < reversiGameBoard.getTiles().length; i++) {
            gameBoardButtons[i].setStyle("-fx-background-color: #1B5B1C; -fx-border-color: #000000; -fx-border-width: 1px;");
            gameBoardButtons[i].setPadding(new Insets(1,1,1,1));
        }

        restart = new Button("Restart Reversi");
        Button exitGame = new Button("Exit Reversi");

        exitGame.setStyle("-fx-background-color: #F14141;  -fx-font-size: 13px; -fx-background-radius: 10;");
        restart.setStyle("-fx-background-color: #808080;  -fx-font-size: 13px; -fx-background-radius: 10;");
        exitGame.setPrefSize(110, 70);
        restart.setPrefSize(110, 70);

        super.getGameButtons().put(exitGame.getText(), exitGame);
        super.getGameButtons().put(restart.getText(), restart);
        super.getButtons().getChildren().addAll(restart, exitGame);

        setCenter(centerLayout);
        super.getTopBar().setAlignment(Pos.CENTER);
        setTop(super.getTopBar());
    }

    /**
     * This method sets the restart button.
     *
     * @param bool true or false. Enable or disable the button.
     */
    public void setRestartButton(boolean bool){
        restart.setDisable(bool);
    }

    /**
     * This method sets the mode.
     *
     * @param mode gameMode.
     */
    public void setMode(String mode){
        this.mode = mode;
    }

    /**
     * This method returns the game board.
     *
     * @return the reversi game board.
     */
    public GameBoard getGameBoard(){
        return reversiGameBoard;
    }

    /**
     * This method sets enabled. This was supposed to be the same implementation as tic tac toe
     * full screen but due to time constraints that couldn't have been finished.
     *
     * @param set whether the side VBoxes should be enabled or not. Regarding full screen.
     */
    public void setLeftRightPane(boolean set){
        this.enabled = set;
    }
}
