package Project.GameFramework.Tests;

import Project.GameModules.ReversiGame.ReversiBoard;
import Project.GameModules.ReversiGame.ReversiGameLogic;
import Project.GameModules.TicTacToeGame.TicTacToeBoard;

public class GameTest {
    public static void main(String[] args){
        // tictactoeTests();
        reversiTests();
    }

    private static void tictactoeTests(){
        // Make tic tac toe board and print it
        System.out.println("Tic tac toe board:");
        TicTacToeBoard tictactoeBoard = new TicTacToeBoard();
        tictactoeBoard.printBoard();
    }

    private static void reversiTests(){
        // Make reversi board + gamelogic and print it
        System.out.println("Reversi board:");
        ReversiBoard reversiBoard = new ReversiBoard();
        reversiBoard.printBoard();
        ReversiGameLogic reversiLogic = new ReversiGameLogic();
        reversiLogic.setBoard(reversiBoard);

        // Print reversiboard disc counts
        System.out.println("Currently there are " + reversiBoard.getDiscCount(0) + " unoccupied positions, "
                + reversiBoard.getDiscCount(1) + " white discs and "
                + reversiBoard.getDiscCount(2) + " black discs.");

        // Print valid moves default board
        System.out.print("Current valid moves for white are: ");
        for(int move : reversiLogic.getMoves(2)){
            System.out.print(move + " ");
            reversiBoard.setBoardPos(move, 3);
        }
        System.out.println(".");

        System.out.print("Current valid moves for black are: ");
        for(int move : reversiLogic.getMoves(1)){
            System.out.print(move + " ");
            reversiBoard.setBoardPos(move, 4);
        }
        System.out.println(".");

        reversiBoard.printBoard();

        // Print valid moves for test case for black player
        reversiBoard.setTestBoard2();
        System.out.print("Current valid moves for black are: ");
        for(int move : reversiLogic.getMoves(2)){
            System.out.print(move + " ");
            // reversiBoard.setBoardPos(move, 3);
        }
        System.out.println(".");
        reversiBoard.printBoard();

        // Make a move
        reversiLogic.doMove(32, 2);
        reversiBoard.printBoard();
    }
}
