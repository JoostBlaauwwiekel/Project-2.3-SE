package Project_SE_Periode3.Project.MVC.Model;

import Project_SE_Periode3.Project.GameModules.GameData;

public class Model {

    private GameData gameData;

    public Model(){
        gameData = new GameData();
    }

    public GameData getGameData(){
        return gameData;
    }
}
