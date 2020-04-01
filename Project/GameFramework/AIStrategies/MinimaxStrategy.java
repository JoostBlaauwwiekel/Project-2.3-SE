package Project.GameFramework.AIStrategies;

import Project.GameFramework.GameAI;
import Project.GameFramework.GameBoard;

public abstract class MinimaxStrategy implements GameAI {

    /**
     * This method should evaluate the board passed as a parameter. The current state of the board could either be
     * a winning state, a losing state or a neutral state.
     *
     * @param board the board about to be checked.
     * @return a given value which indicates the state of the board.
     */
    public abstract int evaluate(GameBoard board);

    /**
     * This method should iterate the whole game board and it should determine which move currently is the best move
     * to make. This method should use the computeAlgorithm() method. Which is an implementation of the algorithm or AI
     * used. (E.g. Minimax, Neural Network, Tabular Q learning, etc).
     *
     * @return the best move.
     */
    public abstract int getBestMove(GameBoard board, int player);

    /**
     * This method calls the miniMax() method. The main logic of the AI will be implemented there.
     *
     * @return the move that has been determined to be the best.
     */
    public int computeAlgorithm(GameBoard board, int depth){
        return miniMax(board, depth, false);
    }

    /**
     * This method checks the board and determines which move is currently the best move to make (for one iteration).
     * This can be accomplished by implementing a Top-Down approach.
     *
     * @param board the board to be checked.
     * @param depth the depth of the binary tree (number of moves to be calculated).
     * @param isMax true when the current player is the maximizer, false when the current player is the minimizer.
     * @return the score for the best move.
     */
    public abstract int miniMax(GameBoard board, int depth, boolean isMax);
}
