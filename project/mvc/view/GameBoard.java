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

    /**
     * This method makes a GameBoard with the given params.
     * @param width
     * @param height
     * @param buttonHeight
     * @param buttonWidth
     * @param layout
     * @param topBar
     * @param controller
     */
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

    /**
     * This method draws the board onto the window.
     */
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

    /**
     * This method sets the gameStatus after a round.
     * Also ups the score of the winner.
     * @param winner
     * @param gameStatus
     * @return
     */
    private boolean setGameStatus(String winner, int gameStatus){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("End of round");
        alert.setHeaderText(null);
        alert.setContentText(winner);
        alert.showAndWait();

        setScorePlayer(gameStatus);
        setGameStats();

        resetBoard();
        return true;
    }

    /**
     * This method is used to return the width of the gameButtons.
     * @return
     */
    protected double getGameButtonWidth() {
        return gameButtonWidth;
    }

    /**
     * This method is used to return the Height of the gameButtons.
     * @return
     */
    protected double getGameButtonHeight() {
        return gameButtonHeight;
    }

    /**
     * This method is used to return the controller of the application.
     * @return
     */
    protected ApplicationController getController(){
        return controller;
    }

    /**
     * This method checks if the game is over.
     * @param result
     * @return
     */
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

    /**
     * This method makes the buttons clickable again after it's been reset or restarted.
     */
    public void unSetButtons(){
        for(Button tile : tiles){
            tile.setOnAction(e -> {});
        }
    }

    /**
     * This method returns the tiles of the board.
     * @return
     */
    public Button[] getTiles(){ return tiles; }

    public abstract void resetBoard();

    public abstract void setAImove();

    public abstract void setButtons();

    public abstract void setMoveForEitherParty(int turn);

    /**
     * This method sets the game statistics such as players, turn and scores.
     */
    public void setGameStats(){
        setPlayersHBox(player1, player2);
        setScoresHBox(scorePlayer1, scorePlayer2);
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

    /**
     * This method returns who's turn it is to make a move.
     * @return
     */
    public int getTurn(){
        return turn;
    }

    /**
     * This method resets the scores.
     */
    public void resetScores(){
        scorePlayer1 = 0;
        scorePlayer2 = 0;
    }

    /**
     * This method shows the scores of the players on the board.
     * @param scorePlayerA
     * @param scorePlayerB
     */
    public void setScoresHBox(int scorePlayerA, int scorePlayerB){
        Label score1 = GameBoardView.makeLabel(Integer.toString(scorePlayerA), 150, 75, "center");
        Label between = GameBoardView.makeLabel("-", 50, 75, "center");
        Label score2 = GameBoardView.makeLabel(Integer.toString(scorePlayerB), 150, 75, "center");
        scores.getChildren().clear();
        scores.getChildren().addAll(score1, between, score2);
    }

    /**
     * This method sets the players on the board.
     * @param playerA
     * @param playerB
     */
    public void setPlayersHBox(String playerA, String playerB){
        Label player1Label = GameBoardView.makeLabel(playerA, 150, 75, "center");
        Label versus = GameBoardView.makeLabel("VS", 50, 75, "center");
        Label player2Label = GameBoardView.makeLabel(playerB, 150, 75, "center");
        players.getChildren().clear();
        players.getChildren().addAll(player1Label, versus, player2Label);
    }

    /**
     * This method increments the score of an individual player after a win.
     * @param player
     */
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

    /**
     * This method sets the players who play on the gameboard.
     * @param player1
     * @param player2
     */
    public void setPlayers(String player1, String player2){
        this.player1 = player1;
        this.player2 = player2;
    }
}
