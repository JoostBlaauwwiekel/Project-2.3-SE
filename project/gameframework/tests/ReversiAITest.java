package project.gameframework.tests;

import project.gamemodules.reversigame.ReversiBoardLogic;
import project.gamemodules.reversigame.ReversiGameLogic;
import project.gamemodules.reversigame.ReversiMinimaxStrategy;

import java.util.ArrayList;
import java.util.Random;

public class ReversiAITest {
    public static void main(String[] args){
        ReversiBoardLogic board = new ReversiBoardLogic();
        ReversiGameLogic logic = new ReversiGameLogic();
        logic.setBoard(board);
        ReversiMinimaxStrategy ai = new ReversiMinimaxStrategy();

        Random random = new Random();

        int tests = 0;
        int p1 = 0;
        int p2 = 0;
        int draw = 0;
        System.out.println("REVERSI AI TEST:");
        while(tests < 100){
            int turn = 1;
            while(logic.gameOver() == 0){
                ArrayList<Integer> moves = logic.getMoves(turn);

                int move = -1;
                if(moves.size() != 0){
                    if(turn == 1){
                        int choice = random.nextInt(moves.size());
                        move = moves.get(choice);
                    } else {
                        move = ai.getBestMove(board, turn);
                    }
                }

                if(move != -1){
                    logic.doMove(move, turn);
//                    System.out.println("Player " + turn + " made move " + move);
                } else {
//                    System.out.println("Player " + turn + " passed");
                }
//                board.printBoard();
                turn = 3 - turn;
            }

            switch(logic.gameOver()){
                case 1:
                    p1++;
                    break;
                case 2:
                    p2++;
                    break;
                case 3:
                    draw++;
                    break;
            }
            System.out.println("Total tests: " + tests);
            System.out.println("Player 1 won " + p1 + " times");
            System.out.println("Player 2 won " + p2 + " times");
            System.out.println("Draws " + draw);
            board.resetBoard();
            tests++;
        }

        System.out.println("Total tests: " + tests);
        System.out.println("Player 1 won " + p1 + " times");
        System.out.println("Player 2 won " + p2 + " times");
        System.out.println("Draws " + draw);
    }
}
