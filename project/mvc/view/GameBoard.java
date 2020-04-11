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
import project.gameframework.GameLogic;
import project.gameframework.aistrategies.MinimaxStrategy;
import project.gamemodules.GameData;
import project.gamemodules.reversigame.ReversiMinimaxStrategy;
import project.gamemodules.tictactoegame.TicTacToeMinimaxStrategy;
import project.mvc.model.ApplicationModel;

import java.util.ArrayList;
import java.util.Optional;
import project.mvc.view.BoardLogic;

public abstract class GameBoard extends FlowPane {

    private int gameBoardWidth;
    private int gameBoardHeight;
    private int gameBoardDimension;

    private double gameButtonWidth;
    private double gameButtonHeight;

    private int turn;
    private int counter;
    private String player1;
    private String player2;

    private Button[] tiles;
    private BoardLogic BoardLogic;

    private GameData gameData;
    private GridPane gameLayout;
    private String gameName;
    private GameBoardLogic gameBoardLogic;
    private BoardLogic boardLogic;

    private MinimaxStrategy minimaxStrategy;

    public GameBoard(int width, int height, double buttonHeight, double buttonWidth, GameBoardLogic gameBoard, ApplicationModel model, String name, GridPane layout) {
        gameBoardWidth = width;
        gameBoardHeight = height;
        gameBoardDimension = width * height;
        gameButtonWidth = buttonWidth;
        gameButtonHeight = buttonHeight;
        gameLayout = layout;
        gameName = name;
        gameData = model.getGameData();

        this.gameBoardLogic = gameBoard;

        if(gameName.equals("Tic-tac-toe")) {
            this.minimaxStrategy = new TicTacToeMinimaxStrategy();
        }

        if(gameName.equals("Reversi")) {
            this.minimaxStrategy = new ReversiMinimaxStrategy();
        }

        this.boardLogic = new BoardLogic(gameData, gameName, gameBoardWidth, gameBoardHeight, gameBoardDimension, gameButtonHeight, gameButtonWidth, this);

        turn = 1;
        counter = 0;
        tiles = new Button[width * height];
        drawBoard();

        if(gameName.equals("Reversi")) {
            boardLogic.updateReversiBoard();
        }
    }

    public void drawBoard() {
        int id = 0;
        System.out.println(gameName);
        for (int row = 0; row < gameBoardHeight; row++) {
            for (int column = 0; column < gameBoardWidth; column++) {
                id = ((row * gameBoardWidth) + column);

                tiles[id] = new Button("");
                tiles[id].setMinSize(gameButtonWidth, gameButtonHeight);
                tiles[id].setId(Integer.toString(id));

                Button btn = tiles[id];
                btn.setOnAction(e -> {
                    GameLogic logic = gameData.getGame(gameName);
                    if(gameName.equals("Tic-tac-toe")) {
                        turn = 1;
                        int ID = Integer.parseInt(btn.getId());
                        if(logic.isValid(ID, turn)) {
                            setMove(ID, turn, btn);
                            if (!boardLogic.gameOver()) {
                                setAIMove();
                                boardLogic.gameOver();
                            }
                        }
                    } else if(gameName.equals("Reversi")) {
                        int ID = Integer.parseInt(btn.getId());
                        //if(logic.isValid(ID, 2)) {
                            boardLogic.updateReversiBoard();
                            setMove(ID, 1, btn);
                            if (!boardLogic.gameOver()) {
                                setAIMove();
                                boardLogic.updateReversiBoard();
                                boardLogic.gameOver();
                            }
                        //}
                    }
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
        //gameData.getGame(gameName).getBoard().printBoard();
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
    public int getTurn(){
        return turn;
    }
    public Button[] getTiles(){
        return tiles;
    }

    public BoardLogic getBoardLogic(){
        return boardLogic;
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

    public String getPlayer1(){
        return player1;
    }

    public String getPlayer2(){
        return player2;
    }

    public void setPlayers(String player1, String player2){
        this.player1 = player1;
        this.player2 = player2;
    }
}





