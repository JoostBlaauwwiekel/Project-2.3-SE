package GameBoard;

public abstract class GameBoard{
    public char[][] board;

    public GameBoard() { }

    public  void setBoard(int size){
        board = new char[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                board[row][column] = '-';
            }
        }
    }

    public void printBoard() {
        System.out.println("---------------------------------------");
        for (int row = 0; row < board.length; row++){
            String output = "| ";
            for(int column = 0; column < board.length; column++){
                output += String.valueOf(board[row][column]) + "| ";
            }
            System.out.println(output);
        }
        System.out.println("---------------------------------------");
    }

    public void makeMove(char player, int row, int col) {
        if(isValid(row, col)) {
            board[row][col] = player;
            printBoard();
        } else {
            System.out.println("Invalid move");
        }
    }
    public boolean isValid(int row, int col) {
        if(board.length > row && board.length > col){
            if(board[row][col] == '-'){
            return true;
            }
        }
        return false;
    }


}
