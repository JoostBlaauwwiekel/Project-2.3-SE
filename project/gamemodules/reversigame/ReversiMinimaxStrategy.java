package project.gamemodules.reversigame;

import project.gameframework.GameBoardLogic;
import project.gameframework.aistrategies.MinimaxStrategy;

public class ReversiMinimaxStrategy extends MinimaxStrategy {
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
            int eval = miniMax(newBoard, 6, isMax);
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
        //TODO: Deze hele methode is echt heel slecht door gebrek aan hersencellen maar ik fix het later

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

        if(isMax){
            for(int move : logic.getMoves(player)){
                ReversiBoardLogic newBoard = new ReversiBoardLogic();
                newBoard.setBoard(board.getBoard());
                ReversiGameLogic tempGame = new ReversiGameLogic();
                tempGame.setBoard(newBoard);
                tempGame.doMove(move, player);
                int eval = miniMax(newBoard, depth-1, false);
//                System.out.println("Depth: " + depth + " Move: " + move + " Eval: " + eval);
                if(eval > bestEval){
                    bestEval = eval;
                }
            }
            return bestEval;
        } else {
            for(int move : logic.getMoves(player)){
                ReversiBoardLogic newBoard = new ReversiBoardLogic();
                newBoard.setBoard(board.getBoard());
                ReversiGameLogic tempGame = new ReversiGameLogic();
                tempGame.setBoard(newBoard);
                tempGame.doMove(move, player);
                int eval = miniMax(newBoard, depth-1, true);
//                System.out.println("Depth: " + depth + " Move: " + move + " Eval: " + eval);
                if(eval < bestEval){
                    bestEval = eval;
                }
            }
            return bestEval;
        }
    }
}
