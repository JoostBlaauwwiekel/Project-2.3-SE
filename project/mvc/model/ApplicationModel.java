package project.mvc.model;

import project.gamemodules.GameData;

public class ApplicationModel {

    private GameData gameData;

    public ApplicationModel(){
        gameData = new GameData();
    }

    public GameData getGameData(){
        return gameData;
    }
}
