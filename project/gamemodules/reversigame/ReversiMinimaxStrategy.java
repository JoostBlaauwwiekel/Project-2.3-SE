package project.gamemodules.reversigame;

import project.gameframework.GameBoardLogic;
import project.gameframework.aistrategies.MinimaxStrategy;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReversiMinimaxStrategy extends MinimaxStrategy {

    // Maximimum depth for the minimax algorithm.
    private int maxDepth = 5;

    // Maximum time per move
    private float maxTime = 10;

    /**
     * This method iterates the valid moves and it it determines which move currently is the best move
     * to make. This method uses our own version of a minimax algorithm with alpha-beta pruning and
     * quiescence search. It also uses multithreading for even better performance.
     *
     * @return the best move.
     */
    @Override
    public synchronized int getBestMove(GameBoardLogic board, int player) {
        // Map that stores the calculated scores for valid moves.
        Map<Integer, Integer> results = new ConcurrentHashMap<>();

        // Board and logic to use later.
        ReversiBoardLogic reversiBoard = (ReversiBoardLogic) board;
        ReversiGameLogic logic = new ReversiGameLogic();
        logic.setBoard(reversiBoard);
        ArrayList<Integer> moves = logic.getMoves(player);

        // Start the timeout timer.
        long startTime = System.currentTimeMillis();

        // Choose if player should be maximizing our minimizing.
        int bestEval;
        boolean isMax;
        if(player == 1){
            bestEval = -10000;
            isMax = true;
        } else {
            isMax = false;
            bestEval = 10000;
        }

        // If there's to much moves use less depth.
        int depth;
        if(moves.size() > 6){
            depth = maxDepth - 1;
        } else {
            depth = maxDepth;
        }

        int resultCount = 0;
        for(int move : moves){
            // Generate a temp board and do the move.
            ReversiBoardLogic newBoard = new ReversiBoardLogic();
            newBoard.setBoard(reversiBoard.getBoard());
            ReversiGameLogic newLogic = new ReversiGameLogic();
            newLogic.setBoard(newBoard);
            newLogic.doMove(move, player);

            // Give the new board to a minimax worker.
            ReversiMinimaxWorker worker = new ReversiMinimaxWorker(newBoard, depth, !isMax, move, results);
            Thread thread = new Thread(worker);
            thread.start();

            resultCount++;
        }

        // Wait until all results are back.
        boolean timeout = false;
        while(results.size() != resultCount && !timeout){
            try {
                Thread.sleep(10);
                // If it's taking to long we want to stop calculating moves and just work with
                // the results we have gotten so far. This is not the best way of implementing time
                // constraints, but it just acts as a fail-safe. Ideally we want this to never occur.
                if((System.currentTimeMillis() - startTime) / 1000.0 > 9.8){
                    System.err.println("A timeout occurred!");
                    timeout = true;
                }
            } catch (InterruptedException ignored) {}
        }

        // Choose the best result
        int bestMove = -1;
        if(!results.isEmpty()){
            for(Map.Entry<Integer, Integer> result : results.entrySet()){
                int eval = result.getValue();
                int move = result.getKey();
                if(isMax && eval > bestEval || !isMax && eval < bestEval){
                    bestEval = eval;
                    bestMove = move;
                }
            }
        } else {
            if(moves.size() > 0) bestMove = moves.get(0);
        }
        results.clear();

//        System.out.println("Calculating move took : " + (float)((System.currentTimeMillis() - startTime) / 1000.0) + " seconds");
        return bestMove;
    }

    /**
     * Method to get maximum depth for the minimax algorithm.
     *
     * @return maximum depth.
     */
    public int getMaxDepth() {
        return maxDepth;
    }

    /**
     * Method to change maximum depth for the minimax algorithm.
     *
     * @param maxDepth maximum depth.
     */
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    /**
     * Method to get the maximum time to calculate a move.
     *
     * @return maximum time.
     */
    public float getMaxTime() {
        return maxTime;
    }

    /**
     * Method to change the maximum time to calculate a move.
     *
     * @param maxTime maximum time.
     */
    public void setMaxTime(float maxTime) {
        this.maxTime = maxTime;
    }
}
