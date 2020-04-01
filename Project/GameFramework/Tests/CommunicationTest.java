package Project.GameFramework.Tests;

import Project.GameFramework.CommunicationChannel;
import Project.GameModules.GameCommunicationChannel;
import Project.GameModules.TicTacToeGame.TicTacToeBoard;
import Project.GameModules.TicTacToeGame.TicTacToeAI;
import javafx.scene.chart.ScatterChart;
import Project.GameFramework.GameBoard;

import TicTacToe_Joost.*;

import java.io.IOException;
import java.util.*;

public class CommunicationTest {

    public static void main(String[] args) throws IOException {
        CommunicationChannel channel = new GameCommunicationChannel();
        channel.setUsername("2");

        HashMap<String, String> map;
        Scanner scanner = new Scanner(System.in);

        // Don't forget to start the server before running this program! Thanks.
        channel.startServerAndPrepareLists();
        TicTacToeBoard board = new TicTacToeBoard();
        TicTacToeAI ai = new TicTacToeAI();

        channel.subscribe("Tic-tac-toe");
        channel.challenge("joost", "Tic-tac-toe");

        while (true) {
            String message = channel.readFormattedLine();
            if (message != "") {
                if (message.contains("YOUR TURN")) {
                    int move = ai.getBestMove(board.getBoard());
                    channel.move(move);
                    board.setBoardPos(move, 2);
                    System.out.println(move);
                } else if (message.contains("previous move") && !message.contains("YOU")) {
                    int opponentHisMove = Integer.parseInt(message.charAt(message.length() - 1) + "");
                    System.out.println("opponent's move: " + opponentHisMove);
                    board.setBoardPos(opponentHisMove, 1);
                } else if (message.contains("LOSE") || message.contains("WIN") || message.contains("DRAW")) {
                    System.out.println(message);
                    board.resetBoard();
                }

                board.printBoard();
                System.out.println();
            }
        }
    }
}
