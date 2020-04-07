package project.mvc.view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import project.gameframework.GameBoardLogic;
import project.gameframework.aistrategies.MinimaxStrategy;
import project.gamemodules.GameData;
import project.gamemodules.reversigame.ReversiMinimaxStrategy;
import project.gamemodules.tictactoegame.TicTacToeMinimaxStrategy;

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

    String gameName;

    private MinimaxStrategy minimaxStrategy;

    public GameBoard(int width, int height, double buttonHeight, double buttonWidth, GameBoardLogic gameBoard, GameData gameData, String gameName) {
        gameBoardWidth = width;
        gameBoardHeight = height;
        gameBoardDimension = width * height;
        gameButtonWidth = buttonWidth;
        gameButtonHeight = buttonHeight;

        this.gameName = gameName;

        this.gameData = gameData;

        this.gameBoard = gameBoard;

        if(gameName.equals("Tic-tac-toe")) {
            this.minimaxStrategy = new TicTacToeMinimaxStrategy();
        }

        if(gameName.equals("Reversi")) {
            this.minimaxStrategy = new ReversiMinimaxStrategy();
        }

        turn = 1;
        counter = 0;
        tiles = new Button[width * height];
        drawBoard();
    }

    public void drawBoard() {
        for (int i = 0; i < gameBoardDimension; i++) {
            tiles[i] = new Button("");
            // Misschien is het handig dat we de knop grootte kunnen instellen per game. Als je namelijk de knop 150x150 maakt dan past het niet op het scherm bij reversi
            tiles[i].setMinSize(gameButtonWidth, gameButtonHeight);
            tiles[i].setId(Integer.toString(i));
            Button btn = tiles[i];

            btn.setOnMouseClicked(new EventHandler() {
                @Override
                public void handle(Event event) {
                    // handle the player's input
                    turn = 1;
                    int ID = Integer.parseInt(btn.getId());
                    setMove(ID, turn, btn);

                    // now let the ai make a move
                    setAIMove();
                }
            });
        }
    }

    private void setAIMove() {
        // first get the gameboard which we can give to the algorithm
        GameBoardLogic board = gameData.getGame(gameName).getBoard();
        int move = minimaxStrategy.getBestMove(board, 2);
        setMove(move, 2, tiles[move]);
    }

    private void setMove(int pos, int state, Button btn) {
        btn.setText(Integer.toString(state));
        gameData.getGame(gameName).doMove(pos, state);
        gameData.getGame(gameName).getBoard().printBoard();
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
