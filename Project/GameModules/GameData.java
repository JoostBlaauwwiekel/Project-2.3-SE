package Project.GameModules;

import Project.GameFramework.CommunicationChannel;
import Project.GameFramework.GameBoard;
import Project.GameFramework.GameLogic;
import Project.GameModules.ReversiGame.ReversiGameLogic;
import Project.GameModules.TicTacToeGame.TicTacToeGameLogic;

import java.util.HashMap;
import java.util.Map;

public class GameData {

    private static final String SERVER_IP_ADDRESS = "145.33.225.170";
    private Map<String, GameLogic> gameMap;
    private CommunicationChannel communicationChannel;

    public GameData(){
        initializeGameLogicList();
        communicationChannel = new GameCommunicationChannel();
    }

    private void initializeGameLogicList(){
        this.gameMap = new HashMap<>();
        this.gameMap.put("Tic-tac-toe", new TicTacToeGameLogic());
        this.gameMap.put("Reversi", new ReversiGameLogic());
    }

    public void registerToServer(String username, boolean playLocal){
        communicationChannel.setUsername(username);
        if(playLocal)
            communicationChannel.setIpAddress("localhost");
        else
            communicationChannel.setIpAddress(SERVER_IP_ADDRESS);
    }

    public GameLogic getGame(String gameName){
        return gameMap.get(gameName);
    }

    public void playGame(String gameName){
        GameLogic game = gameMap.get(gameName);
        GameBoard board = game.getBoard();

        //TODO implement the other features such that a game can be played
    }


}
