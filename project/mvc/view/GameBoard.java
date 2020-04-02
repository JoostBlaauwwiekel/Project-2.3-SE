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

    private int turn;
    private int counter;

    private Button[] tiles;
    private GameBoardLogic gameBoard;

    public GameBoard(int width, int height, GameBoardLogic gameBoard) {
        gameBoardWidth = width;
        gameBoardHeight = height;
        gameBoardDimension = width * height;
        this.gameBoard = gameBoard;
        turn = 1;
        counter = 0;
        tiles = new Button[width * height];
        drawBoard();
    }

    public void drawBoard() {
        for (int i = 0; i < gameBoardDimension; i++) {
            tiles[i] = new Button("");
            tiles[i].setMinSize(150, 150);
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
}
