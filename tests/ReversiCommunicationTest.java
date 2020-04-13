package project.gameframework.tests;

import project.gameframework.CommunicationChannel;
import project.gameframework.GameBoardLogic;
import project.gamedata.GameCommunicationChannel;
import project.gamemodules.reversigame.ReversiBoardLogic;
import project.gamemodules.reversigame.ReversiGameLogic;
import project.gamemodules.reversigame.ReversiMinimaxStrategy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class ReversiCommunicationTest {

    public static void main(String[] args) throws IOException {
        CommunicationChannel channel = new GameCommunicationChannel();
        channel.setUsername("bitm2");

        HashMap<String, String> map;
        Scanner scanner = new Scanner(System.in);

        // Don't forget to start the server before running this program! Thanks.
        channel.startServerAndPrepareLists();
        GameBoardLogic board = new ReversiBoardLogic();
        ReversiMinimaxStrategy ai = new ReversiMinimaxStrategy();
        ReversiGameLogic logic = new ReversiGameLogic();
        logic.setBoard(board);

//        channel.subscribe("Reversi");
//        channel.challenge("computer", "Reversi");

        int player = 0;
        while (true) {
            String message = channel.readFormattedLine();
//            System.out.println(message);
            if (message.contains("PLAYER TO START")){
                if(message.contains("OPPONENT")){
                    player = 1;
                } else {
                    player = 2;
                }
                System.out.println(message);
            } else if (message.contains("YOUR TURN")) {
                int move = ai.getBestMove(board, player);
                channel.move(move);
                logic.doMove(move, player);
                System.out.println("Our move " + move);
                board.printBoard();
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

                System.out.println("Opponent's move: " + opponentHisMove);
                logic.doMove(opponentHisMove, 3 - player);
                board.printBoard();
            } else if (message.contains("LOSE") || message.contains("WIN") || message.contains("DRAW")) {
                System.out.println(message);
                board.resetBoard();
            }
            System.out.println();
        }
    }
}
