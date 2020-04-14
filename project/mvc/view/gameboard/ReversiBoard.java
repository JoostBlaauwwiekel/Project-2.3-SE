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

import java.util.ArrayList;

public class ReversiBoard extends GameBoard {

    private int turn = 1;
    private ArrayList<Integer> moves;

    /**
     * This constructor creates a reversiboard with the given params.
     * @param buttonHeight
     * @param buttonWidth
     * @param layout
     * @param topBar
     * @param controller
     */
    public ReversiBoard(Stage window, double buttonHeight, double buttonWidth, GridPane layout, HBox topBar, ApplicationController controller){
        super(window,8,8, buttonHeight,buttonWidth, layout, topBar, controller);
        moves = new ArrayList<>();
    }

    /**
     * This method is used to set the buttons on the reversiboard.
     */
    public void setButtons(){
        Button[] tiles = super.getTiles();
        for(Button tile : tiles){
            tile.setOnAction(e -> {
                int id = Integer.parseInt(tile.getId());
                if(super.getController().playerHisTurn(id)) {
                    super.setGameStats();
                    updateReversiGame();
                    gameOver(super.getController().getGameOver());
                }
            });
        }
    }

    /**
     * This method is used to update the reversi game.
     */
    private void updateReversiGame() {
        moves = super.getController().getMoves(1);
        if(moves.size() == 0){
            super.getController().playerHisTurn(-1);
        }
        updateReversiBoard(moves);
    }

    /**
     * This method is used to update the reversiboard.
     * @param moves
     */
    private void updateReversiBoard(ArrayList<Integer> moves){
        int[] b = super.getController().getBoard();
        Button[] tiles = super.getTiles();
        for(int i = 0; i < tiles.length; i++) {
            if(!moves.contains(i)) {
                tiles[i].setStyle("-fx-background-color: #1B5B1C; -fx-border-color: #000000; -fx-border-width: 0.5px;");
            } else {
                tiles[i].setStyle("-fx-background-color: #089000; -fx-border-color: #000000; -fx-border-width: 0.5px;");
            }
            setMove(b[i], tiles[i]);
        }
    }

    /**
     * This method is used to set a move on the given tile by the given player.
     * @param state
     * @param btn
     */
    private void setMove(int state, Button btn) {
        if (state == 2) {
            Image image = new Image(getClass().getResourceAsStream("../../web/black-circle.png"), super.getGameButtonWidth() - 15, super.getGameButtonWidth() - 15, false, false);
            ImageView imageView = new ImageView(image);
            if(!super.getController().getOffline()){
                Platform.runLater(() -> {
                    btn.setGraphic(imageView);
                });
            }
            else {
                btn.setGraphic(imageView);
            }
        } else if (state == 1) {
            Image image = new Image(getClass().getResourceAsStream("../../web/white-circle.png"), super.getGameButtonWidth() - 15, super.getGameButtonWidth() - 15, false, false);
            ImageView imageView = new ImageView(image);
            if(!super.getController().getOffline()) {
                Platform.runLater(() -> {
                    btn.setGraphic(imageView);
                });
            }
            else{
                btn.setGraphic(imageView);
            }
        }
    }

    /**
     * This method is used to set an AI move.
     */
    public void setAImove(){
        updateReversiGame();
    }

    public void setMoveForEitherParty(int turn){
        moves = super.getController().getMoves(turn);
        updateReversiBoard(moves);
    }

    /**
     * This method is used to reset the board.
     */
    public void resetBoard(){
        super.getController().resetGame();
        Button[] tiles = super.getTiles();
        for (Button button : tiles) {
            button.setGraphic(null);
        }
        updateReversiGame();
    }
}