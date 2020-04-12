package project.mvc.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import project.mvc.controller.ApplicationController;

public abstract class GameBoard {

    private int gameBoardWidth;
    private int gameBoardHeight;
    private int gameBoardDimension;

    private ApplicationController controller;

    private double gameButtonWidth;
    private double gameButtonHeight;
    private GridPane gameLayout;

    private Button[] tiles;

    protected GameBoard(int width, int height, double buttonHeight, double buttonWidth, GridPane layout, ApplicationController controller){
        this.controller = controller;
        gameBoardWidth = width;
        gameBoardHeight = height;
        gameBoardDimension = width * height;
        gameButtonWidth = buttonWidth;
        gameButtonHeight = buttonHeight;
        gameLayout = layout;
        tiles = new Button[width * height];

        drawBoard();
    }

    private void drawBoard() {
        int id;
        for (int row = 0; row < gameBoardHeight; row++) {
            for (int column = 0; column < gameBoardWidth; column++) {
                id = ((row * gameBoardWidth) + column);
                tiles[id] = new Button("");
                tiles[id].setMinSize(gameButtonWidth, gameButtonHeight);
                tiles[id].setId(Integer.toString(id));
                gameLayout.add(tiles[id], column, row);
            }
        }
    }

    private boolean setGameStatus(String winner){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("End of round");
        alert.setHeaderText(null);
        alert.setContentText(winner);
        alert.showAndWait();
        resetBoard();
        return true;
    }

    protected double getGameButtonWidth() {
        return gameButtonWidth;
    }

    protected double getGameButtonHeight() {
        return gameButtonHeight;
    }

    protected ApplicationController getController(){
        return controller;
    }

    protected boolean gameOver(int result) {
        if (result == 1) {
            return setGameStatus("Player 1 won!");
        }
        else if (result == 2) {
            return setGameStatus("Player 2 won!");
        }
        else if (result == 3) {
            return setGameStatus("Draw!");
        }
        else if(result == 4){
            return setGameStatus("Game has reset!");
        }
        else
            return false;
    }

    public void unSetButtons(){
        for(Button tile : tiles){
            tile.setOnAction(e -> {});
        }
    }

    public Button[] getTiles(){ return tiles; }

    public abstract void resetBoard();

    public abstract void setAImove(int move);

    public abstract void setButtons();

    public abstract void setMoveForEitherParty(int move, int turn);
}
