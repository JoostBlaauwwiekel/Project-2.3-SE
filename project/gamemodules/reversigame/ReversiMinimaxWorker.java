package project.gamemodules.reversigame;

import project.gameframework.GameBoardLogic;

import java.util.Map;

public class ReversiMinimaxWorker implements Runnable{

    private GameBoardLogic board;
    private int depth;
    private boolean isMax;
    private int move;
    private Map<Integer, Integer> result;

    int temp1;
    int temp2;
    int temp3;

    ReversiMinimaxWorker(GameBoardLogic board, int depth, boolean isMax, int move, Map<Integer, Integer> result){
        this.board = board;
        this.depth = depth;
        this.isMax = isMax;
        this.move = move;
        this.result = result;
    }

    @Override
    public void run() {
        int eval = minimax(board, depth, isMax, -10000, 10000);
        result.put(move, eval);
//        System.out.println("Thread for move : " + move + " finished");
//        System.out.println("Stability : " + temp1);
//        System.out.println("Mobility : " + temp2);
//        System.out.println("Bias : " + temp3);
    }

    private int minimax(GameBoardLogic board, int depth, boolean isMax, int alpha, int beta) {
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

        // TODO: test if it is better without returning if no moves
        if(depth == 0 || logic.getMoves(player).size() == 0){
//        if(depth == 0){
            return evaluate(board);
        }

        for(int move : logic.getMoves(player)){
            ReversiBoardLogic newBoard = new ReversiBoardLogic();
            newBoard.setBoard(board.getBoard());
            ReversiGameLogic tempGame = new ReversiGameLogic();
            tempGame.setBoard(newBoard);
            tempGame.doMove(move, player);

            int eval;
            if(depth == 1 && isThreat(move, board)){
                eval = minimax(newBoard, depth, !isMax, alpha, beta);
            } else {
                eval = minimax(newBoard, depth-1, !isMax, alpha, beta);
            }

            if(isMax){
                if(eval > bestEval) bestEval = eval;
                if(eval > alpha) alpha = eval;
            } else {
                if(eval < bestEval) bestEval = eval;
                if(eval < beta) beta = eval;
            }
            if(beta <= alpha) break;
        }
        return bestEval;
    }

    private int evaluate(GameBoardLogic board){
        ReversiBoardLogic reversiBoard = (ReversiBoardLogic) board;
        ReversiGameLogic logic = new ReversiGameLogic();
        logic.setBoard(board);

        int turn = reversiBoard.getDiscCount(1) + reversiBoard.getDiscCount(2);
        if(logic.getMoves(1).size() == 0 && logic.getMoves(2).size() == 0 && turn > 50){
            int result = reversiBoard.getDiscCount(1) - reversiBoard.getDiscCount(2);
            if(result < 0){
                result -= 5000;
            } else if(result > 0){
                result += 5000;
            }
            return result;
        }

        int stability = logic.getStableDiscs(board, 1) - logic.getStableDiscs(board, 2);
        int mobility = logic.getPossibleFlips(board, 1) - logic.getPossibleFlips(board, 2);
        // int mobility = logic.getMoves(1).size() - logic.getMoves(2).size();

//        return (int)(stability * 5.5 + mobility * 1.9) + getBias(board);
//        temp1 = (int)((stability * turn) / 20.0);
//        temp2 = mobility * 20;
//        temp3 = getBias(board) * 2;
        // These numbers have been found through 50000+ tests
        return (int)((stability * turn) / 12 + mobility * 1.9) + (getBias(board));
    }

    /**
     * This method checks if a move is a threatening move. This is used by our AI to determine if it
     * should search deeper.
     * @param move the move that should be checked.
     * @return whether a move is a threat or not.
     */
    private boolean isThreat(int move, GameBoardLogic board){
        // Corners
        if(move == 0 || move == 7 || move == 56 || move == 63){
            return true;
        }
        // Adjacent to corners
        if(move == 1 || move == 6 || move == 8 || move == 9|| move == 14|| move == 15 ||
                move == 48 || move == 49 || move == 54 || move == 55|| move == 57|| move == 62){
            return true;
        }
        // 1 or less moves left
//        ReversiGameLogic logic = new ReversiGameLogic();
//        logic.setBoard(board);
//        if(logic.gameOver() == 0){
//            if(logic.getMoves(1).size() <= 1 || logic.getMoves(0).size() <= 1){
//                return true;
//            }
//        }
        return false;
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
