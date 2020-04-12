package project.mvc.view.gameboard;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import project.gamemodules.GameData;
import project.gamemodules.reversigame.ReversiBoardLogic;
import project.mvc.model.ApplicationModel;
import project.mvc.view.GameBoard;

public class ReversiBoard extends GameBoard {

    public ReversiBoard(double buttonHeight, double buttonWidth, ApplicationModel model, GridPane layout, HBox topbar){
        super(8,8, buttonHeight,buttonWidth, new ReversiBoardLogic(), model, "Reversi", layout, topbar);
    }
}