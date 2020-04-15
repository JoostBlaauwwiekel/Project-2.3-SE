package project.mvc.view.gameview;

import javafx.scene.image.Image;
import project.mvc.controller.ApplicationController;
import project.mvc.view.GameBoard;
import project.mvc.view.GameBoardView;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import project.mvc.view.gameboard.TicTacToeBoard;

import java.awt.*;

import static javafx.scene.paint.Color.BLACK;

public class TicTacToeView extends GameBoardView {

    private String mode = "";
    private GameBoard ticTacToeBoard;
    private Button restart;

    private VBox left;
    private VBox right;

    public TicTacToeView(Stage window, int boardWidth, ApplicationController controller) {
        super(window);
        int screenWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
        int sidePaneWidth = (screenWidth - boardWidth) / 2;
        Image leftImage = new Image(getClass().getResourceAsStream("../../web/TicTacToe/leftBackground.jpg"), sidePaneWidth, 880, false, false);
        Image centerImage = new Image(getClass().getResourceAsStream("../../web/TicTacToe/centerBackground.jpg"), boardWidth, 880, false, false);
        Image rightImage = new Image(getClass().getResourceAsStream("../../web/TicTacToe/rightBackground.jpg"), sidePaneWidth, 880, false, false);

        BackgroundImage leftBackgroundImage = new BackgroundImage(leftImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background leftBackground = new Background(leftBackgroundImage);
        BackgroundImage centerBackgroundImage = new BackgroundImage(centerImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background centerBackground = new Background(centerBackgroundImage);
        BackgroundImage rightBackgroundIMage = new BackgroundImage(rightImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background rightBackground = new Background(rightBackgroundIMage);;

        GridPane centerLayout = new GridPane();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setBackground(centerBackground);
        centerLayout.setMaxWidth(boardWidth);
        centerLayout.setMaxHeight(880);

        left = new VBox();
        left.setPrefSize(sidePaneWidth, 880);
        left.setBackground(leftBackground);

        right = new VBox();
        right.setPrefSize(sidePaneWidth, 500);
        right.setBackground(rightBackground);

        double tileSize = boardWidth/3;
        ticTacToeBoard = new TicTacToeBoard(window, tileSize, tileSize, centerLayout, super.getTopBar(), controller);

        Button[] gameBoardButtons = ticTacToeBoard.getTiles();
        for(int i = 0; i < ticTacToeBoard.getTiles().length; i++) {
            Image image = new Image(getClass().getResourceAsStream("../../web/TicTacToe/begin/ttt" + i + ".jpg"), tileSize, tileSize, false, false);
            Background background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
            //gameBoardButtons[i].setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000; -fx-border-width: 2px;");
            gameBoardButtons[i].setBackground(background);
            gameBoardButtons[i].setBorder(new Border(new BorderStroke(BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
        }

        restart = new Button("Restart Tic Tac Toe");
        Button exitGame = new Button("Exit Tic Tac Toe");

        exitGame.setStyle("-fx-background-color: #F14141;  -fx-font-size: 11px; -fx-background-radius: 10;");
        restart.setStyle("-fx-background-color: #808080;  -fx-font-size: 11px; -fx-background-radius: 10;");
        exitGame.setPrefSize(110, 70);
        restart.setPrefSize(110, 70);

        super.getGameButtons().put("Exit Tic-tac-toe", exitGame);
        super.getGameButtons().put("Restart Tic-tac-toe", restart);
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
        return ticTacToeBoard;
    }

    public void setLeftRightPane(boolean set){
        if(set){
            setLeft(left);
            setRight(right);
        }
        else{
            setLeft(null);
            setRight(null);
        }
    }

}
