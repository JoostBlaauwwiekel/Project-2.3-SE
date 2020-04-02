package project.gamemodules.tictactoegame;

import project.gameframework.GameBoardLogic;

public class TicTacToeBoardLogic extends GameBoardLogic {

    private int[] board;

    public TicTacToeBoardLogic(){
        initBoard();
    }

    @Override
    public void initBoard() {
        int[] defaultBoard = new int[9];
        for(int i =0; i < defaultBoard.length; i++){
            defaultBoard[0] = 0;
        }
        setBoard(defaultBoard);
        this.board = getBoard();
    }

    @Override
    public void setBoardPos(int pos, int state) {
        if(isValid(pos)) {
            board[pos] = state;
        }
    }

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

    @Override
    public void printBoard() {
        int[] board = getBoard();
        System.out.println("-------------");
        System.out.println("| " + board[0] + " | " + board[1] + " | " + board[2] + " |");
        System.out.println("-------------");
        System.out.println("| " + board[3] + " | " + board[4] + " | " + board[5] + " |");
        System.out.println("-------------");
        System.out.println("| " + board[6] + " | " + board[7] + " | " + board[8] + " |");
        System.out.println("-------------");
    }

    public int[] getTicTacToeBoard() {
        return board;
    }
}
