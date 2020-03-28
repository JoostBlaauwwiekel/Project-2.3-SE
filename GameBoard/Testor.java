package GameBoard;

public class Testor {
    public static void main(String[] args) {
        TicTacToeBoard board = new TicTacToeBoard();
        board.makeMove('x', 0, 0);
        board.printBoard();
    }
}
