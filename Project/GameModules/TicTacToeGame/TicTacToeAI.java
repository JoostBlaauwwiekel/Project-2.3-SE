package Project.GameModules.TicTacToeGame;

import Project.GameFramework.AIStrategies.MinimaxStrategy;

public class TicTacToeAI {

    int player = 2;
    int opponent = 1;

    public int evaluate(int[] board) {
        int rowCounter = 0;
        for(int row = 0; row < 3; row++) {
            // 0 - 1 : 1 - 2
            if(board[rowCounter] == board[rowCounter + 1] && board[rowCounter + 1] == board[rowCounter + 2]) {
                if(board[rowCounter] == player) {
                    return 10;
                } else if(board[rowCounter] == opponent) {
                    return -10;
                }
            }
            rowCounter = rowCounter + 3;
        }

        int colCounter = 0;
        for(int col = 0; col < 3; col++) {
            // 1 - 4 : 4 - 7
            if(board[colCounter] == board[colCounter + 3] && board[colCounter + 3] == board[colCounter + 6]) {
                if(board[colCounter] == player) {
                    return 10;
                } else if(board[colCounter] == opponent) {
                    return -10;
                }
            }
            colCounter = colCounter + 1;
        }

        // Checking for diagonals
        if(board[0] == board[4] && board[4] == board[8]) {
            if (board[0] == player) {
                return 10;
            } else if(board[0] == opponent) {
                return -10;
            }
        }

        // Checking for diagonals
        if(board[2] == board[4] && board[4] == board[6]) {
            if (board[2] == player) {
                return 10;
            } else if(board[2] == opponent) {
                return -10;
            }
        }
        // if both of them didn't won return 0
        return 0;
    }

    public int getBestMove(int[] board) {
        int bestValue = -1000;
        int bestMove = -1;

        for(int pos = 0; pos < 9; pos++) {
            if (board[pos] == 0) {
                board[pos] = player;

                int moveValue = miniMax(board, 0, false);

                board[pos] = 0;

                if (moveValue > bestValue) {
                    bestMove = pos;
                    bestValue = moveValue;
                }
            }
        }
        return bestMove;
    }

    public int miniMax(int[] board, int depth, boolean isMax) {
        int score = evaluate(board);

        if(score == 10) { return score; }

        if(score == -10) { return score; }

        if(!hasMovesLeft(board)) { return 0; }

        // If this maximizer's move
        if(isMax) {
            int best = -1000;

            for(int pos = 0; pos < 9; pos++) {
                if(board[pos] == 0) {

                    board[pos] = player;

                    best = Math.max(best, miniMax(board, depth + 1, false));

                    board[pos] = 0;

                }
            }

            return best;
        } else {
            // The minimizer's move
            int best = 1000;

            for(int pos = 0; pos < 9; pos++) {

                if (board[pos] == 0) {

                    board[pos] = opponent;

                    best = Math.min(best, miniMax(board, depth + 1, true));

                    board[pos] = 0;
                }
            }
            return best;
        }
    }

    /**
     * This method checks whether there are possible
     * moves left.
     *
     * @param board     the board of the game
     * @return          whether there are moves left or not
     */
    public Boolean hasMovesLeft(int[] board) {
        for(int pos : board) {
            if (pos == 0) {
                return true;
            }
        }

        return false;
    }
}

