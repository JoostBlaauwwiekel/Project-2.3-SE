package project.mvc.view;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import project.gameframework.GameBoardLogic;

public abstract class GameBoard extends FlowPane {

    private int gameBoardWidth;
    private int gameBoardHeight;
    private int gameBoardDimension;

    // Deze twee voeg ik toe zodat je de grootte van de knop kan bepalen. Als je de knoppen standaard op 150x150 past het wel bij TicTacToe op het scherm maar niet bij reversi - Rainier
    private int gameButtonWidth;
    private int gameButtonHeight;

    private int turn;
    private int counter;

    private Button[] tiles;
    private GameBoardLogic gameBoard;

    public GameBoard(int width, int height, int buttonHeight, int buttonWidth, GameBoardLogic gameBoard) {
        gameBoardWidth = width;
        gameBoardHeight = height;
        gameBoardDimension = width * height;
        gameButtonWidth = buttonWidth;
        gameButtonHeight = buttonHeight;

        this.gameBoard = gameBoard;

        turn = 1;
        counter = 0;
        tiles = new Button[width * height];
        drawBoard();
    }

    public void drawBoard() {
        for (int i = 0; i < gameBoardDimension; i++) {
            tiles[i] = new Button("");
            // Misschien is het handig dat we de knop grootte kunnen instellen per game. Als je namelijk de knop 150x150 maakt dan past het niet op het scherm bij reversi
            tiles[i].setMinSize(gameButtonWidth, gameButtonHeight);
            tiles[i].setId(Integer.toString(i));
            Button btn = tiles[i];

            btn.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(getCounter() % 2 == 0 || getCounter() == 0) {
                        turn = 1;
                    }
                    else {
                        turn = 2;
                    }
                    int ID = Integer.parseInt(btn.getId());
                    setMove(ID, turn, gameBoard, btn);
                }
            });
        }
    }

    private void setMove(int pos, int state, GameBoardLogic board, Button btn) {
        btn.setText(Integer.toString(state));
        counter++;
    }

    public int getCounter(){
        return counter;
    }

    public Button[] getTiles(){
        return tiles;
    }

    public Label getResult(){

        Label result = new Label("TEST");
        result.setTextFill(Color.WHITE);
        if(counter == 8){
            result.setText("Game over!");
        }
        return result;
    }

    public int getGameBoardWidth() {
        return gameBoardWidth;
    }

    public int getGameBoardHeight() {
        return gameBoardHeight;
    }

    public int getGameBoardDimension() {
        return gameBoardDimension;
    }

    public int getGameButtonWidth() {
        return gameButtonWidth;
    }

    public int getGameButtonHeight() {
        return gameButtonHeight;
    }
}
