package project.mvc.view.gameboard;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import project.gamemodules.GameData;
import project.gamemodules.tictactoegame.TicTacToeBoardLogic;
import project.mvc.model.ApplicationModel;
import project.mvc.view.GameBoard;

public class TicTacToeBoard extends GameBoard {

    public TicTacToeBoard(double buttonHeight, double buttonWidth, ApplicationModel model, GridPane layout, HBox topbar) {
        super(3, 3,buttonHeight,buttonWidth,new TicTacToeBoardLogic(), model, "Tic-tac-toe", layout, topbar);
    }

}