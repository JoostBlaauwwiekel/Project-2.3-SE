package project.gameframework.tests;

import project.gameframework.CommunicationChannel;
import project.gameframework.GameBoardLogic;
import project.gamemodules.GameCommunicationChannel;
import project.gamemodules.reversigame.ReversiBoardLogic;
import project.gamemodules.reversigame.ReversiGameLogic;
import project.gamemodules.reversigame.ReversiMinimaxStrategy;
import project.gamemodules.tictactoegame.TicTacToeBoardLogic;
import project.gamemodules.tictactoegame.TicTacToeMinimaxStrategy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class ReversiCommunicationTest {

    public static void main(String[] args) throws IOException {
        CommunicationChannel channel = new GameCommunicationChannel();
        channel.setUsername("computer");

        HashMap<String, String> map;
        Scanner scanner = new Scanner(System.in);

        // Don't forget to start the server before running this program! Thanks.
        channel.startServerAndPrepareLists();
        GameBoardLogic board = new ReversiBoardLogic();
        ReversiMinimaxStrategy ai = new ReversiMinimaxStrategy();
        ReversiGameLogic logic = new ReversiGameLogic();
        logic.setBoard(board);

        channel.subscribe("Reversi");
//        channel.challenge("computer", "Reversi");

        while (true) {
            String message = channel.readFormattedLine();
            if (message.contains("YOUR TURN")) {
                int move = ai.getBestMove(board, 2);
                channel.move(move);
                logic.doMove(move, 2);
                System.out.println(move);
            } else if (message.contains("previous move") && !message.contains("YOU")) {
                int opponentHisMove;
                String s;

                // First try last two chars of the string
                try {
                    s = message.substring(message.length() - 2);
                    opponentHisMove = Integer.parseInt(s);
                } catch (java.lang.NumberFormatException e) {
                    // Else get the last char of the string
                    s = message.substring(message.length() - 1);
                    opponentHisMove = Integer.parseInt(s);
                }

                System.out.println("opponent's move: " + opponentHisMove);
                logic.doMove(opponentHisMove, 1);
            } else if (message.contains("LOSE") || message.contains("WIN") || message.contains("DRAW")) {
                System.out.println(message);
                board.resetBoard();
            }

            board.printBoard();
            System.out.println();
        }
    }
}
