package project.gameframework.aistrategies;

import project.gameframework.GameAI;
import project.gameframework.GameBoardLogic;

import java.util.List;

public abstract class NeuralNetworkStrategy <E> implements GameAI {

    /**
     * This method should iterate the whole game board and it should determine which move currently is the best move
     * to make. This method should use the computeAlgorithm() method. Which is an implementation of the algorithm or AI
     * used. (E.g. Neural Network, Tabular Q learning, etc).
     *
     * @return the best move.
     */
    public abstract int getBestMove(GameBoardLogic board);

}
