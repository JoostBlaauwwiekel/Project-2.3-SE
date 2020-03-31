package Project.GameFramework;

import java.util.ArrayList;

/**
 * Abstract class that should be used for game logic.
 */
public abstract class GameLogic {

    private GameBoard board;

    /**
     * This method sets the gameboard that is used to play the game.
     * @param board the gameboard that will be uses.
     */
    public void setBoard(GameBoard board){
        this.board = board;
    }

    /**
     * This method is used to get to the gameboard.
     * @return GameBoard with current GameBoard.
     */
    public GameBoard getBoard(){
        return board;
    }

    /**
     * This method should check for all valid moves.
     * @param player integer representing the player that should be checked for.
     * @return ArrayList with integers containing all positions of valid moves.
     */
    public abstract ArrayList<Integer> getMoves(int player);

    /**
     * This method shold be used to make a move on the gameboard.
     * @param pos position of the move.
     * @param player player that is making the move.
     */
    public abstract void doMove(int pos, int player);

    /**
     * This method should check if the game is over, and who won the game.
     * @param player the player that should be checked for.
     * @return integer representing the player that won the game.
     */
    public abstract int gameOver();
}
