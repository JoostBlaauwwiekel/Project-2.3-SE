package project.gamemodules.reversigame;

import project.gameframework.GameBoardLogic;
import project.gameframework.aistrategies.MinimaxStrategy;

public class ReversiMinimaxStrategy extends MinimaxStrategy {
    private int depth = 4;

    // These 'magic values' have been found through thousands of automated tests.
    // midCornerBias and midBias seems to be unnecessary when cornerBias is used.
    // Best values so far: 7, 0, -5, 0, ?
    private int cornerBias = 7;
    private int midCornerBias = 0;
    private int edgeBias = -5;
    private int midBias = 0;
    private int insideCornerBias = 0;

    @Override
    public int evaluate(GameBoardLogic board) {
        ReversiBoardLogic reversiBoard = (ReversiBoardLogic) board;
        return reversiBoard.getDiscCount(1) - reversiBoard.getDiscCount(2);
    }

    public int evaluate2(GameBoardLogic board){
        ReversiBoardLogic reversiBoard = (ReversiBoardLogic) board;
        ReversiGameLogic logic = new ReversiGameLogic();
        logic.setBoard(board);

        if(logic.getMoves(1).size() == 0 && logic.getMoves(2).size() == 0){
            int result = reversiBoard.getDiscCount(1) - reversiBoard.getDiscCount(2);
            if(result > 0){
                result += 5000;
            } else if(result < 0){
                result -= 5000;
            }
            return result;
        }
        int stability = logic.getStableDiscs(board, 1) - logic.getStableDiscs(board, 2);
        int mobility = logic.getPossibleFlips(board, 1) - logic.getPossibleFlips(board, 2);
        return stability * 6 + mobility;
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
            int eval = miniMax(newBoard, depth, !isMax);
//            eval += bias(move, isMax);

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
            bestEval = -10000;
            player = 1;
        } else {
            bestEval = 10000;
            player = 2;
        }

        ReversiGameLogic logic = new ReversiGameLogic();
        logic.setBoard(board);

        if(depth == 0 || logic.getMoves(player).size() == 0){
            return evaluate2(board);
        }

        for(int move : logic.getMoves(player)){
            ReversiBoardLogic newBoard = new ReversiBoardLogic();
            newBoard.setBoard(board.getBoard());
            ReversiGameLogic tempGame = new ReversiGameLogic();
            tempGame.setBoard(newBoard);
            tempGame.doMove(move, player);
            int eval = miniMax(newBoard, depth-1, isMax);
//            eval += bias(move, isMax);
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
            bias += midCornerBias;
        }
        // Bias for inside corners
        else if(move == 9 || move == 14 || move == 49 || move == 54){
            bias += insideCornerBias;
        }
        // Bias for edges
        else if(move == 8 || move == 16 || move == 24 || move == 32 || move == 40 || move == 48 ||
                move == 1 || move == 2 || move == 3 || move == 4 || move == 5 || move == 6 ||
                move == 15 || move == 23 || move == 31 || move == 39 || move == 47 || move == 55 ||
                move == 57 || move == 58 || move == 59 || move == 60 || move == 61 || move == 62){
            bias += edgeBias;
        }
        // Bias for middle 4
        else if(move == 27 || move == 28 || move == 35 || move == 36){
            bias += midBias;
        }

        if(!isMax){
            bias = -bias;
        }

        return bias;
    }

    public void setCornerBias(int bias){
        this.cornerBias = bias;
    }

    public void setMidCornerBias(int bias){
        this.midCornerBias = bias;
    }

    public void setEdgeBias(int bias){
        this.edgeBias = bias;
    }

    public void setMidBias(int bias){
        this.midBias = bias;
    }

    public void setInsideCornerBias(int insideCornerBias) {
        this.insideCornerBias = insideCornerBias;
    }

    public void setDepth(int depth){
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public int getCornerBias() {
        return cornerBias;
    }

    public int getMidCornerBias() {
        return midCornerBias;
    }

    public int getEdgeBias() {
        return edgeBias;
    }

    public int getMidBias() {
        return midBias;
    }

    public int getInsideCornerBias() {
        return insideCornerBias;
    }
}
