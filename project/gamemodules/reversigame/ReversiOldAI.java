package project.gamemodules.reversigame;

import project.gameframework.GameBoardLogic;
import project.gameframework.aistrategies.MinimaxStrategy;

public class ReversiOldAI extends MinimaxStrategy {
    /**
     * This old AI is still here to test against.
     */

    public int evaluate(GameBoardLogic board) {
        ReversiBoardLogic reversiBoard = (ReversiBoardLogic) board;
        return reversiBoard.getDiscCount(1) - reversiBoard.getDiscCount(2);
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

        int bestMove = -1;
        for(int move : logic.getMoves(player)){
            ReversiBoardLogic newBoard = new ReversiBoardLogic();
            newBoard.setBoard(reversiBoard.getBoard());
            ReversiGameLogic newLogic = new ReversiGameLogic();
            newLogic.setBoard(newBoard);
            newLogic.doMove(move, player);
            int eval = miniMax(newBoard, 5, !isMax);
            eval += bias(move, isMax);

            if(isMax && eval > bestEval || !isMax && eval < bestEval){
                bestEval = eval;
                bestMove = move;
            }
        }

        return bestMove;
    }

    public int miniMax(GameBoardLogic board, int depth, boolean isMax) {
        int player;
        int bestEval;
        if(isMax){
            bestEval = -10000;
            player = 1;
        } else {
            bestEval = 10000;
            player = 2;
        }

        ReversiGameLogic logic = new ReversiGameLogic();
        logic.setBoard(board);

        if(depth == 0 || logic.getMoves(player).size() == 0){
            return evaluate(board);
        }

        for(int move : logic.getMoves(player)){
            ReversiBoardLogic newBoard = new ReversiBoardLogic();
            newBoard.setBoard(board.getBoard());
            ReversiGameLogic tempGame = new ReversiGameLogic();
            tempGame.setBoard(newBoard);
            tempGame.doMove(move, player);
            int eval = miniMax(newBoard, depth-1, isMax);
            eval += bias(move, isMax);
            if(isMax){
                if(eval > bestEval){
                    bestEval = eval;
                }
            } else {
                if(eval < bestEval){
                    bestEval = eval;
                }
            }
        }
        return bestEval;
    }

    /**
     * This method calculates a bias value. The bias value is used to add weight
     * to the evaluation of certain moves.
     * @param move the move to calculate the bias for
     * @param isMax if the player is maximizing
     * @return the bias value
     */
    private int bias(int move, boolean isMax){
        int bias = 0;

        // Bias for corners
        if(move == 0 || move == 7 || move == 56 || move == 63){
            bias += 7;
        }
        // Bias for edges
        else if(move == 8 || move == 16 || move == 24 || move == 32 || move == 40 || move == 48 ||
                move == 1 || move == 2 || move == 3 || move == 4 || move == 5 || move == 6 ||
                move == 15 || move == 23 || move == 31 || move == 39 || move == 47 || move == 55 ||
                move == 57 || move == 58 || move == 59 || move == 60 || move == 61 || move == 62){
            bias += -5;
        }

        if(!isMax){
            bias = -bias;
        }

        return bias;
    }
}
