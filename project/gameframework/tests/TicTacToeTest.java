package project.gameframework.tests;

import project.gameframework.GameBoardLogic;
import project.gameframework.GameLogic;
import project.gamemodules.tictactoegame.*;

public class TicTacToeTest {

    public static void main(String[] args) {
        GameBoardLogic board = new TicTacToeBoardLogic();
        TicTacToeMiniMax ai = new TicTacToeMiniMax();
        GameLogic logic = new TicTacToeGameLogic();
        logic.setBoard(board);

        while(logic.gameOver() == 0) {
            System.out.println();
        }
    }
}
