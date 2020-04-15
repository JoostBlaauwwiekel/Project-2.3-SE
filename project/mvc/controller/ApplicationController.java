package project.mvc.controller;

import project.gameframework.GameAI;
import project.mvc.model.ApplicationModel;
import project.mvc.view.ApplicationView;
import java.util.ArrayList;
import java.util.HashSet;

public class ApplicationController {

    private ApplicationModel applicationModel;
    private ApplicationView applicationView;

    /**
     * This method creates an ApplicationController
     * @param applicationModel the application model.
     */
    public ApplicationController(ApplicationModel applicationModel){
        this.applicationModel = applicationModel;
        applicationView = new ApplicationView(this, applicationModel);
        applicationModel.registerObserver(applicationView);
    }

    /**
     * This method returns the applicationview.
     * @return
     */
    public ApplicationView getApplicationView(){
        return applicationView;
    }

    /**
     * This method initializes a gameName with the given pararms.
     * @param gameName the name of the game.
     */
    public void initializeGame(String gameName){
        applicationModel.getGameData().initializeGame(gameName);
    }

    /**
     * This method returns the player their turn.
     * @param move the move of the player.
     * @return
     */
    public boolean playerHisTurn(int move){
        return applicationModel.getGameData().playOfflineGame(move, 1);
    }

    /**
     * This method returns whether or not the game is over.
     * @return
     */
    public int getGameOver(){
        return applicationModel.getGameData().getGameLogic().gameOver();
    }

    /**
     * This method returns whether or not the game is offline.
     * @return
     */
    public boolean getOffline(){
        return applicationModel.getGameData().getOffline();
    }

    /**
     * This method resets the board.
     */
    public void resetGame(){ applicationModel.getGameData().getGameLogic().getBoard().resetBoard(); }

    /**
     * This method returns the board.
     * @return
     */
    public int[] getBoard(){ return applicationModel.getGameData().getGameLogic().getBoard().getBoard(); }

    /**
     * This method returns the moves of a player.
     * @param player a player of the board.
     * @return
     */
    public ArrayList<Integer> getMoves(int player){ return applicationModel.getGameData().getGameLogic().getMoves(player); }

    /**
     * This method returns whether or not applicationModel is registered to server.
     * @return
     */
    public boolean connectToServer(){
        return applicationModel.getGameData().registerToServer();
    }

    /**
     * This method logs the client out from the server.
     */
    public void disconnectFromServer(){
        applicationModel.getGameData().logoutFromServer();
    }

    /**
     * This method sets the settings to play on a server with the given params.
     * @param username the username on the server.
     * @param ipAdddress the ipadress of the server.
     * @param portnumber the portnumber of the server.
     * @param timeOut the given time before a timeout is given.
     */
    public void setSettings(String username, String ipAdddress, int portnumber, float timeOut){
        applicationModel.getGameData().setUsernameIpAddressAndPort(username, ipAdddress, portnumber);
        applicationModel.getGameData().setTimeOut(timeOut);
    }

    /**
     * This method fills the player sets.
     */
    public void fillPlayerSet(){
        clearActionLabel();
        applicationView.getServerOptionsView().getListViews().get("PlayerList").getItems().clear();
        applicationView.getServerOptionsView().getListViews().get("PlayerList").getItems().addAll(applicationModel.getGameData().getPlayerSet());
    }

    /**
     * This method clears the event of a label.
     */
    public void clearActionLabel(){
        applicationView.getServerOptionsView().getEventLabel().setText("Idle...");
    }

    /**
     * This method is used to start an online game.
     */
    public void startOnlineGame(){
        applicationModel.getGameData().startOnlineGame();
    }

    /**
     * This method is used to challenge a player
     * @param player the challenged player.
     */
    public void challengePlayer(String player){
        String currentPlayer = applicationModel.getGameData().getUsername() + " (you)";
        if(player == null){
            applicationView.getServerOptionsView().getEventLabel().setText("You did not choose a player to challenge!\nSelect a player and try again!");
        }
        else if(!currentPlayer.equals(player)) {
            applicationView.getServerOptionsView().getEventLabel().setText("You challenged: [" + player + "] at " + applicationModel.getCurrentGame() + "!\nWaiting for " + player + "'s response....");
            applicationModel.getGameData().challengePlayer(player);
            applicationModel.getGameData().setGameMode("challenged a player");
        }
        else
            applicationView.getServerOptionsView().getEventLabel().setText("You cannot challenge yourself!\nWhomst'd've thought that?!");
    }

    /**
     * This method is used accept a challenge.
     * @param player the players name who's challenge you accept.
     * @param challenge the challenge you accept.
     */
    public void acceptChallenge(String player, int challenge){
        applicationView.getServerOptionsView().getEventLabel().setText("You accepted: " + player + "'s challenge at " + applicationModel.getCurrentGame() + "!");
        applicationModel.getGameData().setCurrentChallengeNr(challenge);
        applicationModel.getGameData().setGameMode("got challenged");
    }

    /**
     * This method subscribes you to the server.
     */
    public void subscribeToGame(){
        applicationView.getServerOptionsView().getEventLabel().setText("You are currently subscribed to: " + applicationModel.getCurrentGame() + "!");
        applicationModel.getGameData().setGameMode("subscribed");
        applicationModel.getGameData().subscribeToGame();
    }

    /**
     * This method sets if game is offline.
     * @param offline
     */
    public void setOffline(boolean offline){
        applicationModel.getGameData().setOffline(offline);
    }

    /**
     * This method sets the ingame variable in the gamedata.
     * @param inGame state of the game.
     */
    public void setInGame(boolean inGame){
        applicationModel.getGameData().setInGame(inGame);
    }

    /**
     * This method sets the lobby variable in the gamedata.
     * @param inLobby state of the lobby.
     */
    public void setInLobby(boolean inLobby){
        applicationModel.getGameData().setInLobby(inLobby);
    }

    /**
     * This method sets the tournament variable in the gamedata.
     * @param inTournament state of the tournament.
     */
    public void setInTournament(boolean inTournament){
        applicationModel.getGameData().setInTournament(inTournament);
    }

    /**
     * This method joins the serverlobby in gamedata.
     */
    public void joinLobby(){
        applicationModel.getGameData().sitInServerLobby();
    }

    /**
     * This method initializes the board in gamedata.
     * @param initialized the state of the board.
     */
    public void setBoardInitialized(boolean initialized){
        applicationModel.getGameData().setBoardInitialized(initialized);
    }

    /**
     * This method initializes the AI difficulty in gamedata.
     * @param aiDifficulty the ai difficulty.
     */
    public void setAIDifficulty(int aiDifficulty){
        applicationModel.getGameData().setAiDifficulty(aiDifficulty);
    }

    /**
     * This method returns the player sets from gamedata.
     * @return
     */
    public HashSet<String> getPlayerSet(){
        return applicationModel.getGameData().getPlayerSet();
    }
}
