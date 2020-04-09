package project.gameframework.tests;

import project.gameframework.GameBoardLogic;
import project.gameframework.GameLogic;
import project.gamemodules.tictactoegame.*;

public class TicTacToeTest {

    public static void main(String[] args) {
        GameBoardLogic board = new TicTacToeBoardLogic();
        TicTacToeMinimaxStrategy ai1 = new TicTacToeMinimaxStrategy();
        TicTacToeMinimaxStrategy ai2 = new TicTacToeMinimaxStrategy();
        GameLogic logic = new TicTacToeGameLogic();
        logic.setBoard(board);

        int counter = 0;
        while(logic.gameOver() == 0) {

            if(counter % 2 == 0) {
                int move = ai1.getBestMove(board, 0);
                board.setBoardPos(move, 1);
            } else {
                int move = ai2.getBestMove(board, 0);
                board.setBoardPos(move, 2);
            }
            board.printBoard();
            counter++;
        }
    }
}
