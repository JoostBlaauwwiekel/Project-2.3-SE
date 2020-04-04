package project.mvc.view.gameboard;

import project.gamemodules.reversigame.ReversiBoardLogic;
import project.mvc.view.GameBoard;

public class ReversiBoard extends GameBoard {

    public ReversiBoard(double buttonHeight, double buttonWidth){
        super(8,8, buttonHeight,buttonWidth, new ReversiBoardLogic());
    }
}
