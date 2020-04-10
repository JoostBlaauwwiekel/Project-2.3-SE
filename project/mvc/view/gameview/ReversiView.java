package project.mvc.view.gameview;

import project.mvc.model.ApplicationModel;
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

    public ReversiView(Stage window, int boardWidth, ApplicationModel model) {
        super(window);

        GridPane centerLayout = new GridPane();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setStyle("-fx-background-color: #524D4E;");
        centerLayout.setMaxWidth(boardWidth);

        double tileSize = boardWidth/8;
        reversiGameBoard = new ReversiBoard(tileSize, tileSize, model, centerLayout);
        Button[] gameBoardButtons = reversiGameBoard.getTiles();
        for(int i = 0; i < reversiGameBoard.getTiles().length; i++) {
            gameBoardButtons[i].setStyle("-fx-background-color: #1B5B1C; -fx-border-color: #000000; -fx-border-width: 1px;");
        }

        Button restart = new Button("Restart Reversi");
        Button exitGame = new Button("Exit Reversi");

        exitGame.setStyle("-fx-background-color: #FF0000; -fx-border-color: #000000;");
        restart.setStyle("-fx-background-color: #808080; -fx-border-color: #000000;");
        exitGame.setPrefSize(150, 100);
        restart.setPrefSize(150, 100);

        super.getGameButtons().put(exitGame.getText(), exitGame);
        super.getGameButtons().put(restart.getText(), restart);
        super.getButtons().getChildren().addAll(restart, exitGame);

        setCenter(centerLayout);
        setTop(super.getTopBar());
    }

    public void setMode(String mode){
        this.mode = mode;
    }

    public GameBoard getGameBoard(){
        return reversiGameBoard;
    }
}
