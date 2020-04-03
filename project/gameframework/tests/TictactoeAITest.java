package project.gameframework.tests;

import javafx.scene.layout.Pane;
import project.gamemodules.tictactoegame.TicTacToeMinimaxStrategy;
import project.gamemodules.tictactoegame.TicTacToeBoardLogic;
import project.gamemodules.tictactoegame.TicTacToeGameLogic;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TictactoeAITest {
    public static void main(String[] args){

        TicTacToeBoardLogic board = new TicTacToeBoardLogic();
        TicTacToeGameLogic logic = new TicTacToeGameLogic();
        logic.setBoard(board);
        TicTacToeMinimaxStrategy ai = new TicTacToeMinimaxStrategy();

        Random random = new Random();

        int tests = 0;
        int one = 0;
        int two = 0;
        int draw = 0;

        while(true){
            int turn = 1;
            while(logic.gameOver() == 0){
                int move;
                if(turn == 1){
                    ArrayList<Integer> moves = logic.getMoves(turn);
                    int choice = random.nextInt(moves.size());
                    move = moves.get(choice);
                } else {
                    move = ai.getBestMove(board, turn);
                }
                logic.doMove(move, turn);
//                board.printBoard();
                turn = 3 - turn;
            }

            tests++;
            switch(logic.gameOver()){
                case 1:
                    one++;
                    break;
                case 2:
                    two++;
                    break;
                case 3:
                    draw++;
                    break;
            }

            board.resetBoard();
            System.out.println("Total tests: " + tests);
            System.out.println("Player 1 won " + one + " times");
            System.out.println("Player 2 won " + two + " times");
            System.out.println("Draws " + draw);
        }
    }
}
