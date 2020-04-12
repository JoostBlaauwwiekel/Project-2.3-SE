package project.mvc.view.gameboard;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import project.mvc.controller.ApplicationController;
import project.mvc.view.GameBoard;

public class TicTacToeBoard extends GameBoard {

    private int turn = 1;
    private int counter;
    private boolean offline;

    public TicTacToeBoard(double buttonHeight, double buttonWidth, GridPane layout, ApplicationController controller) {
        super(3, 3, buttonHeight, buttonWidth, layout, controller);
    }

    public void setButtons(){
        Button[] tiles = super.getTiles();
        for(Button tile : tiles){
            tile.setOnAction(e -> {
                int id = Integer.parseInt(tile.getId());
                if(super.getController().playerHisTurn(id)) {
                    setMove(turn, tile);
                    gameOver(super.getController().getGameOver());
                }
            });
        }
    }

    private void setMove(int state, Button btn) {
        if(state == 1) {
            Image image = new Image(getClass().getResourceAsStream("../../web/ttt-black-circle.png"), super.getGameButtonWidth() - 20, super.getGameButtonHeight() - 20, false, false);
            ImageView imageView = new ImageView(image);
            btn.setGraphic(imageView);
        } else if (state == 2) {
            Image image = new Image(getClass().getResourceAsStream("../../web/ttt-black-times.png"), super.getGameButtonWidth() - 20, super.getGameButtonHeight() - 20, false, false);
            ImageView imageView = new ImageView(image);
            btn.setGraphic(imageView);
        }
        counter++;
    }

    public void setAImove(int move){
        if(move >= 0)
            setMove( 2, super.getTiles()[move]);
    }

    public void setMoveForEitherParty(int move, int turn){
        if(move >= 0)
            setMove(turn, super.getTiles()[move]);
    }

    public void resetBoard(){
        super.getController().resetGame();
        Button[] tiles = super.getTiles();
        for (Button button : tiles) {
            button.setGraphic(null);
        }
    }

}