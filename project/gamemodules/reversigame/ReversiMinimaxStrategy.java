package project.gamemodules.reversigame;

import project.gameframework.GameBoardLogic;
import project.gameframework.aistrategies.MinimaxStrategy;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ReversiMinimaxStrategy extends MinimaxStrategy {

    // Maximimum depth for the minimax algorithm.
    private int maxDepth;

    // Used for generating random moves.
    private Random random = new Random();

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

        // Change AI behaviour according to difficulty
        switch(super.getDifficulty()){
            // EASY - just random moves.
            case 0:
                return getRandomValidMove(board, player);
            // MEDIUM - minimax but very low depth.
            case 1:
                maxDepth = 1;
                break;
            // HARD - minimax on highest possible depth.
            case 2:
                maxDepth = super.getMaxTime() > 9.0 ? 5 : 4;
                break;
        }

        // Change depth dynamically.
        int depth;
        if(moves.size() > 6){
            depth = maxDepth - 1;
        } else if(moves.size() > 3){
            depth = maxDepth;
        }else {
            depth = maxDepth + 1;
        }

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
                if((System.currentTimeMillis() - startTime) / 1000.0 > (super.getMaxTime() - 0.2)){
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
            bestMove = moves.size() > 0 ? moves.get(0) : -1;
        }
        results.clear();

        return bestMove;
    }

    /**
     * This method returns a random valid move.
     *
     * @param board the board that is used.
     * @param player the player to find for.
     * @return the random move.
     */
    private int getRandomValidMove(GameBoardLogic board, int player){
        ReversiGameLogic logic = new ReversiGameLogic();
        logic.setBoard(board);
        ArrayList<Integer> moves = logic.getMoves(player);

        if(moves.size() > 0){
            int choice = random.nextInt(moves.size());
            return moves.get(choice);
        } else {
            return -1;
        }
    }
}
