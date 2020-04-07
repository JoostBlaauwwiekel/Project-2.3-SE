package project.mvc.model;

import project.gameframework.GameLogic;
import project.gamemodules.GameData;
import project.gamemodules.tictactoegame.TicTacToeGameLogic;

public class ApplicationModel {

    private GameData gameData;

    public ApplicationModel(){
        gameData = new GameData();
    }

    public GameData getGameData(){
        return gameData;
    }
}
