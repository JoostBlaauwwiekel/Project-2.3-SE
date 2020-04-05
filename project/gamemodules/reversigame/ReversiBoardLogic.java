package project.gamemodules.reversigame;

import project.gameframework.GameBoardLogic;

public class ReversiBoardLogic extends GameBoardLogic {

    public ReversiBoardLogic(){
        initBoard();
    }

    @Override
    public void initBoard() {
        // 0 = unoccupied 1 = white 2 = black
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
            // Print the position of the first spot in the gameboard, so it's easier to identify the positions
            // when debugging. Also print a extra " " for the first 2 numbers so they line up with the others.
            if(row <= 1){
                System.out.print(" ");
            }
            System.out.print(row * 8 + " - ");

            for(int col=0; col < 8; col++){
                System.out.print(getBoardPos(i) + " ");
                i++;
            }
            System.out.println();
        }
        System.out.println("-=-=-=-=-=-=-=-=-=-=-");
    }

    /**
     * This method sets up a testing scenario for debugging purposes. Should probably be removed eventually.
     */
    public void setTestBoard() {
        // 0 = unoccupied 1 = white 2 = black
        int[] testBoard = new int[64];
        testBoard[0] = 1;
        testBoard[1] = 1;
        testBoard[2] = 1;
        testBoard[3] = 1;
        testBoard[8] = 1;
        testBoard[9] = 1;
        testBoard[10] = 1;
        testBoard[11] = 1;
        testBoard[12] = 1;
        testBoard[16] = 1;
        testBoard[17] = 1;
        testBoard[24] = 1;
        testBoard[25] = 1;
        testBoard[26] = 1;

        testBoard[32] = 1;
        testBoard[40] = 1;
        testBoard[48] = 1;
        testBoard[56] = 1;
        testBoard[57] = 1;
        testBoard[58] = 1;
        testBoard[59] = 1;
        testBoard[49] = 1;
        testBoard[50] = 1;
        testBoard[51] = 1;

        testBoard[39] = 1;
        testBoard[47] = 1;
        testBoard[46] = 1;
        testBoard[55] = 1;
        testBoard[54] = 1;
        testBoard[53] = 1;
        testBoard[52] = 1;
        testBoard[61] = 1;
        testBoard[62] = 1;
        testBoard[63] = 1;
        setBoard(testBoard);
    }

    /**
     * This method sets up a testing scenario for debugging purposes. Should probably be removed eventually.
     * This method also contains very Ａ Ｅ Ｓ Ｔ Ｈ Ｅ Ｔ Ｉ Ｃ if statements.
     */
    public void setTestBoard2() {
        int[] testBoard = new int[64];
        for(int i =0; i < testBoard.length; i++){
            if(i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 23 || i == 27 || i == 29 || i == 30 || i == 31 || i == 36 || i == 37 || i == 38 || i == 39 || i == 44 || i == 45 || i == 46 || i == 47 || i == 51 || i == 54 || i == 55 || i == 58 || i == 61 || i == 62 || i == 63 ){
                testBoard[i] = 2;
            } else if(i == 8 || i == 9 || i == 10 || i == 11 || i == 12 || i == 13 || i == 18 || i == 19 || i == 20 || i == 21 || i == 25 || i == 28 || i == 34 || i == 35 || i == 50 || i == 52 || i == 53){
                testBoard[i] = 1;
            }
        }
        setBoard(testBoard);
    }

    /**
     * This method returns the amount of discs on the board with the specified state.
     * @param state of the discs we want to count.
     * @return the amount of discs with specified state.
     */
    public int getDiscCount(int state){
        int count = 0;
        int[] board = getBoard();
        for(int i =0; i < board.length; i++){
            if(board[i] == state){
                count++;
            }
        }
        return count;
    }
}
