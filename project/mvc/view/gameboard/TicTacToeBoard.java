package project.mvc.view.gameboard;

import project.gamemodules.tictactoegame.TicTacToeBoardLogic;
import project.mvc.view.GameBoard;

public class TicTacToeBoard extends GameBoard {

    public TicTacToeBoard(double buttonHeight, double buttonWidth) {
        super(3, 3,buttonHeight,buttonWidth,new TicTacToeBoardLogic());
    }

}
