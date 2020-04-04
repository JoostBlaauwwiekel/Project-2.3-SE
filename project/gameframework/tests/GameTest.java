package project.gameframework.tests;

import project.gamemodules.reversigame.ReversiGameLogic;
import project.gamemodules.reversigame.ReversiMinimaxStrategy;

public class GameTest {
    public static void main(String[] args){
        // Make tic tac toe board and print it
        System.out.println("Tic tac toe board:");
        project.gamemodules.tictactoegame.TicTacToeBoardLogic tictactoeBoard = new project.gamemodules.tictactoegame.TicTacToeBoardLogic();
        tictactoeBoard.printBoard();

        // Make reversi board + gamelogic and print it
        System.out.println("Reversi board:");
        project.gamemodules.reversigame.ReversiBoardLogic reversiBoard = new project.gamemodules.reversigame.ReversiBoardLogic();
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

        // Test game with AI
        System.out.println("ACTUAL AI TOURNAMENT: ");
        reversiBoard.initBoard();
        reversiBoard.printBoard();

        ReversiMinimaxStrategy AI = new ReversiMinimaxStrategy();
        int turn = 1;
        while(true){
            System.out.println("It's " + turn + "'s turn.");

            int bestMove = AI.getBestMove(reversiBoard, turn);
            System.out.println("Player " + turn + " made move " + bestMove);
            if(bestMove != -1){
                reversiLogic.doMove(bestMove, turn);
            }
            System.out.println(reversiLogic.getPossibleFlips(reversiBoard, turn));
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
