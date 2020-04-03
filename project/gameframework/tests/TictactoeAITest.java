package project.gameframework.tests;

import project.gamemodules.tictactoegame.TicTacToeMiniMax;
import project.gamemodules.tictactoegame.TicTacToeBoardLogic;
import project.gamemodules.tictactoegame.TicTacToeGameLogic;

public class TictactoeAITest {
    public static void main(String[] args){

        TicTacToeBoardLogic board = new TicTacToeBoardLogic();
        TicTacToeGameLogic logic = new TicTacToeGameLogic();
        logic.setBoard(board);
        TicTacToeMiniMax ai = new TicTacToeMiniMax();

        int turn = 1;
        while(true){
            int move = ai.getBestMove(board, turn);
            logic.doMove(move, turn);
            board.printBoard();
            turn = 3 - turn;
        }
    }
}
