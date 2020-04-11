package project.mvc.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.gameframework.GameBoardLogic;
import project.gameframework.GameLogic;
import project.gamemodules.GameData;

import java.util.ArrayList;

public class BoardLogic {

    private GameData gameData;
    private String gameName;
    private GameBoard board;

    private int gameBoardWidth;
    private int gameBoardHeight;
    private int gameBoardDimension;

    private double gameButtonWidth;
    private double gameButtonHeight;

    private Button[] tiles;

    public BoardLogic(GameData gameData, String gameName, int gameBoardWidth, int gameBoardHeight, int gameBoardDimension, double gameButtonHeight, double gameButtonWidth, GameBoard board) {
        this.gameData = gameData;
        this.gameName = gameName;
        this.gameBoardWidth = gameBoardWidth;
        this.gameBoardHeight = gameBoardHeight;
        this.gameBoardDimension = gameBoardDimension;
        this.gameButtonWidth = gameButtonWidth;
        this.gameButtonHeight = gameButtonHeight;
        this.board = board;
    }

    public boolean gameOver() {
        tiles = board.getTiles();

        if(gameData.getGame(gameName).gameOver() == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("End of round");
            alert.setHeaderText(null);
            alert.setContentText("player 1 Won!");

            alert.showAndWait();
            System.out.println("Player 1 won!");
            gameData.getGame(gameName).getBoard().resetBoard();
            resetBoard();
            return true;
        } else if(gameData.getGame(gameName).gameOver() == 2) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("End of round");
            alert.setHeaderText(null);
            alert.setContentText("player 2 Won!");

            alert.showAndWait();
            System.out.println("Player 2 won!");
            gameData.getGame(gameName).getBoard().resetBoard();
            resetBoard();
            return true;
        } else if(gameData.getGame(gameName).gameOver() == 3) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("End of round");
            alert.setHeaderText(null);
            alert.setContentText("Draw!");

            alert.showAndWait();
            System.out.println("draw!");
            gameData.getGame(gameName).getBoard().resetBoard();
            resetBoard();
            return true;
        }

        return false;
    }

    public void resetBoard() {
        tiles = board.getTiles();
        GameBoardLogic board = gameData.getGame(gameName).getBoard();

        for(Button button : tiles) {
            button.setGraphic(null);
        }

        if(gameName.equals("Reversi")) {
            updateReversiBoard();
        }
    }

    public void updateReversiBoard() {
        tiles = board.getTiles();
        GameLogic logic = gameData.getGame(gameName);
        ArrayList moves = logic.getMoves(1);
        GameBoardLogic board = gameData.getGame(gameName).getBoard();
        System.out.println(moves);
        int[] b = board.getBoard();

        for(int i = 0; i < tiles.length; i++) {
            if(!moves.contains(i)) {
                tiles[i].setDisable(true);
            } else {
                tiles[i].setDisable(false);
            }

            if(b[i] == 1) {
                // draw a black disc
                Image image = new Image(getClass().getResourceAsStream("../web/black-circle.png"), gameButtonWidth - 15, gameButtonHeight - 15, false, false);
                ImageView imageView = new ImageView(image);
                tiles[i].setGraphic(imageView);
            }

            if(b[i] == 2) {
                // draw a white disc
                Image image = new Image(getClass().getResourceAsStream("../web/white-circle.png"), gameButtonWidth - 15, gameButtonHeight - 15, false, false);
                ImageView imageView = new ImageView(image);
                tiles[i].setGraphic(imageView);
            }
        }
    }
}
