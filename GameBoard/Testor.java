package Project_SE_Periode3.GameBoard;

public class Testor {
    public static void main(String[] args) {
        GameBoard board = new Reversi();
        board.printBoard();
        board.makeMove( 'x', 0, 0);
        board.makeMove( 'x', 5, 0);
    }
}
