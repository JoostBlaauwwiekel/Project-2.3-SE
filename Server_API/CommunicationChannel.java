package Server_API;

import java.io.IOException;
import java.util.HashSet;

public interface CommunicationChannel {

    /**
     * This method should read a line from the server and returns it in a String format.
     *
     * @return the line read from the server.
     * @throws IOException if there is a connection problem with the server, this method will throw an IOException.
     */
    public String readLine() throws IOException;

    /**
     * This method should return the game set, containing all games that can be played on the server.
     *
     * @return a HashSet which contains Strings, each item represents a game that can be played.
     */
    public HashSet<String> getGameSet();

    /**
     * This method should return the player set, containing all players currently online.
     *
     * @return a HashSet which contains Strings, each item represents currently logged onto the server.
     * @throws IOException if there is a connection problem with the server, this method will throw an IOException.
     */
    public HashSet<String> getPlayerSet() throws IOException;

    /**
     * This method should be used to skip the readLine() method, x times. As indicated by the times parameter.
     *
     * @param times the number of times, the readLine() method should be skipped.
     * @throws IOException if there is a connection problem with the server, this method will throw an IOException.
     */
    public void skipLines(int times) throws IOException;

    /**
     * This method should read all the lines received from the server. Furthermore, it should return the last line
     * received from the server.
     *
     * @return the last line received from the server.
     * @throws IOException if there is a connection problem with the server, this method will throw an IOException.
     */
    public String readLines() throws IOException;

    /**
     * This method should be used to log the user onto the server.
     *
     * @param username the username to be logged in.
     * @throws IOException if there is a connection problem with the server, this method will throw an IOException.
     */
    public void login(String username) throws IOException;

    /**
     * This method should be used to log off the user from the server.
     */
    public void logout();

    /**
     * This method should subscribe the logged on player to a specified game.
     *
     * @param game the game which the user will subscribe to.
     */
    public void subscribe(String game);

    /**
     * This method should ensure that the user is able to challenge another player to a specified game.
     *
     * @param player the player who the logged on player wants to challenge.
     * @param game the game the logged on player wants to challenge the specified player in.
     */
    public void challenge(String player, String game);

    /**
     * This method should ensure that the user can accept an incoming challenge, with challenge number: number.
     *
     * @param number the number of the challenge to be accepted.
     */
    public void challengeAccept(int number);

    /**
     * This method should ensure that the user can put in an integer and that the integer will be used to make
     * a move using the appropriate server call.
     *
     * @param position the position (move) to be passed to the server.
     */
    public void move(int position);
}
