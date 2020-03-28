/**
 * this class does all the calculations for the game.
 * It is based on the MiniMax algorithm.
 *
 * @author      Joost Blaauwwiekel
 * @version     1.0
 */

package TicTacToe_Joost;

public class MiniMax {

    char player = 'x';
    char opponent = 'o';

    public MiniMax() { }

    /**
     * This method checks whether there are possible
     * moves left.
     *
     * @param board     the board of the game
     * @return          whether there are moves left or not
     */
    public Boolean isMovesLeft(char[][] board) {
        for(int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == '-') {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * This method evaluates the possibilities. A win situation returns
     * 10 points, a lose situation returns -10 points. Anything else
     * returns 0 points.
     *
     * @param b     the board of the game
     * @return      the amount of points after the evaluation
     */
    public int evaluate(char[][] b) {
        // Checking for Rows for X or O victory.
        for(int row = 0; row < 3; row++) {
            if(b[row][0] == b[row][1] && b[row][1] == b[row][2]) {
                if(b[row][0] == player) {
                    return 10;
                } else if(b[row][0] == opponent) {
                    return -10;
                }
            }
        }

        // Checking for Columns for X or O victory.
        for(int col = 0; col < 3; col++) {
            if(b[0][col] == b[1][col] && b[1][col] == b[2][col]) {
                if(b[0][col] == player) {
                    return 10;
                } else if(b[0][col] == opponent) {
                    return -10;
                }
            }
        }

        // Checking for Diagonals for X or O victory.
        if(b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == player) {
                return 10;
            } else if(b[0][0] == opponent) {
                return -10;
            }
        }

        if(b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == player) {
                return 10;
            } else if (b[0][2] == opponent) {
                return -10;
            }
        }

        // Else if none of them have won then return 0
        return 0;
    }

    /**
     * This is where the magic happens :)
     * This method recursively checks which move is the most
     * smart to choose.
     *
     * @param board     the board of the game
     * @param depth     how many moves the algorithm has to calculate
     * @param isMax     whether this is the maximizer's move or not
     * @return          the score for the best possible move
     */
    public int miniMax(char[][] board, int depth, Boolean isMax) {
        int score = evaluate(board);

        if(score == 10) { return score; }

        if(score == -10) { return score; }

        if(!isMovesLeft(board)) { return 0; }

        // If this maximizer's move
        if(isMax) {
            int best = -1000;

            for(int row = 0; row < 3; row++) {
                for(int col = 0; col < 3; col++) {

                    if(board[row][col] == '-') {

                        board[row][col] = player;

                        best = Math.max(best, miniMax(board, depth + 1, false));

                        board[row][col] = '-';
                    }
                }
            }
            return best;
        } else {
            // The minimizer's move
            int best = 1000;

            for(int row = 0; row < 3; row++) {
                for(int col = 0; col < 3; col++) {

                    if (board[row][col] == '-') {

                        board[row][col] = opponent;

                        best = Math.min(best, miniMax(board,
                                depth + 1, true));

                        board[row][col] = '-';
                    }
                }
            }
            return best;
        }
    }

    /**
     * This method calls the miniMax() method which return the best
     * possible move to make
     *
     * @param board     the board of the game
     * @return          a Move object with the row and column for the next move
     */
    public Move getBestMove(char[][] board) {
        int bestValue = -1000;
        Move move = new Move();
        move.row = -1;
        move.col = -1;

        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {

                if (board[row][col] == '-') {
                    board[row][col] = player;

                    int moveValue = miniMax(board, 0, false);

                    board[row][col] = '-';

                    if (moveValue > bestValue) {
                        move.row = row;
                        move.col = col;
                        bestValue = moveValue;
                    }
                }
            }
        }
        return move;
    }
}