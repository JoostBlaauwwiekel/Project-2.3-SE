package project.mvc.view;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.mvc.controller.ApplicationController;

/**
 * This is the abstract class GameBoard which defines the main structure of game boards.
 */
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

    private Stage window;

    /**
     * This method makes a GameBoard with the given parameters.
     *
     * @param width the width of the board.
     * @param height the height of the board.
     * @param buttonHeight the height of the buttons
     * @param buttonWidth the width of the buttons.
     * @param layout the layout of the board.
     * @param topBar the corresponding board view's top bar.
     * @param controller  the MVC controller.
     */
    protected GameBoard(Stage window, int width, int height, double buttonHeight, double buttonWidth, GridPane layout, HBox topBar, ApplicationController controller){
        this.window = window;
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
     *
     * @param winner the winner.
     * @param gameStatus the current game status. To set the players' scores.
     * @return true because the game has ended.
     */
    private boolean setGameStatus(String winner, int gameStatus){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("End of round");
        alert.setHeaderText(null);
        alert.setContentText(winner);
        alert.initOwner(window);
        alert.showAndWait();

        setScorePlayer(gameStatus);
        setGameStats();

        resetBoard();
        return true;
    }

    /**
     * This method is used to return the width of the gameButtons.
     *
     * @return the game button width.
     */
    protected double getGameButtonWidth() {
        return gameButtonWidth;
    }

    /**
     * This method is used to return the Height of the gameButtons.
     *
     * @return the game button height.
     */
    protected double getGameButtonHeight() {
        return gameButtonHeight;
    }

    /**
     * This method is used to return the controller of the application.
     *
     * @return the MVC controller.
     */
    protected ApplicationController getController(){
        return controller;
    }

    /**
     * This method checks if the game is over.
     *
     * @param result the current result of the game.
     * @return false if the game isn't over yet, true if the game has been completed/
     */
    protected boolean gameOver(int result) {
        if (result == 1) {
            return setGameStatus(player1 + " won!", 1);
        }
        else if (result == 2) {
            return setGameStatus(player2 + " won!", 2);
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
     * This method makes the buttons not clickable after it's been reset or restarted.
     */
    public void unSetButtons(){
        for(Button tile : tiles){
            tile.setOnAction(e -> {});
        }
    }

    /**
     * This method returns the tiles of the board.
     * @return the tiles.
     */
    public Button[] getTiles(){ return tiles; }

    /**
     * This method should reset the UI board.
     */
    public abstract void resetBoard();

    /**
     * This method should update the board when a game is being played offline.
     */
    public abstract void updateOfflineBoard();

    /**
     * This method should enable all buttons on the board. All moves that can be clicked.
     */
    public abstract void setButtons();

    /**
     * This method is used to update the board when playing an online game.
     *
     * @param turn the current turn.
     */
    public abstract void updateBoard(int turn);

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
        Label turnLabel = GameBoardView.makeLabel("Turn: " + turnPlayer, 300, 50, "left");
        turnLabel.setPadding(new Insets(0, 0, 0, 20));
        turnLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        gameStats.getChildren().clear();
        gameStats.getChildren().addAll(turnLabel, players, scores);
        gameStats.setStyle("-fx-font-size: 30");
        gameTopBar.getChildren().removeAll(gameStats);
        gameTopBar.getChildren().add(gameStats);
    }

    /**
     * This method returns who's turn it is to make a move.
     *
     * @return the turn.
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
     *
     * @param scorePlayerA the score of the corresponding player.
     * @param scorePlayerB the score of the other corresponding player.
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
     *
     * @param playerA the corresponding player.
     * @param playerB the other corresponding player.
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
     *
     * @param player the corresponding player.
     */
    public void setScorePlayer(int player){
        if(player == 1){
            scorePlayer1++;
        }
        if(player == 2){
            scorePlayer2++;
        }
    }

    /**
     * This method sets the players who play on the gameboard.
     *
     * @param player1 the current player1.
     * @param player2 the current player2.
     */
    public void setPlayers(String player1, String player2){
        this.player1 = player1;
        this.player2 = player2;
    }
}
