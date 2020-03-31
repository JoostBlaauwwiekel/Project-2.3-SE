package Project.MVC.Model;

import Project.GameModules.GameData;

public class Model {

    private GameData gameData;

    public Model(){
        gameData = new GameData();
    }

    public GameData getGameData(){
        return gameData;
    }
}
