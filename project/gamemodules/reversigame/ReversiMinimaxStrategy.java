package project.gamemodules.reversigame;

import project.gameframework.GameBoardLogic;
import project.gameframework.aistrategies.MinimaxStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReversiMinimaxStrategy extends MinimaxStrategy {

    private Map<Integer, Integer> results = new ConcurrentHashMap<>();
    private int depth = 5;

    @Override
    public int evaluate(GameBoardLogic board){
        ReversiGameLogic logic = new ReversiGameLogic();
        logic.setBoard(board);

        //TODO: For some reason it keeps seeing game over possibilities at the start of the game
        // I need to figure out why but for now I took this code out for a temporary fix.
//        ReversiBoardLogic reversiBoard = (ReversiBoardLogic) board;
//        if(logic.getMoves(1).size() == 0 && logic.getMoves(2).size() == 0){
//            int result = reversiBoard.getDiscCount(1) - reversiBoard.getDiscCount(2);
//            if(result > 0){
//                result += 5000;
//            } else if(result < 0){
//                result -= 5000;
//            }
//            return result;
//        }
        int stability = logic.getStableDiscs(board, 1) - logic.getStableDiscs(board, 2);
        int mobility = logic.getPossibleFlips(board, 1) - logic.getPossibleFlips(board, 2);

        return (int)(stability * 5.5 + mobility) + getBias(board);
    }

    @Override
    public int getBestMove(GameBoardLogic board, int player) {
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
        // TODO: 10 second timer to choose if calculating takes to long
        while(results.size() != resultCount){
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {}
        }

        // Choose the best result
        int bestMove = -1;
        for(Map.Entry<Integer, Integer> result : results.entrySet()){
            int eval = result.getValue();
            int move = result.getKey();
            if(isMax && eval > bestEval || !isMax && eval < bestEval){
                bestEval = eval;
                bestMove = move;
            }
//            System.out.println("Move " + move + " eval:" + eval);
        }
        results.clear();

        return bestMove;
    }

    @Override
    public int miniMax(GameBoardLogic board, int depth, boolean isMax) {
        return 0;
    }

    /**
     * This method calculates a bias value. The bias value is used to add weight
     * to the evaluation of certain moves.
     * @return the bias value
     */
    private int getBias(GameBoardLogic board){
        int bias = 0;

        if(board.getBoardPos(0) == 1){
            bias += 50;
        } else if(board.getBoardPos(0) == 2){
            bias -= 50;
        }
        if(board.getBoardPos(7) == 1){
            bias += 50;
        } else if(board.getBoardPos(7) == 2){
            bias -= 50;
        }
        if(board.getBoardPos(56) == 1){
            bias += 50;
        } else if(board.getBoardPos(56) == 2){
            bias -= 50;
        }
        if(board.getBoardPos(63) == 1){
            bias += 50;
        } else if(board.getBoardPos(63) == 2){
            bias -= 50;
        }

        return bias;
    }
}
