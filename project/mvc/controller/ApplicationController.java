package project.mvc.controller;

import project.mvc.model.ApplicationModel;
import project.mvc.view.ApplicationView;
import java.util.ArrayList;

public class ApplicationController {

    private ApplicationModel applicationModel;
    private ApplicationView applicationView;

    public ApplicationController(ApplicationModel applicationModel){
        this.applicationModel = applicationModel;
        applicationView = new ApplicationView(this, applicationModel);
        applicationModel.registerObserver(applicationView);
    }

    public ApplicationView getApplicationView(){
        return applicationView;
    }

    public void initializeGame(String gameName){
        applicationModel.getGameData().initializeGame(gameName);
    }

    public boolean playerHisTurn(int move){
        return applicationModel.getGameData().playOfflineGame(move, 1);
    }

    public int getGameOver(){
        return applicationModel.getGameData().getGameLogic().gameOver();
    }

    public void resetGame(){ applicationModel.getGameData().getGameLogic().getBoard().resetBoard(); }

    public int[] getBoard(){ return applicationModel.getGameData().getGameLogic().getBoard().getBoard(); }

    public ArrayList<Integer> getMoves(int player){ return applicationModel.getGameData().getGameLogic().getMoves(player); }

    public boolean connectToServer(){
        return applicationModel.getGameData().registerToServer();
    }

    public void disconnectFromServer(){
        applicationModel.getGameData().logoutFromServer();
    }

    public void setSettings(String username, String ipAdddress, int portnumber){
        applicationModel.getGameData().setUsernameIpAddressAndPort(username, ipAdddress, portnumber);
    }

    public void fillPlayerSet(){
        clearActionLabel();
        applicationView.getServerOptionsView().getListViews().get("PlayerList").getItems().clear();
        applicationView.getServerOptionsView().getListViews().get("PlayerList").getItems().addAll(applicationModel.getGameData().getPlayerSet());
    }

    public void fillChallengeSet(){
        clearActionLabel();
        applicationView.getServerOptionsView().getListViews().get("ChallengeList").getItems().clear();
        applicationView.getServerOptionsView().getListViews().get("ChallengeList").getItems().addAll(applicationModel.getGameData().getChallenges().values());
    }

    public void clearActionLabel(){
        applicationView.getServerOptionsView().getEventLabel().setText("Idle...");
    }

    public void startOnlineGame(){
        applicationModel.getGameData().startOnlineGame();
    }

    public void challengePlayer(String player){
        String currentPlayer = applicationModel.getGameData().getCurrentPlayer() + " (you)";
        if(player == null){
            applicationView.getServerOptionsView().getEventLabel().setText("You did not choose a player to challenge!\nSelect a player and try again!");
        }
        else if(!currentPlayer.equals(player)) {
            applicationView.getServerOptionsView().getEventLabel().setText("You challenged: [" + player + "] at " + applicationModel.getCurrentGame() + "!\nWaiting for " + player + "'s response....");
            applicationModel.getGameData().challengePlayer(player);
            applicationModel.getGameData().setChallengedPlayer(true);
            applicationModel.getGameData().setChallengeAccepted(false);
            applicationModel.getGameData().setSubscribed(false);
        }
        else
            applicationView.getServerOptionsView().getEventLabel().setText("You cannot challenge yourself!\nWhomst'd've thought that?!");
    }

    public void acceptChallenge(String player, int challenge){
        applicationView.getServerOptionsView().getEventLabel().setText("You accepted: " + player + "'s challenge at " + applicationModel.getCurrentGame() + "!");
        applicationModel.getGameData().setCurrentChallengeNr(challenge);
        applicationModel.getGameData().setChallengeAccepted(true);
        applicationModel.getGameData().setSubscribed(false);
    }

    public void subscribeToGame(){
        applicationView.getServerOptionsView().getEventLabel().setText("You are currently subscribed to: " + applicationModel.getCurrentGame() + "!");
        applicationModel.getGameData().setChallengedPlayer(false);
        applicationModel.getGameData().setSubscribed(true);
        applicationModel.getGameData().subscribeToGame();
    }

    public void setOffline(boolean offline){
        applicationModel.getGameData().setOffline(offline);
    }

    public void setInGame(boolean inGame){
        applicationModel.getGameData().setInGame(inGame);
    }

    public void setInLobby(boolean inLobby){
        applicationModel.getGameData().setInLobby(inLobby);
    }

    public void joinLobby(){
        applicationModel.getGameData().sitInServerLobby();
    }
}
