package project.mvc.view.gamescreen;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class TicTacToeBoard {

    project.gamemodules.TicTacToeGame.TicTacToeBoard board = new project.gamemodules.TicTacToeGame.TicTacToeBoard();
    Button[] tiles = new Button[9];
    int turn = 1;
    int counter = 0;

    public TicTacToeBoard(int type) {
        if (type == 0) {
            //For loop geeft elke button eigenschappen
            for (int i = 0; i < 9; i++) {
                tiles[i] = new Button("");
                tiles[i].setMinSize(150, 150);
                tiles[i].setId(Integer.toString(i));
                Button btn = tiles[i];
                //Zorgt voor text op de button nadat deze geklikt is
                btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(getCounter() % 2 == 0 || getCounter() == 0) {
                            turn = 1;
                        }
                        else {
                            turn = 2;
                        }
                        int ID = Integer.parseInt(btn.getId());
                        setMove(ID, turn, board, btn);
                    }
                });
            }

        }

    }
    public project.gamemodules.TicTacToeGame.TicTacToeBoard getBoard(){
        return board;
    }

    private void setMove(int pos, int state, project.gamemodules.TicTacToeGame.TicTacToeBoard board, Button btn) {
        btn.setText(Integer.toString(state));
        board.setBoardPos(state, pos);
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
