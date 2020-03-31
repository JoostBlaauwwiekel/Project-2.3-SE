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
package TicTacToe_Joost;

import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);
        MiniMax minmax = new MiniMax();

        int counter = 0;
        while(!board.isFinished()) {
            if(counter % 2 == 0) {

                int pos;
                System.out.println("Enter position:");
                pos = scanner.nextInt();
                board.makeMove(1, pos);

            } else {
                System.out.println("Computer's move:");
                Move bestMove = minmax.getBestMove(board.getBoard());
                board.makeMove(2, bestMove.pos);

            }
            board.printBoard();
            counter++;
        }
    }
}
