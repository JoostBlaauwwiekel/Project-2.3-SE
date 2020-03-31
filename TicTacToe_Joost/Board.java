/**
 * This class contains the board where you
 * can play tic tac toe on.
 * The board is made up of a
 * multidimensional array of chars.
 * This class handles all the board related operations.
 *
 * @author      Joost Blaauwwiekel
 * @version     1.0
 */
package Project_SE_Periode3.TicTacToe_Joost;

public class Board {
    int counter = 0;
    int[] board = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    public Board() { }

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
     * With this method you can make a move on the board
     * by putting a char in the multidimensional array.
     *
     * @param player    char 'x' or 'o'
     * @param pos       position on the gameboard
     */
    public void makeMove(int player, int pos) {
        if(isValid(pos)) {
            board[pos] = player;
        } else {
            System.out.println("Invalid move");
        }
    }

    /**
     * By calling this method it returns whether the
     * game is finished or not. It loops trough all the
     * possible ends and outputs the winner to the console.
     *
     * @return          boolean whether the game is finished
     */
    public boolean isFinished() {
        counter++;
        for (int a = 0; a < 8; a++) {
            switch (a) {
                case 0:
                    if(board[0] == board[1] && board[1] == board[2] && board[0] != 0) {
                        String s = String.valueOf(board[0]);
                        System.out.println(s + " won!");
                        return true;
                    }
                    break;
                case 1:
                    if(board[3] == board[4] && board[4] == board[5] && board[4] != 0) {
                        String s = String.valueOf(board[4]);
                        System.out.println(s + " won!");
                        return true;
                    }
                    break;
                case 2:
                    if(board[6] == board[7] && board[7] == board[8] && board[6] != 0) {
                        String s = String.valueOf(board[6]);
                        System.out.println(s + " won!");
                        return true;
                    }
                    break;
                case 3:
                    if(board[0] == board[3] && board[3] == board[6] && board[0] != 0) {
                        String s = String.valueOf(board[0]);
                        System.out.println(s + " won!");
                        return true;
                    }
                    break;
                case 4:
                    if(board[1] == board[4] && board[4] == board[7] && board[4] != 0) {
                        String s = String.valueOf(board[4]);
                        System.out.println(s + " won!");
                        return true;
                    }
                    break;
                case 5:
                    if(board[2] == board[5] && board[5] == board[8] && board[2] != 0) {
                        String s = String.valueOf(board[2]);
                        System.out.println(s + " won!");
                        return true;
                    }
                    break;
                case 6:
                    if(board[0] == board[4] && board[4] == board[8] && board[0] != 0) {
                        String s = String.valueOf(board[0]);
                        System.out.println(s + " won!");
                        return true;
                    }
                    break;
                case 7:
                    if(board[2] == board[4] && board[4] == board[6] && board[2] != 0) {
                        String s = String.valueOf(board[2]);
                        System.out.println(s + " won!");
                        return true;
                    }
                    break;
            }
        }
        if(counter == 10) {
            System.out.println("Draw!");
            return true;
        }

        return false;
    }


    /**
     * Method that prints a nice layout of the board.
     */
    public void printBoard() {
        System.out.println("-------------");
        System.out.println("| " + board[0] + " | " + board[1] + " | " + board[2] + " |");
        System.out.println("-------------");
        System.out.println("| " + board[3] + " | " + board[4] + " | " + board[5] + " |");
        System.out.println("-------------");
        System.out.println("| " + board[6] + " | " + board[7] + " | " + board[8] + " |");
        System.out.println("-------------");
    }

    /**
     * Returns the board
     *
     * @return      multidimensional array of chars
     */
    public int[] getBoard() {
        return board;
    }
}