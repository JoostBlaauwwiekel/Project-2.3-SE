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

    public ReversiMinimaxWorker(GameBoardLogic board, int depth, boolean isMax, int move, Map<Integer, Integer> result){
        this.board = board;
        this.depth = depth;
        this.isMax = isMax;
        this.move = move;
        this.result = result;
    }

    @Override
    public void run() {
        int eval = minimax(board, depth, isMax);
        result.put(move, eval);
//        System.out.println("Thread for move : " + move + " finished");
//        System.out.println("Stability : " + temp1);
//        System.out.println("Mobility : " + temp2);
//        System.out.println("Bias : " + temp3);
    }

    private int minimax(GameBoardLogic board, int depth, boolean isMax) {
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
            int eval = minimax(newBoard, depth-1, isMax);

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

    private int evaluate(GameBoardLogic board){
        ReversiBoardLogic reversiBoard = (ReversiBoardLogic) board;
        ReversiGameLogic logic = new ReversiGameLogic();
        logic.setBoard(board);

//        int turn = reversiBoard.getDiscCount(1) + reversiBoard.getDiscCount(2);
        int stability = logic.getStableDiscs(board, 1) - logic.getStableDiscs(board, 2);
        int mobility = logic.getPossibleFlips(board, 1) - logic.getPossibleFlips(board, 2);
        // int mobility = logic.getMoves(1).size() - logic.getMoves(2).size();

        return (int)(stability * 5.5 + mobility) + getBias(board);
//        temp1 = (int)((stability * turn) / 20.0);
//        temp2 = mobility * 20;
//        temp3 = getBias(board) * 2;
//        return (int)((stability * turn) / 20.0 + mobility * 5) + (getBias(board) * 2);
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
