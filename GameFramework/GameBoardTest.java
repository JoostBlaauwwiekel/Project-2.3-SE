package GameFramework;

import ReversiGame.ReversiBoard;
import TicTacToeGame.TicTacToeBoard;

public class GameBoardTest {
    public static void main(String[] args){
        System.out.println("Tac tac toe board:");
        TicTacToeBoard tictactoeBoard = new TicTacToeBoard();
        tictactoeBoard.printBoard();

        System.out.println("Reversi board:");
        ReversiBoard reversiBoard = new ReversiBoard();
        reversiBoard.printBoard();
    }
}
