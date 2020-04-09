package project.mvc.view.gameboard;

import javafx.scene.layout.GridPane;
import project.gamemodules.GameData;
import project.gamemodules.tictactoegame.TicTacToeBoardLogic;
import project.mvc.view.GameBoard;

public class TicTacToeBoard extends GameBoard {

    public TicTacToeBoard(double buttonHeight, double buttonWidth, GameData gameData, GridPane layout) {
        super(3, 3,buttonHeight,buttonWidth,new TicTacToeBoardLogic(), gameData, "Tic-tac-toe", layout);
    }

}