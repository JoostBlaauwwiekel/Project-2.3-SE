package GameBoard;

public class TicTacToeBoard extends GameBoard {
    int counter = 0;

    public TicTacToeBoard() {
        int size = 3;
        setBoard(size);
    }
}
