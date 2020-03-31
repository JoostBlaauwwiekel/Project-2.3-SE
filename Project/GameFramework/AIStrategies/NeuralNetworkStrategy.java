package Project_SE_Periode3.Project.GameFramework.AIStrategies;

import Project_SE_Periode3.Project.GameFramework.GameAI;

import java.util.List;

public abstract class NeuralNetworkStrategy <E> implements GameAI {

    /**
     * This method should evaluate the board passed as a parameter. The current state of the board could either be
     * a winning state, a losing state or a neutral state.
     *
     * @param board the board about to be checked.
     * @return a given value which indicates the state of the board.
     */
    public abstract int evaluate(int[] board);

    /**
     * This method should iterate the whole game board and it should determine which move currently is the best move
     * to make. This method should use the computeAlgorithm() method. Which is an implementation of the algorithm or AI
     * used. (E.g. Minimax, Neural Network, Tabular Q learning, etc).
     *
     * @return the best move.
     */
    public abstract int getBestMove(int[] board);

    /**
     * This method calls the miniMax() method. The main logic of the AI will be implemented there.
     *
     * @return the move that has been determined to be the best.
     */
    public int computeAlgorithm(int[] board, int depth){
        return determineMove(board, depth);
    }

    /**
     * This method should implement the main logic of the AI. It determines which move is currently the best move
     * to make (for one iteration). And it returns the move.
     *
     * @param board the board to be checked.
     * @param depth the depth of the binary tree (number of moves to be calculated).
     * @return currently the best move to make.
     */
    public abstract int determineMove(int[] board, int depth);

    /**
     * This method should be implemented that the AI can be trained with a given dataset.
     *
     * @param board the board which the AI uses.
     * @param depth the depth of the tree (number of moves to be calculated).
     * @param dataset a generic dataset which the AI can use.
     */
    public abstract void train(int[] board, int depth, List<E> dataset);
}
