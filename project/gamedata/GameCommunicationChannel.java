package project.gamedata;

import project.gameframework.CommunicationChannel;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

public class GameCommunicationChannel implements CommunicationChannel {

    private static final int TIMEOUT = 100000;

    private int port = 7789;
    private String ipAddress = "localhost";

    // Declare the networking variables
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    // Declare the necessary lists
    private ArrayList<String> availableLists;
    private HashSet<String> gameSet;
    private HashSet<String> playerSet;

    private String username = "BITM";
    private boolean acquiringACertainSet = false;

    /**
     * The default constructor for the class GameCommunicationChannel.
     */
    public GameCommunicationChannel(){}

    /**
     * The acquireLists() method, will query the server for all possible lists that it contains. All possible lists
     * will be added to the availableLists lists. This way, when the user asks to acquire data from a specific list
     * we check whether the list is a valid list or not.
     *
     * @throws IOException if there is a connection problem with the server, this method will throw an IOException.
     */
    private void acquireLists() throws IOException{
        output.println("help get");
        skipLines(3);
        String line = readLine();
        skipLines(4);
        String[] lists = line.substring(line.indexOf("<") + 1, line.lastIndexOf(">")).split(Pattern.quote(" | "));
        Collections.addAll(availableLists, lists);
    }

    private void setRightUsername(String[] usernames){
        int i = 0;
        for(String player : usernames) {
            if (username.equals(player)) {
                usernames[i] += " (you)";
                break;
            }
            i++;
        }
    }

    /**
     * The acquireSet() method will try to fill a Set data structure with the necessary data. First the typeOfList String
     * parameter will be checked for its validity. That is, whether the availableLists list contains the list to be
     * queried or not. If not, a RunTimeError will be thrown. Else the data acquired from the server will be put in the
     * set.
     *
     * @param typeOfList a String param, which specifies the type of list to be received from the server.
     *                   Possible arguments include: 'gamelist' & 'playerlist'. If the argument is not part of the
     *                   availableLists list. Then a RunTimeError will be thrown.
     * @param set The set in which data will be stored.
     * @throws IOException if there is a connection problem with the serverF, this method will throw an IOException.
     */
    private void acquireSet(String typeOfList, HashSet<String> set) throws IOException {
        acquiringACertainSet = true;
        if(availableLists.contains(typeOfList)) {
            output.println("get " + typeOfList);
            readLine();
            String listElementString = readLine();
            acquiringACertainSet = false;
            if(!listElementString.equals("SVR PLAYERLIST []")) {
                listElementString = listElementString.substring(listElementString.indexOf("\"") + 1, listElementString.lastIndexOf("\""));
                String[] listElements = listElementString.split("\", \"");
                if (typeOfList.equals("playerlist"))
                    setRightUsername(listElements);
                Collections.addAll(set, listElements);
            }
        }
        else {
            acquiringACertainSet = false;
            throw new RuntimeException("Please choose a valid list you would like query!");
        }
    }

    /**
     * The formatServerMessage() method will format a server message into actual readable text.
     *
     * @param line the line to be formatted.
     * @return line, a String which has been formatted.
     */
    private String formatServerMessage(String line){
        String[] betweenQuotes;
        if(line.contains("GAME MOVE")){
            betweenQuotes = line.split("\"");
            if(betweenQuotes[1].equals(username))
                return "YOU: " + username + ", your previous move: " + betweenQuotes[3];
            else
                return "OPPONENT: " + betweenQuotes[1] + "'s previous move: " + betweenQuotes[3];
        }
        else if(line.contains("PLAYERTOMOVE")){
            betweenQuotes = line.split("\"");
            if(betweenQuotes[1].equals(username))
                return "PLAYER TO START: YOU! " + username + "! You're up against player: [" + betweenQuotes[5] + "]!";
            else
                return "PLAYER TO START: OPPONENT [" + betweenQuotes[1] + "] starts!";
        }
        else if(line.contains("CHALLENGE CANCELLED")){
            betweenQuotes = line.split("\"");
            return "CURRENT GAME CHALLENGE CANCELLED, CHALLENGE NUMBER: " + betweenQuotes[1];
        }
        else if(line.contains("GAME CHALLENGE")){
            betweenQuotes = line.split("\"");
            return "PLAYER {" + betweenQuotes[1] + "} challenged you to a game of [" + betweenQuotes[5] + "] challenge number: *" + betweenQuotes[3] + "*";
        }
        else if (line.contains("LOSS")) {
            String reason = "";
            if (!line.split("\"")[5].equals(""))
                reason = "" + line.split("\"")[5];
            return "YOU LOSE!" + reason + "!";
        }
        else if(line.contains("WIN"))
            return "YOU WIN! ";
        else if(line.contains("YOURTURN"))
            return "IT'S YOUR TURN " + username + "!";
        else
            return line;
    }

    /**
     * This method initializes the server configuration and initializes the necessary data structures. Furthermore,
     * this method also fills the initialized data structures with server acquired from the server.
     *
     * @return whether a connection has been established successfully.
     */
    public boolean startServerAndPrepareLists(){
        // Declare all the lists. Initialize the availableLists list.
        availableLists = new ArrayList<>();
        gameSet = new HashSet<>();
        playerSet = new HashSet<>();
        try {
            // Create an Internet Socket Address with address: ipAddress and with port: port, and the timeout TIMEOUT
            socket = new Socket(ipAddress, port);
//            socket = new Socket();
//            socket.connect(new InetSocketAddress(ipAddress, port));
            socket.setKeepAlive(true);

            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            skipLines(2);
            login(this.username);

            // Acquire a game list and a player list from the server. Add the acquired data to the class' lists.
            acquireLists();
            acquireSet("gamelist", gameSet);
            acquireSet("playerlist", playerSet);
        }
        catch(IOException e){
            return false;
        }
        return true;
    }

    public boolean getAcquiringACertainSet(){
        return acquiringACertainSet;
    }

    /**
     * The readLine() method, reads a line from the server and returns the read line in String format.
     *
     * @return input.readLine(), the line currently sent from the server.
     * @throws IOException if there is a connection problem with the server, this method will throw an IOException.
     */
    public String readLine() throws IOException{
        if(socket.isConnected()) {
            return input.readLine();
        }
        return "";
    }

    /**
     * The readFormattedLine() method, reads a line from the server formats the line using the formatServerMessage()
     * method and returns the read line in String format.
     *
     * @return the formatted line from the server.
     */
    public String readFormattedLine() {
        String line = "";
        try{
            line = formatServerMessage(readLine());
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        return line;
    }

    /**
     * The getGameSet() method, returns the gameSet Set.
     *
     * @return gameSet, the game set which contains all games which can be played.
     */
    public HashSet<String> getGameSet(){
        return gameSet;
    }

    /**
     * The getPlayerSet() method, returns the playerSet Set.
     *
     * @return playerSet, the player set which contains all players currently online. Each time this method is called
     * the list will be updated. In case some players logged off or new people logged in.
     * @throws IOException if there is a connection problem with the server, this method will throw an IOException.
     */
    public HashSet<String> getPlayerSet() {
        playerSet.clear();
        try {
            acquireSet("playerlist", playerSet);
        }
        catch(IOException ioe){
        }
        return playerSet;
    }

    /**
     * This method lets you change the IP address of the gameServer.
     *
     * @param ipAddress the corresponding IP address.
     */
    public void setIpAddress(String ipAddress){
        this.ipAddress = ipAddress;
    }

    /**
     * This method lets you change the username that will be used on the server.
     * @param username the username which will be used on the server.
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * This method lets you change the port that will be used to connect to the server.
     * @param port to connect to.
     */
    public void setPort(int port){
        this.port = port;
    }

    /**
     * The skipLines() method, skips a finite number of lines, indicated by the user.
     *
     * @param times the number of lines to be skipped.
     * @throws IOException if there is a connection problem with the server, this method will throw an IOException.
     */
    public void skipLines(int times) throws IOException{
        for(int i = 0; i < times; i++){
            readLine();
        }
    }

    public boolean getInputReady(){
        boolean inputReady = false;
        try {
            inputReady = input.ready();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        return inputReady;
    }

    /**
     * The readLines() method, reads all lines being sent from the server. It returns the last line that was received.
     *
     * @return the last line received from the server.
     * @throws IOException if there is a connection problem with the server, this method will throw an IOException.
     */
    public String readLines() throws IOException{
        String lastLine = "";
        while(input.ready()){
            lastLine = readLine();
            System.out.println(" S: " + formatServerMessage(lastLine));
        }
        return lastLine;
    }

    /**
     *  The login() method, logs in a user.
     *
     * @param username the username which will be visible on the server. Used for tournaments and matches against
     *                 other players.
     * @throws IOException if there is a connection problem with the server, this method will throw an IOException.
     */
    public void login(String username) throws IOException {
        output.println("login " + username);
        input.readLine();
    }

    /**
     *  The logout() method, logs you off the server.
     */
    public void logout(){
        output.println("logout");
    }

    /**
     *  The subscribe() method, subscribes the player currently logged in to the corresponding game.
     *
     * @param game the game which will be played.
     */
    public void subscribe(String game){
        output.println("subscribe " + game);
    }

    /**
     *  The challenge() method, allows the user currently logged in, to challenge another user. The user who
     *  challenges another user can choose which game will be played.
     *
     * @param player the player to be challenged.
     * @param game the game to be played.
     */
    public void challenge(String player, String game){
        output.println("challenge " + "\"" + player + "\" \"" + game + "\"");
    }

    /**
     * The challengeAccept() method, will accept a challenge with challenge number: number.
     *
     * @param number the number of the challenge to be accepted.
     */
    public void challengeAccept(int number){
        output.println("challenge accept " + number);
    }

    /**
     *  The move() method, will tell the server which move the algorithm has decided to make.
     *
     * @param position the position where a piece is to be placed.
     */
    public void move(int position){
        output.println("move " + position);
    }
}