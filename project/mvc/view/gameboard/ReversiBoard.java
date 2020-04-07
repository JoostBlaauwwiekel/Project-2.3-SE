package project.mvc.view.gameboard;

import project.gamemodules.GameData;
import project.gamemodules.reversigame.ReversiBoardLogic;
import project.mvc.view.GameBoard;

public class ReversiBoard extends GameBoard {

    public ReversiBoard(double buttonHeight, double buttonWidth, GameData gameData){
        super(8,8, buttonHeight,buttonWidth, new ReversiBoardLogic(), gameData, "Reversi");
    }
}
