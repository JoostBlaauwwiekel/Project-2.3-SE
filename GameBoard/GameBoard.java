package GameBoard;

public abstract class GameBoard implements WinBehaviour{
    WinBehaviour ticTacToeFinished;
    public char[][] board;

    public GameBoard() { }

    public void makeMove(char player, int row, int col) {
        if(isValid(row, col)) {
            board[row][col] = player;
        } else {
            System.out.println("Invalid move");
        }
    }

    public boolean isValid(int row, int col) {
        return board[row][col] == '-';
    }

    public boolean isFinished(char[][] board) {
        return WinBehaviour.isFinished(board);
    }

}
