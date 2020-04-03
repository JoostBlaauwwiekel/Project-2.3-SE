package project.gamemodules.reversigame;

import project.gameframework.GameBoardLogic;
import project.gameframework.aistrategies.MinimaxStrategy;

public class ReversiMinimaxStrategy extends MinimaxStrategy {
    private static final int DEPTH = 4;
    private int cornerBias = 0;

    @Override
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
            bestEval = -100;
            isMax = true;
        } else {
            isMax = false;
            bestEval = 100;
        }

        int bestMove = -1;
        for(int move : logic.getMoves(player)){
            ReversiBoardLogic newBoard = new ReversiBoardLogic();
            newBoard.setBoard(reversiBoard.getBoard());
            ReversiGameLogic newLogic = new ReversiGameLogic();
            newLogic.setBoard(newBoard);
            newLogic.doMove(move, player);
            int eval = miniMax(newBoard, DEPTH, !isMax);
            eval += bias(move, isMax);

//            System.out.println("Move: " + move + " Eval: " + eval);
            if(isMax && eval > bestEval || !isMax && eval < bestEval){
                bestEval = eval;
                bestMove = move;
            }
        }

        return bestMove;
    }

    @Override
    public int miniMax(GameBoardLogic board, int depth, boolean isMax) {
        int player;
        int bestEval;
        if(isMax){
            bestEval = -100;
            player = 1;
        } else {
            bestEval = 100;
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
            bias += cornerBias;
        }
        // Bias for middle corners
        else if(move == 18 || move == 21 || move == 42 || move == 45){
            bias += 2;
        }

        if(!isMax){
            bias = -bias;
        }

        return bias;
    }

    public void setCornerbias(int bias){
        this.cornerBias = bias;
    }
}
