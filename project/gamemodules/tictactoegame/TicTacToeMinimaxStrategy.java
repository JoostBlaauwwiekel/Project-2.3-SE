/**
 * Inspiration for this code: https://www.geeksforgeeks.org/minimax-algorithm-in-game-theory-set-3-tic-tac-toe-ai-finding-optimal-move/
 *
 * this class does all the calculations for the game.
 * It is based on the MiniMax algorithm.
 *
 * @author Joost Blaauwwiekel
 * @version 1.0
 */

package project.gamemodules.tictactoegame;

import project.gameframework.GameBoardLogic;
import project.gameframework.aistrategies.MinimaxStrategy;
import project.mvc.view.gameboard.TicTacToeBoard;

public class TicTacToeMinimaxStrategy extends MinimaxStrategy {

    /**
     * This method evaluates the possibilities. A win situation returns
     * 10 points, a lose situation returns -10 points. Anything else
     * returns 0 points.
     *
     * @param b         the board of the game
     * @return          the amount of points after the evaluation
     */
    public int evaluate(GameBoardLogic b) {
        int[] board = b.getBoard();
        int rowCounter = 0;
        for(int row = 0; row < 3; row++) {
            // 0 - 1 : 1 - 2
            if(board[rowCounter] == board[rowCounter + 1] && board[rowCounter + 1] == board[rowCounter + 2]) {
                if(board[rowCounter] == 1) {
                    return 1;
                } else if(board[rowCounter] == 2) {
                    return -1;
                }
            }
            rowCounter = rowCounter + 3;
        }

        int colCounter = 0;
        for(int col = 0; col < 3; col++) {
            // 1 - 4 : 4 - 7
            if(board[colCounter] == board[colCounter + 3] && board[colCounter + 3] == board[colCounter + 6]) {
                if(board[colCounter] == 1) {
                    return 1;
                } else if(board[colCounter] == 2) {
                    return -1;
                }
            }
            colCounter = colCounter + 1;
        }

        // Checking for diagonals
        if(board[0] == board[4] && board[4] == board[8]) {
            if (board[0] == 1) {
                return 1;
            } else if(board[0] == 2) {
                return -1;
            }
        }

        // Checking for diagonals
        if(board[2] == board[4] && board[4] == board[6]) {
            if (board[2] == 1) {
                return 1;
            } else if(board[2] == 2) {
                return -1;
            }
        }
        // if no one has won yet return 0
        return 0;
    }

    /**
     *
     * @param b         The gameBoard, used to get the int[] board layout
     * @param p    This variable is ignored by this algorithm
     * @return          int with the best possible position
     */
    public int getBestMove(GameBoardLogic b, int p) {
        TicTacToeBoardLogic ticTacToeBoard = (TicTacToeBoardLogic) b;
        int bestMoveValue;
        int bestMove = -1;
        boolean isMax;

        if(p == 1){
            bestMoveValue = -10;
            isMax = true;
        } else {
            isMax = false;
            bestMoveValue = 10;
        }

        TicTacToeGameLogic logic = new TicTacToeGameLogic();
        logic.setBoard(b);
        System.out.println();
        for(int move : logic.getMoves(p)){
            TicTacToeBoardLogic tempBoard = new TicTacToeBoardLogic();
            tempBoard.setBoard(ticTacToeBoard.getBoard());
            TicTacToeGameLogic tempLogic = new TicTacToeGameLogic();
            tempLogic.setBoard(tempBoard);
            tempLogic.doMove(move, p);
            int moveValue = miniMax(tempBoard, 5, !isMax);
            if(isMax && moveValue > bestMoveValue || !isMax && moveValue < bestMoveValue){
                bestMoveValue = moveValue;
                bestMove = move;
            }
        }
        return bestMove;
    }

    /**
     * This is where the magic happens :)
     * This method recursively checks which move is the most
     * smart to choose.
     *
     * @param b         The gameBoard, used to get the int[] board layout
     * @param depth     the depth of the binary tree (number of moves to be calculated).
     * @param isMax     true when the current player is the maximizer, false when the current player is the minimizer.
     * @return
     */
    public int miniMax(GameBoardLogic b, int depth, boolean isMax) {
        int player;
        int bestEval;
        if(isMax){
            bestEval = -10;
            player = 1;
        } else {
            bestEval = 10;
            player = 2;
        }

        TicTacToeGameLogic logic = new TicTacToeGameLogic();
        logic.setBoard(b);

        int score = evaluate(b);
        if(depth == 0 || logic.gameOver() != 0) {
            return score;
        }

        for(int move : logic.getMoves(player)){
            TicTacToeBoardLogic newBoard = new TicTacToeBoardLogic();
            newBoard.setBoard(b.getBoard());
            TicTacToeGameLogic tempGame = new TicTacToeGameLogic();
            tempGame.setBoard(newBoard);
            tempGame.doMove(move, player);
            if(isMax){
                int eval = miniMax(newBoard, depth-1, false);
                if(eval > bestEval){
                    bestEval = eval;
                }
            } else {
                int eval = miniMax(newBoard, depth-1, true);
                if(eval < bestEval){
                    bestEval = eval;
                }
            }
        }
        return bestEval;
    }
}

