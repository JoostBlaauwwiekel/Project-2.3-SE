package project.gameframework.aistrategies;

import project.gameframework.GameAI;
import project.gameframework.GameBoardLogic;

public abstract class MinimaxStrategy implements GameAI {

    /**
     * This method should iterate the valid moves and it should determine which move currently is the best move
     * to make. This method should use the computeAlgorithm() method. Which is an implementation of the algorithm or AI
     * used. (E.g. Minimax, Negamax, etc).
     *
     * @return the best move.
     */
    public abstract int getBestMove(GameBoardLogic board, int player);

}
