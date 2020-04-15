package project.mvc.view.gameview;

import javafx.geometry.Insets;
import javafx.stage.StageStyle;
import project.mvc.controller.ApplicationController;
import project.mvc.view.GameBoard;
import project.mvc.view.GameBoardView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import project.mvc.view.gameboard.ReversiBoard;

public class ReversiView extends GameBoardView {

    private String mode = "";
    private GameBoard reversiGameBoard;
    private Button restart;

    public ReversiView(Stage window, int boardWidth, ApplicationController controller) {
        super(window);

        GridPane centerLayout = new GridPane();
        centerLayout.setAlignment(Pos.CENTER);
        //centerLayout.setStyle("-fx-background-color: #E5E2E2;");
        centerLayout.setStyle("-fx-background-color: #000000;");
        centerLayout.setMaxWidth(boardWidth);

        double tileSize = boardWidth/8;
        reversiGameBoard = new ReversiBoard(window, tileSize - 1, tileSize - 1, centerLayout, super.getTopBar(), controller);

        Button[] gameBoardButtons = reversiGameBoard.getTiles();
        for(int i = 0; i < reversiGameBoard.getTiles().length; i++) {
            gameBoardButtons[i].setStyle("-fx-background-color: #1B5B1C; -fx-border-color: #000000; -fx-border-width: 1px;");
            gameBoardButtons[i].setPadding(new Insets(1,1,1,1));
        }

        restart = new Button("Restart Reversi");
        Button exitGame = new Button("Exit Reversi");

        exitGame.setStyle("-fx-background-color: #F14141;  -fx-font-size: 13px; -fx-background-radius: 10;");
        restart.setStyle("-fx-background-color: #808080;  -fx-font-size: 13px; -fx-background-radius: 10;");
        exitGame.setPrefSize(110, 70);
        restart.setPrefSize(110, 70);

        super.getGameButtons().put(exitGame.getText(), exitGame);
        super.getGameButtons().put(restart.getText(), restart);
        super.getButtons().getChildren().addAll(restart, exitGame);

        setCenter(centerLayout);
        super.getTopBar().setAlignment(Pos.CENTER);
        setTop(super.getTopBar());
    }

    public void setRestartButton(boolean bool){
        restart.setDisable(bool);
    }

    public void setMode(String mode){
        this.mode = mode;
    }

    public GameBoard getGameBoard(){
        return reversiGameBoard;
    }

    public void setLeftRightPane(boolean set){

    }
}
