package project.mvc.view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import project.gameframework.GameBoardLogic;
import project.gameframework.aistrategies.MinimaxStrategy;
import project.gamemodules.GameData;
import project.gamemodules.reversigame.ReversiMinimaxStrategy;
import project.gamemodules.tictactoegame.TicTacToeMinimaxStrategy;

import java.util.Optional;

public abstract class GameBoard extends FlowPane {

    private int gameBoardWidth;
    private int gameBoardHeight;
    private int gameBoardDimension;

    // Deze twee voeg ik toe zodat je de grootte van de knop kan bepalen. Als je de knoppen standaard op 150x150 past het wel bij TicTacToe op het scherm maar niet bij reversi - Rainier
    private double gameButtonWidth;
    private double gameButtonHeight;

    private int turn;
    private int counter;

    private Button[] tiles;
    private GameBoardLogic gameBoard;

    private GameData gameData;
    private GridPane gameLayout;

    String gameName;

    private MinimaxStrategy minimaxStrategy;

    public GameBoard(int width, int height, double buttonHeight, double buttonWidth, GameBoardLogic gameBoard, GameData gameData, String gameName, GridPane layout) {
        gameBoardWidth = width;
        gameBoardHeight = height;
        gameBoardDimension = width * height;
        gameButtonWidth = buttonWidth;
        gameButtonHeight = buttonHeight;
        gameLayout = layout;

        this.gameName = gameName;

        this.gameData = gameData;

        this.gameBoard = gameBoard;

        if(gameName.equals("Tic-tac-toe")) {
            this.minimaxStrategy = new TicTacToeMinimaxStrategy();
        }

        if(gameName.equals("Reversi")) {
            this.minimaxStrategy = new ReversiMinimaxStrategy();
        }

        gameData.getGame("Reversi").getBoard().printBoard();
        turn = 1;
        counter = 0;
        tiles = new Button[width * height];
        drawBoard();
    }

    public void drawBoard() {
        int id = 0;
        for (int row = 0; row < gameBoardHeight; row++) {
            for (int column = 0; column < gameBoardWidth; column++) {
                id = ((row * gameBoardWidth) + column);

                tiles[id] = new Button("");
                tiles[id].setMinSize(gameButtonWidth, gameButtonHeight);
                tiles[id].setId(Integer.toString(id));

                Button btn = tiles[id];
                btn.setOnAction(e -> {
                    turn = 1;
                    int ID = Integer.parseInt(btn.getId());
                    setMove(ID, turn, btn);
                    setAIMove();
                });
                gameLayout.add(tiles[id], column, row);
            }
        }
    }

    private void setAIMove() {
        // first get the gameboard which we can give to the algorithm
        GameBoardLogic board = gameData.getGame(gameName).getBoard();
        int move = minimaxStrategy.getBestMove(board, 2);
        try {
            setMove(move, 2, tiles[move]);
        } catch (ArrayIndexOutOfBoundsException e) {
            // ignore
        }
        gameData.getGame(gameName).getBoard().printBoard();
    }

    private boolean gameOver() {
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

    private void resetBoard() {
        GameBoardLogic board = gameData.getGame(gameName).getBoard();

        for(Button button : tiles) {
            button.setText("");
        }

        if(board.getGame().equals("TicTacToe")) {
            for(Button button : tiles) {
                button.setGraphic(null);
            }
        }
    }

    private void setMove(int pos, int state, Button btn) {
        GameBoardLogic board = gameData.getGame(gameName).getBoard();
        if(board.getGame().equals("TicTacToe")) {
            if(state == 1) {
                Image image = new Image(getClass().getResourceAsStream("../../web/ttt-black-circle.png"), gameButtonWidth - 20, gameButtonHeight - 20, false, false);
                ImageView imageView = new ImageView(image);
                btn.setGraphic(imageView);
            } else if (state == 2) {
                Image image = new Image(getClass().getResourceAsStream("../../web/ttt-black-times.png"), gameButtonWidth - 20, gameButtonHeight - 20, false, false);
                ImageView imageView = new ImageView(image);
                btn.setGraphic(imageView);
            }
        } else if(board.getGame().equals("Reversi")) {
            if(state == 1) {
                Image image = new Image(getClass().getResourceAsStream("../../web/black-circle.png"), gameButtonWidth - 15, gameButtonHeight - 15, false, false);
                ImageView imageView = new ImageView(image);
                btn.setGraphic(imageView);
            } else if (state == 2) {
                Image image = new Image(getClass().getResourceAsStream("../../web/white-circle.png"), gameButtonWidth - 15, gameButtonHeight - 15, false, false);
                ImageView imageView = new ImageView(image);
                btn.setGraphic(imageView);
            }
        }
        gameData.getGame(gameName).doMove(pos, state);
        counter++;
    }

    public int getCounter(){
        return counter;
    }

    public Button[] getTiles(){
        return tiles;
    }

    public Label getResult(){

        Label result = new Label("TEST");
        result.setTextFill(Color.WHITE);
        if(counter == 8){
            result.setText("Game over!");
        }
        return result;
    }

    public int getGameBoardWidth() {
        return gameBoardWidth;
    }

    public int getGameBoardHeight() {
        return gameBoardHeight;
    }

    public int getGameBoardDimension() {
        return gameBoardDimension;
    }

    public double getGameButtonWidth() {
        return gameButtonWidth;
    }

    public double getGameButtonHeight() {
        return gameButtonHeight;
    }
}
