package project.gamemodules.reversigame;

import project.gameframework.GameBoardLogic;
import project.gameframework.aistrategies.MinimaxStrategy;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReversiMinimaxStrategy extends MinimaxStrategy {

    private Map<Integer, Integer> results = new ConcurrentHashMap<>();
    private int depth = 3;

    @Override
    public int getBestMove(GameBoardLogic board, int player) {
        long startTime = System.currentTimeMillis();
        ReversiBoardLogic reversiBoard = (ReversiBoardLogic) board;
        ReversiGameLogic logic = new ReversiGameLogic();
        logic.setBoard(reversiBoard);

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
        for(int move : logic.getMoves(player)){
            // Generate a temp board and do the move
            ReversiBoardLogic newBoard = new ReversiBoardLogic();
            newBoard.setBoard(reversiBoard.getBoard());
            ReversiGameLogic newLogic = new ReversiGameLogic();
            newLogic.setBoard(newBoard);
            newLogic.doMove(move, player);

            // Give the new board to a minimax worker
            ReversiMinimaxWorker worker = new ReversiMinimaxWorker(newBoard, depth, !isMax, move, results);
            Thread thread = new Thread(worker);
            thread.start();

            resultCount++;
        }

        // Wait until all results are back
        boolean timeout = false;
        while(results.size() != resultCount && !timeout){
            try {
                Thread.sleep(10);
                // If it's taking to long we want to stop calculating moves and just work with
                // the results we have gotten so far. This is not the best way of implementing time
                // constraints, but it just acts as a fail-safe. Ideally we want this to never occur.
                if((System.currentTimeMillis() - startTime) / 1000.0 > 9.7){
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
            ArrayList<Integer> moves = logic.getMoves(player);
            if(moves.size() > 0) bestMove = logic.getMoves(player).get(0);
        }
        results.clear();

//        System.out.println("Calculating move took : " + (float)((System.currentTimeMillis() - startTime) / 1000.0) + " seconds");
        return bestMove;
    }
}
