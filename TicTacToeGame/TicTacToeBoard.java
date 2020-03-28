package TicTacToeGame;

import GameFramework.GameBoard;

public class TicTacToeBoard extends GameBoard {

    public TicTacToeBoard(){
        initBoard();
    }

    @Override
    public void initBoard() {
        int[] defaultBoard = new int[9];
        for(int i =0; i < defaultBoard.length; i++){
            defaultBoard[0] = 0;
        }
        setBoard(defaultBoard);
    }

    @Override
    public void printBoard() {
        int i = 0;
        for(int row=0; row < 3; row++){
            for(int col=0; col < 3; col++){
                System.out.print(getBoardPos(i) + " ");
                i++;
            }
            System.out.println();
        }
    }
}
