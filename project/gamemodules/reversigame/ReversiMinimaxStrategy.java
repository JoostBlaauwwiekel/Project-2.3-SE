package project.gamemodules.reversigame;

import project.gameframework.GameBoardLogic;
import project.gameframework.aistrategies.MinimaxStrategy;

public class ReversiMinimaxStrategy extends MinimaxStrategy {
    private int depth = 5;

    // These 'magic values' have been found through thousands of automated tests.
    // midCornerBias and midBias seems to be unnecessary when cornerBias is used.
    // Best values so far: 7, 0, -5, 0, ?
    private int cornerBias = 7;
    private int midCornerBias = 0;
    private int edgeBias = -5;
    private int midBias = 0;
    private int insideCornerBias = 0;

    @Override
    public int evaluate(GameBoardLogic board){
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
//        int mobility = logic.getMoves(1).size() - logic.getMoves(2).size();

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

        int bestMove = -1;
        for(int move : logic.getMoves(player)){
            ReversiBoardLogic newBoard = new ReversiBoardLogic();
            newBoard.setBoard(reversiBoard.getBoard());
            ReversiGameLogic newLogic = new ReversiGameLogic();
            newLogic.setBoard(newBoard);
            newLogic.doMove(move, player);
            int eval = miniMax(newBoard, depth, !isMax);

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
            return evaluate(board);
        }

        for(int move : logic.getMoves(player)){
            ReversiBoardLogic newBoard = new ReversiBoardLogic();
            newBoard.setBoard(board.getBoard());
            ReversiGameLogic tempGame = new ReversiGameLogic();
            tempGame.setBoard(newBoard);
            tempGame.doMove(move, player);
            int eval = miniMax(newBoard, depth-1, isMax);

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
