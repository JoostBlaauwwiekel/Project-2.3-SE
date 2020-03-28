package ReversiGame;

import GameFramework.GameBoard;

public class ReversiBoard extends GameBoard {

    public ReversiBoard(){
        initBoard();
    }

    @Override
    public void initBoard() {
        int[] defaultBoard = new int[64];
        for(int i =0; i < defaultBoard.length; i++){
            defaultBoard[0] = 0;
        }
        defaultBoard[27] = 1;
        defaultBoard[28] = 2;
        defaultBoard[35] = 2;
        defaultBoard[36] = 1;
        setBoard(defaultBoard);
    }

    @Override
    public void printBoard() {
        int i = 0;
        for(int row=0; row < 8; row++){
            for(int col=0; col < 8; col++){
                System.out.print(getBoardPos(i) + " ");
                i++;
            }
            System.out.println();
        }
    }
}
