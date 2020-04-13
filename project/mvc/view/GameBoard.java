package project.mvc.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.mvc.controller.ApplicationController;

public abstract class GameBoard {

    private int gameBoardWidth;
    private int gameBoardHeight;
    private int gameBoardDimension;

    private ApplicationController controller;

    private int turn;
    private int counter;
    private String player1;
    private String player2;
    private int scorePlayer1;
    private int scorePlayer2;

    private HBox players = new HBox();
    private HBox scores = new HBox();
    private VBox gameStats = new VBox();
    private HBox gameTopBar;

    private double gameButtonWidth;
    private double gameButtonHeight;
    private GridPane gameLayout;

    private Button[] tiles;

    protected GameBoard(int width, int height, double buttonHeight, double buttonWidth, GridPane layout, HBox topBar, ApplicationController controller){
        this.controller = controller;
        gameBoardWidth = width;
        gameBoardHeight = height;
        gameBoardDimension = width * height;
        gameButtonWidth = buttonWidth;
        gameButtonHeight = buttonHeight;
        gameLayout = layout;
        gameTopBar = topBar;
        tiles = new Button[width * height];

        turn = 1;
        counter = 0;

        setPlayers("Player", "AI");
        drawBoard();
    }

    private void drawBoard() {
        setGameStats();
        int id;
        for (int row = 0; row < gameBoardHeight; row++) {
            for (int column = 0; column < gameBoardWidth; column++) {
                id = ((row * gameBoardWidth) + column);
                tiles[id] = new Button("");
                tiles[id].setMinSize(gameButtonWidth, gameButtonHeight);
                tiles[id].setId(Integer.toString(id));
                gameLayout.add(tiles[id], column, row);
            }
        }
    }

    private boolean setGameStatus(String winner, int gameStatus){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("End of round");
        alert.setHeaderText(null);
        alert.setContentText(winner);
        alert.showAndWait();

        setScorePlayer(gameStatus);
        System.out.println("here");
        setGameStats();

        resetBoard();
        return true;
    }

    protected double getGameButtonWidth() {
        return gameButtonWidth;
    }

    protected double getGameButtonHeight() {
        return gameButtonHeight;
    }

    protected ApplicationController getController(){
        return controller;
    }

    protected boolean gameOver(int result) {
        if (result == 1) {
            return setGameStatus("Player 1 won!", 1);
        }
        else if (result == 2) {
            return setGameStatus("Player 2 won!", 2);
        }
        else if (result == 3) {
            return setGameStatus("Draw!", 3);
        }
        else if(result == 4){
            return setGameStatus("Game has reset!", 4);
        }
        else
            return false;
    }

    public void unSetButtons(){
        for(Button tile : tiles){
            tile.setOnAction(e -> {});
        }
    }

    public Button[] getTiles(){ return tiles; }

    public abstract void resetBoard();

    public abstract void setAImove(int move);

    public abstract void setButtons();

    public abstract void setMoveForEitherParty(int move, int turn);

    public void setGameStats(){
        setPlayersHBox();
        setScoresHBox();
        String turnPlayer;
        if(getTurn() == 1){
            turnPlayer = player1;
        }
        else{
            turnPlayer = player2;
        }
        Label turnLabel = GameBoardView.makeLabel("Turn: " + turnPlayer, 300, 50, "left");;
        gameStats.getChildren().clear();
        gameStats.getChildren().addAll(turnLabel, players, scores);
        gameTopBar.getChildren().removeAll(gameStats);
        gameTopBar.getChildren().add(gameStats);
    }

    public int getTurn(){
        return turn;
    }

    private void setScoresHBox(){
        Label score1 = GameBoardView.makeLabel(Integer.toString(scorePlayer1), 150, 75, "center");
        Label between = GameBoardView.makeLabel("-", 50, 75, "center");
        Label score2 = GameBoardView.makeLabel(Integer.toString(scorePlayer2), 150, 75, "center");
        scores.getChildren().clear();
        scores.getChildren().addAll(score1, between, score2);
    }

    private void setPlayersHBox(){
        Label player1Label = GameBoardView.makeLabel(player1, 150, 75, "center");
        Label versus = GameBoardView.makeLabel("VS", 50, 75, "center");
        Label player2Label = GameBoardView.makeLabel(player2, 150, 75, "center");
        players.getChildren().clear();
        players.getChildren().addAll(player1Label, versus, player2Label);
    }

    public void setScorePlayer(int player){
        if(player == 1){
            scorePlayer1++;
        }
        if(player == 2){
            scorePlayer2++;
        }
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
