package project.gameframework.tests;

import project.gameframework.*;
import project.gamemodules.GameCommunicationChannel;
import project.gamemodules.tictactoegame.*;


import java.io.IOException;
import java.util.*;

public class CommunicationTest {

    public static void main(String[] args) throws IOException {
        CommunicationChannel channel = new GameCommunicationChannel();
        channel.setUsername("computer");

        HashMap<String, String> map;
        Scanner scanner = new Scanner(System.in);

        // Don't forget to start the server before running this program! Thanks.
        channel.startServerAndPrepareLists();
        GameBoardLogic board = new TicTacToeBoardLogic();
        TicTacToeMiniMax ai = new TicTacToeMiniMax();

        channel.subscribe("Tic-tac-toe");
        //channel.challenge("computer", "Tic-tac-toe");

        while (true) {
            String message = channel.readFormattedLine();
            if (message.contains("YOUR TURN")) {
                int move = ai.getBestMove(board, 0);
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
