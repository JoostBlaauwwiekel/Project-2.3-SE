package project.mvc.view.gameboard;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import project.mvc.controller.ApplicationController;
import project.mvc.view.GameBoard;

/**
 * This is the Tic Tac Toe board class which contains the Tic Tac Toe UI board.
 */
public class TicTacToeBoard extends GameBoard {

    private int turn;
    private int counter;

    /**
     * This constructor creates a tictactoeboard with the given parameters.
     *
     * @param buttonHeight the height of the buttons.
     * @param buttonWidth tjhe width of the buttons
     * @param layout the layout of the board.
     * @param topBar the top bar of the tic tac toe view.
     * @param controller the corresponding MVC controller.
     */
    public TicTacToeBoard(Stage window, double buttonHeight, double buttonWidth, GridPane layout, HBox topBar, ApplicationController controller) {
        super(window, 3, 3, buttonHeight, buttonWidth, layout, topBar, controller);
    }

    /**
     * This method sets the buttons on the tictactoeboard.
     */
    public void setButtons(){
        turn = 1;
        Button[] tiles = super.getTiles();
        for(Button tile : tiles){
            tile.setOnAction(e -> {
                int id = Integer.parseInt(tile.getId());
                if(super.getController().playerHisTurn(id)) {
                    super.setGameStats();
                    updateTicTacToeBoard();
                    gameOver(super.getController().getGameOver());
                }
            });
        }
    }

    /**
     * This method is used to update the tictactoeboard.
     */
    private void updateTicTacToeBoard(){
        int[] board = getController().getBoard();
        int counter = 0;
        for(int piece : board){
            Button b = super.getTiles()[counter];
            if(piece == 1){
                Image image = new Image(getClass().getResourceAsStream("../../web/ttt-black-circle.png"), super.getGameButtonWidth() - 20, super.getGameButtonHeight() - 20, false, false);
                ImageView imageView = new ImageView(image);
                if(!super.getController().getOffline()) {
                    Platform.runLater(() -> {
                        b.setGraphic(imageView);
                    });
                }
                else {
                    b.setGraphic(imageView);
                }
            }
            else if(piece == 2){
                Image image = new Image(getClass().getResourceAsStream("../../web/ttt-black-times.png"), super.getGameButtonWidth() - 20, super.getGameButtonHeight() - 20, false, false);
                ImageView imageView = new ImageView(image);
                if(!super.getController().getOffline()) {
                    Platform.runLater(() -> {
                        b.setGraphic(imageView);
                    });
                }
                else {
                    b.setGraphic(imageView);
                }
            }
            counter++;
        }
    }

    /**
     * This method is used update the board offline.
     */
    public void updateOfflineBoard(){
        updateTicTacToeBoard();
    }

    /**
     * This method is used to update the board when playing an online game.
     *
     * @param turn the current turn.
     */
    public void updateBoard(int turn){
        this.turn = turn;
        updateTicTacToeBoard();
    }

    /**
     * This method resets the tictactoeboard.
     */
    public void resetBoard(){
        super.getController().resetGame();
        Button[] tiles = super.getTiles();
        for (Button button : tiles) {
            button.setGraphic(null);
        }
    }

}