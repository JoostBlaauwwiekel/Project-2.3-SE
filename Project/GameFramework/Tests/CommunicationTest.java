package Project.GameFramework.Tests;

import Project.GameFramework.CommunicationChannel;
import Project.GameModules.GameCommunicationChannel;
import javafx.scene.chart.ScatterChart;

import Project.TicTacToe_Joost.*;

import java.io.IOException;
import java.util.*;

public class CommunicationTest {

    public static void main(String[] args) throws IOException {
        CommunicationChannel channel = new GameCommunicationChannel();
        channel.setUsername("2");
        HashMap<String, String> map;
        // Don't forget to start the server before running this program! Thanks.
        channel.startServerAndPrepareLists();
        Board board = new Board();
        MiniMax miniMax = new MiniMax();

        channel.subscribe("Tic-tac-toe");
        channel.challenge("joost", "Tic-tac-toe");

        while(true) {
            String s = channel.readLine();
            //System.out.println(s);
            if(s.contains("MOVE")) {
                map = getMap(s);
                if (map.get("MOVE") != null) {
                    System.out.println(map.get("MOVE"));
                    board.makeMove(1, Integer.parseInt(map.get("MOVE")));
                }
            }

            if(s.contains("YOURTURN")){
                int move = miniMax.getBestMove(board.getBoard());
                channel.move(move);
                board.makeMove(2, move);
            }

            board.printBoard();
        }
    }

    public static HashMap<String, String> getMap(String s) {
        HashMap<String, String> myMap = new HashMap<String, String>();

            s = s.replace("\"", "");
        try {
            s = s.substring(s.indexOf("{") + 1);
            s = s.substring(0, s.indexOf("}"));
        } catch (StringIndexOutOfBoundsException e) { }
        s = s.replaceAll("\\s+","");
        String[] pairs = s.split(",");

        for (int i=0;i<pairs.length - 1;i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split(":");
            myMap.put(keyValue[0], keyValue[1]);
        }
        return myMap;
    }
}
