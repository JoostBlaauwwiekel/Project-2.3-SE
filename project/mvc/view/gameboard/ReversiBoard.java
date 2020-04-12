package project.mvc.view.gameboard;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import project.mvc.controller.ApplicationController;
import project.mvc.view.GameBoard;

import java.util.ArrayList;

public class ReversiBoard extends GameBoard {

    private int turn = 1;
    private ArrayList<Integer> moves;

    public ReversiBoard(double buttonHeight, double buttonWidth, GridPane layout, ApplicationController controller){
        super(8,8, buttonHeight,buttonWidth, layout, controller);
        moves = new ArrayList<>();
    }

    public void setButtons(){
        Button[] tiles = super.getTiles();
        for(Button tile : tiles){
            tile.setOnAction(e -> {
                int id = Integer.parseInt(tile.getId());
                if(super.getController().playerHisTurn(id)) {
                    updateReversiGame();
                    gameOver(super.getController().getGameOver());
                }
            });
        }
    }

    private void updateReversiGame() {
        moves = super.getController().getMoves(1);
        if(moves.size() == 0){
            super.getController().playerHisTurn(-1);
        }
        updateReversiBoard(moves);
    }

    private void updateReversiBoard(ArrayList<Integer> moves){
        int[] b = super.getController().getBoard();
        Button[] tiles = super.getTiles();
        for(int i = 0; i < tiles.length; i++) {
            if(!moves.contains(i)) {
                tiles[i].setDisable(true);
            } else {
                tiles[i].setDisable(false);
            }
            setMove(b[i], tiles[i]);
        }
    }

    private void setMove(int state, Button btn) {
        if (state == 1) {
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
        } else if (state == 2) {
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

    public void setAImove(int move){
        updateReversiGame();
    }

    public void setMoveForEitherParty(int move, int turn){
        moves = super.getController().getMoves(turn);
        updateReversiBoard(moves);
    }

    public void resetBoard(){
        super.getController().resetGame();
        Button[] tiles = super.getTiles();
        for (Button button : tiles) {
            button.setGraphic(null);
        }
        updateReversiGame();
    }
}