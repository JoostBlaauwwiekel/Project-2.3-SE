package Server_API;

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class API_TESTOR {

    public static void main(String[] args){
        CommunicationChannel channel = new GameCommunicationChannel("BobBobson87");
        // Don't forget to start the server before running this program! Thanks.
        try {
            while(true) {
                Thread.sleep(3000);
                HashSet<String> playerList = channel.getPlayerSet();
                System.out.println("----- Testing Information -----");
                int i = 1;
                for (String player : playerList) {
                    System.out.println("Player #" + i);
                    System.out.println(player);
                    System.out.println();
                    i++;
                }

                HashSet<String> gameList = channel.getGameSet();
                int j = 1;
                for (String game : gameList) {
                    System.out.println("Game #" + j);
                    System.out.println(game);
                    System.out.println();
                    j++;
                }
                break;
            }

            // Here is where the magic happens
            channel.subscribe("Tic-tac-toe");
            Scanner scanner = new Scanner(System.in);
            String line;
            System.out.println("Wait for the server to respond.... (don't forgot to subscribe with a puTTy client!)");
            System.out.println("Wait until 'move' shows up.");
            while(true){
                if(channel.readLines().contains("YOURTURN")){
                    System.out.println("S: move: ");
                    line = scanner.nextLine();
                    channel.move(Integer.parseInt(line));
                }
            }
        }
        catch(IOException ie){ }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
