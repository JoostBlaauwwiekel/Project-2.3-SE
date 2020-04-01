package Project.GameModules.ReversiGame;

import Project.GameFramework.AIStrategies.MinimaxStrategy;
import Project.GameFramework.GameBoard;

public class ReversiMiniMaxStrategy extends MinimaxStrategy {
    @Override
    public int evaluate(GameBoard board) {
        ReversiBoard reversiBoard = (ReversiBoard) board;
        return reversiBoard.getDiscCount(1) - reversiBoard.getDiscCount(2);
    }

    @Override
    public int getBestMove(GameBoard board, int player) {
        // bestEval = -infinity;
        // for move : validMoves:
        //      eval = computeAlgorithm
        //          if eval > bestEval
        ReversiBoard reversiBoard = (ReversiBoard) board;
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
            ReversiBoard newBoard = new ReversiBoard();
            newBoard.setBoard(reversiBoard.getBoard());
            ReversiGameLogic newLogic = new ReversiGameLogic();
            newLogic.setBoard(newBoard);
            newLogic.doMove(move, player);
            int eval = miniMax(newBoard, 2, isMax);
            System.out.println("Move: " + move + " Eval: " + eval);
            if(isMax && eval > bestEval || !isMax && eval < bestEval){
                bestEval = eval;
                bestMove = move;
            }
        }

        return bestMove;
    }

    @Override
    public int miniMax(GameBoard board, int depth, boolean isMax) {
        // basic test:
        // bestMove = -infinity
        // for move : validMoves:
        //      if evaluate(move) > bestmove
        //          bestMove = move
        // return bestMove

        // if depth == 0 or board.finished;
        //      return evaluate(board)
        //
        // if isMax
        //      bestEval = -infinity
        //      for move: validMoves:
        //         newBoard = new ReversiBoard()
        //         newBoard.setBoard(board.getBoard())
        //         gameInstance = new ReversiGameLogic.setBoard(newBoard)
        //         gameInstance.doMove(move)
        //         eval = miniMax(newBoard, depth-1, false)
        //         if eval > bestEval:
        //             bestEval = eval
        //          return maxEval
        // else:
        //     andersom

//        ReversiGameLogic logic = new ReversiGameLogic();
//        ReversiBoard reversiBoard = (ReversiBoard) board;
//        logic.setBoard(reversiBoard);
        return evaluate(board);
    }
}
