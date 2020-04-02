package project.mvc.view.gameboard;

import project.gamemodules.reversigame.ReversiBoardLogic;
import project.mvc.view.GameBoard;

public class ReversiBoard extends GameBoard {

    public ReversiBoard(){
        super(8,8, new ReversiBoardLogic());
    }
}
