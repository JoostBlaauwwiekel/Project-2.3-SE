package Project.MVC.Model;

import Project.GameModules.GameData;

public class ApplicationModel {

    private GameData gameData;

    public ApplicationModel(){
        gameData = new GameData();
    }

    public GameData getGameData(){
        return gameData;
    }
}
