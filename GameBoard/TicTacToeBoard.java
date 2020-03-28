package GameBoard;

public class TicTacToeBoard extends GameBoard {
    int counter = 0;

    public TicTacToeBoard() {
        ticTacToeFinished = new TicTacToeFinished();
        char[][] board = {
                {'-', '-', '-'},
                {'-', '-', '-'},
                {'-', '-', '-'}
        };
    }

    /**
     * Method that prints a nice layout of the board.
     */
    public void printBoard() {
        System.out.println("-------------");
        System.out.println("| " + board[0][0] + " | " + board[0][1] + " | " + board[0][2] + " |");
        System.out.println("-------------");
        System.out.println("| " + board[1][0] + " | " + board[1][1] + " | " + board[1][2] + " |");
        System.out.println("-------------");
        System.out.println("| " + board[2][0] + " | " + board[2][1] + " | " + board[2][2] + " |");
        System.out.println("-------------");
    }
}
