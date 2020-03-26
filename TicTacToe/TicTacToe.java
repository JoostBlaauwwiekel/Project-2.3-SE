/**
 * Class containing the main method.
 *
 * The main method creates the necessary objects
 * to play tic tac toe.
 *
 * The logic in the main method is not (yet) save for user input.
 *
 * @author      Joost Blaauwwiekel
 * @version     1.0
 */
package TicTacToe;

import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);
        MiniMax minmax = new MiniMax();

        int counter = 0;
        while(!board.isFinished()) {
            if(counter % 2 == 0) {

                int row, col;
                System.out.println("Enter row:");
                row = scanner.nextInt();
                System.out.println("Enter column:");
                col = scanner.nextInt();
                board.makeMove('o', row, col);

            } else {
                System.out.println("Computer's move:");
                Move bestMove = minmax.getBestMove(board.getBoard());
                board.makeMove('x', bestMove.row, bestMove.col);

            }
            board.printBoard();
            counter++;
        }
    }
}
