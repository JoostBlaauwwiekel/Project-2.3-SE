package Project.GameFramework;

public abstract class GameBoard {

    // This variable stores all states within the gameBoard
    public int[] board;

    /**
     * This method should initialise the gameBoard.
     */
    public abstract void initBoard();

    /**
     * This method should print the board. This is used to simplify debugging.
     */
    public abstract void printBoard();

    /**
     * This method checks if a position on the
     * board is occupied.
     *
     * @param pos       position on the gameboard
     * @return          return whether a position is occupied or not
     */
    public boolean isValid(int pos) {
        return board[pos] == 0;
    }

    /**
     * This method is used to set the state of a position within the board
     * @param pos the position within the board that will be changed.
     * @param state the state it should be changed to.
     */
    public void setBoardPos(int pos, int state){
        board[pos] = state;
    }

    /**
     * This method is used to return the state of a position within the board.
     * @param pos the position in the board that needs to be returned.
     * @return int that represents the state of a position.
     */
    public int getBoardPos(int pos){
        return board[pos];
    }

    /**
     * This method is used to return the board.
     * @return int array that represents our game board.
     */
    public int[] getBoard(){
        return board;
    }

    /**
     * This method is used to set the board. This should always be used on board initialisation.
     * @param newBoard a int array that represents the new game board.
     */
    public void setBoard(int[] newBoard){
        board = newBoard;
    }
}
