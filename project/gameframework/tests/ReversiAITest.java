package project.gameframework.tests;

import project.gamemodules.reversigame.ReversiBoardLogic;
import project.gamemodules.reversigame.ReversiGameLogic;
import project.gamemodules.reversigame.ReversiMinimaxStrategy;
import project.gamemodules.reversigame.ReversiOldAI;

import java.util.ArrayList;
import java.util.Random;

public class ReversiAITest {

    private ReversiBoardLogic board;
    private ReversiGameLogic logic;
    private ReversiMinimaxStrategy ai;
    private ReversiOldAI oldAi;

    public static void main(String[] args){
        ReversiAITest test = new ReversiAITest();
//        test.findBias();
        test.effectivenessTest();
    }

    public ReversiAITest(){
        this.board = new ReversiBoardLogic();
        this.logic = new ReversiGameLogic();
        logic.setBoard(board);
        ai = new ReversiMinimaxStrategy();
        oldAi = new ReversiOldAI();
    }

    private void effectivenessTest(){
        long startTime = System.currentTimeMillis();
        int testCount = 100;
        float win = doTests(testCount);
        System.out.println("The test finished and took " + (System.currentTimeMillis() - startTime) / 1000.00 + " seconds");
        System.out.println("Win percentage is " + ((win / testCount) * 100) + "%");
    }

    private void findBias(){
        int bestBiasEval = -1;
        int bestBias = 0;
        for(int bias=-10; bias < 50; bias++){
//            ai.setInsideCornerBias(bias);
            System.out.println("Testing with bias : " + bias + ", best bias so far is : " + bestBias);
            int biasEval = doTests(100);
            System.out.println("Eval: " + biasEval);
            if(biasEval > bestBiasEval){
                bestBiasEval = biasEval;
                bestBias = bias;
            }
        }
        System.out.println("The best possible bias is : " + bestBias);
    }

    private int doTests(int amount){
        Random random = new Random();

        int tests = 0;
        int p1 = 0;
        int p2 = 0;
        int draw = 0;
        while(tests < amount){
            int turn = 2;
            while(logic.gameOver() == 0){
                ArrayList<Integer> moves = logic.getMoves(turn);

                int move = -1;
                if(moves.size() != 0){
                    if(turn == 1){
                        int choice = random.nextInt(moves.size());
                        move = moves.get(choice);
//                        move = oldAi.getBestMove(board, turn);
                    } else {
                        move = ai.getBestMove(board, turn);
                    }
                }

                if(move != -1){
                    logic.doMove(move, turn);
                } else {
                }
//                board.printBoard();
                turn = 3 - turn;
            }

            switch(logic.gameOver()){
                case 1:
                    board.printBoard();
                    p1++;
                    break;
                case 2:
                    p2++;
                    break;
                case 3:
                    draw++;
                    break;
            }
            board.resetBoard();

            System.out.println("Total tests: " + tests);
            System.out.println("Player 1 won " + p1 + " times");
            System.out.println("Player 2 won " + p2 + " times");
            System.out.println("Draws " + draw);

            tests++;
        }

        System.out.println("Total tests: " + tests);
        System.out.println("Player 1 won " + p1 + " times");
        System.out.println("Player 2 won " + p2 + " times");
        System.out.println("Draws " + draw);

        return p2;
    }
}
