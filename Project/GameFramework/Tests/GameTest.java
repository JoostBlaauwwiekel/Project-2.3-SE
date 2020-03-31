package Project.GameFramework.Tests;

import Project.GameModules.ReversiGame.ReversiBoard;
import Project.GameModules.ReversiGame.ReversiGameLogic;
import Project.GameModules.TicTacToeGame.TicTacToeBoard;

public class GameTest {
    public static void main(String[] args){
        // Make tic tac toe board and print it
        System.out.println("Tic tac toe board:");
        TicTacToeBoard tictactoeBoard = new TicTacToeBoard();
        tictactoeBoard.printBoard();

        // Make reversi board + gamelogic and print it
        System.out.println("Reversi board:");
        ReversiBoard reversiBoard = new ReversiBoard();
        reversiBoard.printBoard();
        ReversiGameLogic reversiLogic = new ReversiGameLogic();
        reversiLogic.setBoard(reversiBoard);

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
        for(int move : reversiLogic.getMoves(2)){
            reversiBoard.setBoardPos(move, 3);
        }
        System.out.println(".");
        reversiBoard.printBoard();

        // Test game
        System.out.println("ACTUAL AI TOURNAMENT: ");
        reversiBoard.initBoard();
        reversiBoard.printBoard();
        int turn = 1;
        while(true){
            System.out.println("It's " + turn + "'s turn.");

            reversiLogic.doMove(reversiLogic.getMoves(turn).get(0), turn);
            reversiBoard.printBoard();
            turn = 3 - turn;

            // Print reversiboard disc counts
            System.out.println("Currently there are " + reversiBoard.getDiscCount(0) + " unoccupied positions, "
                    + reversiBoard.getDiscCount(1) + " white discs and "
                    + reversiBoard.getDiscCount(2) + " black discs.");

            if(reversiLogic.gameOver() != 0){
                break;
            }
        }
        System.out.println("Player " + reversiLogic.gameOver() + " won");
    }
}
