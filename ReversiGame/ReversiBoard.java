package ReversiGame;

import GameFramework.GameBoard;

import java.util.ArrayList;

public class ReversiBoard extends GameBoard {

    public ReversiBoard(){
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
    }

    /**
     * This method sets up a testing scenario for debugging purposes. Should probably be removed eventually.
     */
    public void setTestBoard() {
        // 0 = unoccupied 1 = white 2 = black
        int[] testBoard = new int[64];
        testBoard[18] = 1;
        testBoard[19] = 1;
        testBoard[20] = 1;
        testBoard[26] = 1;
        testBoard[27] = 1;
        testBoard[28] = 2;
        testBoard[34] = 1;
        testBoard[35] = 1;
        testBoard[36] = 1;
        testBoard[37] = 2;
        testBoard[45] = 1;
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

    /**
     * This method flips a disc on the gameboard.
     * @param pos position of disc that needs to be flipped.
     */
    public void flipDisc(int pos){
        int old = getBoardPos(pos);
        switch(old){
            case 1:
                setBoardPos(pos, 2);
                break;
            case 2:
                setBoardPos(pos, 1);
                break;
            default:
                System.out.println("Tried to flip unoccupied position");
        }
    }

    /**
     * This method returns a ArrayList containing all valid moves for a player.
     * @param color the color of the player to retur valid moves for.
     * @return ArrayList of integers containing all valid moves.
     */
    public ArrayList<Integer> getMoves(int color){
        ArrayList<Integer> result = new ArrayList<>();

        for(int pos = 0; pos < getBoard().length; pos++){
            if(getBoardPos(pos) == 0){
                for(int dir = 0; dir < 8; dir++){
                    if(checkDir(dir, pos, color)){
                        result.add(pos);
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Used to call checkDir function without having to pass a boolean.
     * See next method for more.
     */
    public boolean checkDir(int dir, int pos, int color){
        return checkDir(dir, pos, color, false);
    }

    /**
     * This is a recursive method that checks if there are any flippable discs in a specific direction of a position.
     * This is used to determine if a move is valid.
     * @param dir a integer representing a direction that needs to be checked.
     * @param pos integer of the position that needs to be checked.
     * @param color integer representing the color of the player we want to check for.
     * @param flipped boolean indicating if a flippable disc has been found.
     * @return a boolean, true = there is a flippable disc in this direction,
     *                    false = there is no flippable disc in this direction.
     */
    private boolean checkDir(int dir, int pos, int color, boolean flipped){
        // Calculate position of target to check.
        int target = 0;
        switch(dir){
            case 0:
                target = pos - 9;
                break;
            case 1:
                target = pos - 8;
                break;
            case 2:
                target = pos - 7;
                break;
            case 3:
                target = pos + 1;
                break;
            case 4:
                target = pos + 9;
                break;
            case 5:
                target = pos + 8;
                break;
            case 6:
                target = pos + 7;
                break;
            case 7:
                target = pos - 1;
                break;
        }

        // Filtering out vertical edge cases.
        int vEdge = pos % 8;
        switch(vEdge){
            // Left edge
            case 0:
                if(dir == 6 || dir == 7 || dir == 0){
                    return false;
                }
                break;
            // Right edge
            case 7:
                if(dir == 2 || dir == 3 || dir == 4){
                    return false;
                }
                break;
        }

        // Filtering out horizontal edge cases.
        if(pos < 8){
            if(dir == 0 || dir == 1 || dir == 2){
                return false;
            }
        } else if(pos > 55){
            if(dir == 4 || dir == 5 || dir == 6){
                return false;
            }
        }

        // Recursive part that checks if there is a flippable disc in direction.
        int targetState = getBoardPos(target);
        if(targetState == 3 - color){
            if(checkDir(dir, target, color, true)){
                return true;
            }
        } else if(targetState == color && flipped){
            return true;
        }

        return false;
    }
}
