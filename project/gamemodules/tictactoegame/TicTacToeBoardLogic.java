package project.gamemodules.tictactoegame;

import project.gameframework.GameBoardLogic;

public class TicTacToeBoardLogic extends GameBoardLogic {

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
}
