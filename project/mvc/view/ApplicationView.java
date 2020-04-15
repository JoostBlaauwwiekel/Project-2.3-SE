package project.mvc.view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;
import project.mvc.controller.ApplicationController;
import project.mvc.model.ApplicationModel;
import project.mvc.view.mainscreen.ErrorBox;
import project.mvc.view.mainscreen.MainView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.mvc.view.mainscreen.OkayBox;

import java.awt.GraphicsEnvironment;
import java.util.HashMap;

/**
 * In the ApplicationView class all views are accessible. The primaryStage is set up and the necessary set-on-action
 * commands will be set.
 */
public class ApplicationView implements ObserverView {

    private static final String TICTACTOE = "Tic-tac-toe";
    private static final String REVERSI = "Reversi";

    private ApplicationController applicationController;
    private ApplicationModel applicationModel;

    private HashMap<String, GameBoardView> games;

    private Stage primaryStage;

    private Scene mainScene;
    private Scene chooseGameScene;
    private Scene chooseGameModeScene;
    private Scene optionsScene;
    private Scene serverOptionsScene;
    private Scene ticTacToeScene;
    private Scene reversiScene;

    private ScreenView mainView;
    private ScreenView chooseGameView;
    private ScreenView chooseGameModeView;

    private GameBoardView ticTacToeView;
    private GameBoardView reversiView;

    private ScreenBorderPaneView serverOptionsView;
    private ScreenBorderPaneView optionsView;

    private ErrorBox errorBox;

    /**
     * The default constructor for the ApplicationView class.
     * @param applicationController
     * @param applicationModel
     */
    public ApplicationView(ApplicationController applicationController, ApplicationModel applicationModel){
        this.applicationController = applicationController;
        this.applicationModel = applicationModel;
        games = new HashMap<>();
    }

    /**
     * This method is called once the subject notifies each and every observer.
     * Here the observer (this class) is notified that the game state has been changed and that new game data is
     * available in the ApplicationModel class.
     */
    public void update(){
        int currentMove = applicationModel.getCurrentMove();
        String game = applicationModel.getCurrentGame();
        int turn = applicationModel.getCurrentTurn();

        if((applicationModel.getGameStatus() == 6) && (!applicationController.getOffline())){
            Platform.runLater(() -> {
                serverOptionsView.getListViews().get("ChallengeList").getItems().remove(applicationModel.getCurrentChallenger() + ". Challenge number is: " + applicationModel.getCurrentChallengerNr());
            });
        }
        else if(applicationModel.getGameStatus() == 5){
            Platform.runLater(() -> {
                serverOptionsView.getListViews().get("PlayerList").getItems().clear();
                serverOptionsView.getListViews().get("PlayerList").getItems().addAll(applicationController.getPlayerSet());
            });
        }
        else if((applicationModel.getGameStatus() == 4) && (!applicationController.getOffline())) {
            Platform.runLater(() -> {
                serverOptionsView.getListViews().get("ChallengeList").getItems().add(applicationModel.getCurrentChallenger() + ". Challenge number is: " + applicationModel.getCurrentChallengerNr());
            });
        }
        else if((applicationModel.getGameStatus() == 3) && (!applicationController.getOffline())) {
            resetCurrentBoard(game);
        }
        else if((applicationModel.getGameStatus() == 2) && (!applicationController.getOffline())){
            resetCurrentBoard(game);
            Platform.runLater(() -> {
                if(game.equals(REVERSI)){
                    reversiView.getWindow().setScene(serverOptionsScene);
                    reversiView.getWindow().setTitle("Reversi Online");
                }
                else if(game.equals(TICTACTOE)){
                    ticTacToeView.getWindow().setScene(serverOptionsScene);
                    ticTacToeView.getWindow().setTitle("Tic Tac Toe Online");
                }
                serverOptionsView.getEventLabel().setText(applicationModel.getGameData().getFormattedGameResult());
                serverOptionsView.getScoreLabel().setText("Wins: " + applicationModel.getGameData().getWins() + " Losses: " + applicationModel.getGameData().getLosses() + " Draws: " + applicationModel.getGameData().getDraws());
            });
        }
        else if((applicationModel.getGameStatus() == 1) && (!applicationController.getOffline())){
            disableBoardPlayability(game);
            resetCurrentBoard(game);
            Platform.runLater(() -> {
                if(!applicationModel.getGameData().getInTournament()) {
                    if (game.equals(REVERSI)) {
                        serverOptionsView.getWindow().setScene(reversiScene);
                        serverOptionsView.getWindow().setTitle(REVERSI + " game");
                        reversiView.getGameBoard().setPlayersHBox("Our AI", "Opponent's AI");
                    } else if (game.equals(TICTACTOE)) {
                        serverOptionsView.getWindow().setScene(ticTacToeScene);
                        serverOptionsView.getWindow().setTitle(TICTACTOE + " game");
                        ticTacToeView.getGameBoard().setPlayersHBox("Our AI", "Opponent's AI");
                    }
                }

            });
        }
        else if(!applicationModel.getGameData().getGameMode().equals("idle") && !applicationModel.getGameData().getBoardInitialized() && (!applicationController.getOffline())) {
            disableBoardPlayability(game);
        }
        else if (applicationModel.isOffline()) {
            games.get(game).getGameBoard().updateOfflineBoard();
        } else {
            if(currentMove != -1) {
                games.get(game).getGameBoard().updateBoard(turn);
            }
        }
    }

    /**
     * This method returns a child of ScreenBorderPaneView, in this case the serverOptionsView.
     * @return serverOptionsView.
     */
    public ScreenBorderPaneView getServerOptionsView(){
        return serverOptionsView;
    }

    /**
     * This method initializes the application screen and initializes all the views and scenes necessary in order for
     * the application to work.
     *
     * @param stage the current window.
     */
    public void initializeApplicationScreens(Stage stage){
        this.primaryStage = stage;

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../web/icon.png")));
        primaryStage.setTitle("Universal Game Launcher");
        mainView = new MainView(primaryStage, applicationController);

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            mainView.closeApplication(primaryStage);
        });

        primaryStage.setFullScreenExitHint("");
        primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                primaryStage.setFullScreen(true);
                if (primaryStage.getTitle().contains(TICTACTOE)){
                    ticTacToeView.setLeftRightPane(true);
                }
            }
        });

        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.F11 == event.getCode()){
                primaryStage.setFullScreen(true);
                if (primaryStage.getTitle().contains(TICTACTOE)){
                    ticTacToeView.setLeftRightPane(true);
                }
            }
        });

        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode() && GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight() > 1000 && GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth() > 1000) {
                primaryStage.sizeToScene();
                if (primaryStage.getTitle().contains(TICTACTOE)){
                    ticTacToeView.setLeftRightPane(false);
                    primaryStage.sizeToScene();
                }
            }
        });

        primaryStage.setResizable(false);

        //Image image = new Image(getClass().getResourceAsStream("../../web/icon.png"));
        //primaryStage.getIcons().add(image);

        // Declare and initialize the main scene. And the error box.
        mainScene = new Scene(mainView, 900, 600);
        errorBox = new ErrorBox();

        // Declare and initialize the chooseGameScene scene.
        chooseGameScene = mainView.getSceneUnderneath();
        chooseGameView = mainView.getViewUnderneath();

        // Declare and initialize the chooseGameModeScene scene.
        chooseGameModeScene = chooseGameView.getSceneUnderneath();
        chooseGameModeView = chooseGameView.getViewUnderneath();

        // Declare and initialize the Tic-tac-toe scene and view.
        ticTacToeScene = chooseGameModeView.getGameScenes().get(TICTACTOE);
        ticTacToeView = chooseGameModeView.getGameBoardViews().get(TICTACTOE);

        // Declare and initialize the Reversi scene and view.
        reversiScene = chooseGameModeView.getGameScenes().get(REVERSI);
        reversiView = chooseGameModeView.getGameBoardViews().get(REVERSI);

        optionsScene = mainView.getSceneOverhead();
        optionsView = mainView.getBorderPaneViewUnderneath();

        serverOptionsScene = chooseGameModeView.getSceneUnderneath();
        serverOptionsView = chooseGameModeView.getBorderPaneViewUnderneath();

        games.put(TICTACTOE, ticTacToeView);
        games.put(REVERSI, reversiView);

        setOnActionAllButtons();

        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * This method contains each set on View buttons method. It is a grouping of functions.
     */
    private void setOnActionAllButtons(){
        setMainVButtons();
        setOptionsVButtons();
        setChooseGameVButtons();
        setGameModeVButtons();
        setServerOptionsVButtons();
        setGameVButtons();
    }

    /**
     * This method sets all Main View buttons.
     */
    private void setMainVButtons(){
        mainView.getButtons().get("Play").setOnAction(e -> {
            mainView.getWindow().setScene(chooseGameScene);
            mainView.getWindow().setTitle("Universal Game Launcher");
        });

        mainView.getButtons().get("Options").setOnAction(e -> {
             mainView.getWindow().setScene(optionsScene);
             mainView.getWindow().setTitle("Options");
        });

        mainView.getButtons().get("Exit").setOnAction(e -> mainView.closeApplication(mainView.getWindow()));
    }

    /**
     * This method sets all Options View buttons. Through the options screen a variety of settings can be adjusted in
     * order to configure the application to the user's own will.
     */
    private void setOptionsVButtons() {
        optionsView.getButtons().get("Change settings").setOnAction(e -> {
            int port = parseStringToInteger(optionsView.getTextFields().get("Port").getText());
            float timeOut = parseStringToFloat(optionsView.getTextFields().get("Timeout").getText().strip());
            applicationController.setSettings(optionsView.getTextFields().get("Username").getText().strip(), optionsView.getTextFields().get("IP Address").getText().strip(), port, timeOut);
            OkayBox okay = new OkayBox();
            okay.display("Change settings", "You just changed the settings!");
        });

        optionsView.getButtons().get("Go back").setOnAction(e -> {
            optionsView.getWindow().setScene(mainScene);
            optionsView.getWindow().setTitle("Main screen");
        });

        optionsView.getSlider().setOnMouseReleased(e -> {
            applicationController.setAIDifficulty((int) optionsView.getSlider().getValue());
        });
    }

    /**
     * This method sets all Choose Game View buttons.
     */
    private void setChooseGameVButtons(){
        chooseGameView.getButtons().get(TICTACTOE).setOnAction(e -> {
            chooseGameView.getWindow().setScene(chooseGameModeScene);
            chooseGameView.getWindow().setTitle(TICTACTOE);
        });

        chooseGameView.getButtons().get(REVERSI).setOnAction(e -> {
            chooseGameView.getWindow().setScene(chooseGameModeScene);
            chooseGameView.getWindow().setTitle(REVERSI);
        });

        chooseGameView.getButtons().get(" Go back").setOnAction(e -> {
            chooseGameView.getWindow().setScene(mainScene);
            chooseGameView.getWindow().setTitle("Universal Game Launcher");
        });

        errorBox.getConfirm().setOnAction(e -> {
            chooseGameView.getWindow().setScene(chooseGameModeScene);
            if(chooseGameModeView.getWindow().getTitle().equals(TICTACTOE)){
                chooseGameView.getWindow().setTitle(TICTACTOE);
            }
            else if(chooseGameView.getWindow().getTitle().equals(REVERSI)){
                chooseGameView.getWindow().setTitle(REVERSI);
            }
            errorBox.closeWindow();
        });
    }

    /**
     * This method sets all Game Mode View buttons.
     */
    private void setGameModeVButtons(){
        chooseGameModeView.getButtons().get("Player vs AI").setOnAction(e -> {
            applicationController.setOffline(true);
            if(chooseGameModeView.getWindow().getTitle().equals(TICTACTOE)) {
                ticTacToeView.setMode("Player vs AI");
                ticTacToeView.getGameBoard().setPlayersHBox("Player", "AI");
                applicationController.initializeGame(TICTACTOE);
                ticTacToeView.getGameBoard().setButtons();
                ticTacToeView.getGameBoard().resetScores();
                ticTacToeView.setRestartButton(false);
                chooseGameModeView.getWindow().setScene(ticTacToeScene);
                chooseGameModeView.getWindow().setTitle(TICTACTOE + " single player");
            }
            else{
                reversiView.setMode("Player vs AI");
                applicationController.initializeGame(REVERSI);
                reversiView.getGameBoard().setPlayersHBox("Player", "AI");
                reversiView.getGameBoard().resetScores();
                reversiView.getGameBoard().setButtons();
                reversiView.setRestartButton(false);
                chooseGameModeView.getWindow().setScene(reversiScene);
                chooseGameModeView.getWindow().setTitle(REVERSI + " single player");
            }
        });

        chooseGameModeView.getButtons().get("AI vs Server").setOnAction(e -> {
            applicationController.setOffline(false);
            applicationController.setInLobby(true);
            if(applicationController.connectToServer()) {
                applicationController.fillPlayerSet();
                chooseGameModeView.getWindow().setScene(serverOptionsScene);
                if (chooseGameModeView.getWindow().getTitle().equals(TICTACTOE)) {
                    applicationController.initializeGame(TICTACTOE);
                    chooseGameModeView.getWindow().setTitle("Tic Tac Toe Online");
                } else if (chooseGameModeView.getWindow().getTitle().equals(REVERSI)) {
                    applicationController.initializeGame(REVERSI);
                    chooseGameModeView.getWindow().setTitle("Reversi Online");
                }
                applicationController.joinLobby();
            }
            else{
                mainView.showErrorWindow(errorBox);
            }
        });

        chooseGameModeView.getButtons().get("Go back").setOnAction(e -> {
            chooseGameModeView.getWindow().setScene(chooseGameScene);
            chooseGameModeView.getWindow().setTitle("Universal Game Launcher");
        });
    }

    /**
     * This method sets all actions for the Server Options View buttons. Most interactions with the server are through
     * these buttons.
     */
    private void setServerOptionsVButtons(){
        serverOptionsView.getButtons().get("Go back").setOnAction(e -> {
            serverOptionsView.getWindow().setScene(chooseGameModeScene);
            applicationController.setInLobby(false);
            applicationController.disconnectFromServer();
            applicationController.clearActionLabel();
            if(serverOptionsView.getWindow().getTitle().contains("Tic Tac Toe")) {
                serverOptionsView.getWindow().setTitle(TICTACTOE);
            }
            else if(serverOptionsView.getWindow().getTitle().contains(REVERSI)) {
                serverOptionsView.getWindow().setTitle(REVERSI);
            }
        });

        serverOptionsView.getButtons().get("Refresh list").setOnAction(e -> {
            applicationController.fillPlayerSet();
        });

        serverOptionsView.getButtons().get("Challenge!").setOnAction(e -> applicationController.challengePlayer(serverOptionsView.getListViews().get("PlayerList").getSelectionModel().getSelectedItem()));

        serverOptionsView.getButtons().get("Accept challenge").setOnAction(e -> {
            if(!serverOptionsView.getListViews().get("ChallengeList").getSelectionModel().isEmpty()) {
                String challenge = serverOptionsView.getListViews().get("ChallengeList").getSelectionModel().getSelectedItem();
                String regex = ". Challenge number is: ";
                String challenger = challenge.substring(0, challenge.indexOf(regex));
                int challengeNr = Integer.parseInt((challenge.substring(challenge.lastIndexOf(": ") + 2)));
                if(serverOptionsView.getWindow().getTitle().contains(REVERSI)){
                    serverOptionsView.getWindow().setScene(reversiScene);
                    reversiView.getGameBoard().setPlayersHBox("Our AI", "Opponent's AI");
                    serverOptionsView.getWindow().setTitle(REVERSI + " accepted match");
                }
                else if(serverOptionsView.getWindow().getTitle().contains("Tic Tac Toe")){
                    serverOptionsView.getWindow().setScene(ticTacToeScene);
                    ticTacToeView.getGameBoard().setPlayersHBox("Our AI", "Opponent's AI");
                    serverOptionsView.getWindow().setTitle("Tic Tac Toe accepted match");
                }
                applicationController.acceptChallenge(challenger, challengeNr);
            }
            else{
                serverOptionsView.getEventLabel().setText("You did not choose a challenge,\nor you do not have any challenges at this moment.\nRefresh the list and try again!");
            }
        });

        serverOptionsView.getButtons().get("Join Tournament Lobby").setOnAction(e -> {
            applicationController.setInTournament(true);
            if(serverOptionsView.getWindow().getTitle().contains("Tic Tac Toe")) {
                ticTacToeView.getGameBoard().unSetButtons();
                ticTacToeView.setRestartButton(true);
                ticTacToeView.getGameBoard().setPlayersHBox("Our AI", "Opponent's AI");
                serverOptionsView.getWindow().setScene(ticTacToeScene);
                serverOptionsView.getWindow().setTitle(TICTACTOE + " tournament lobby");
                ticTacToeView.getGameBoard().setScoresHBox(applicationModel.getGameData().getWins(), applicationModel.getGameData().getLosses());
            }
            else if(serverOptionsView.getWindow().getTitle().contains(REVERSI)) {
                reversiView.getGameBoard().unSetButtons();
                reversiView.setRestartButton(true);
                reversiView.getGameBoard().setPlayersHBox("Our AI", "Opponent's AI");
                serverOptionsView.getWindow().setScene(reversiScene);
                reversiView.getGameBoard().setScoresHBox(applicationModel.getGameData().getWins(), applicationModel.getGameData().getLosses());
                serverOptionsView.getWindow().setTitle(REVERSI + " tournament lobby");
            }
            applicationController.startOnlineGame();
        });

        serverOptionsView.getButtons().get("Subscribe").setOnAction(e -> {
            applicationController.subscribeToGame();
        });
    }

    /**
     * This method sets all actions for the Game View buttons, the Game View contains the game information and the
     * corresponding game board where either a player can play a game against an the AI or the player can use their
     * own AI to challenge/ play against other player's AI.
     */
    private void setGameVButtons(){
        ticTacToeView.getGameButtons().get("Exit " + TICTACTOE).setOnAction(e -> {
            ticTacToeView.getGameBoard().resetBoard();
            if(ticTacToeView.getWindow().getTitle().contains("tournament lobby")){
                applicationController.setInGame(false);
                applicationController.setInTournament(false);
                chooseGameModeView.getWindow().setScene(serverOptionsScene);
                chooseGameModeView.getWindow().setTitle("Tic Tac Toe Online");
            }
            else {
                ticTacToeView.getWindow().setScene(chooseGameModeScene);
                ticTacToeView.getWindow().setTitle(TICTACTOE);
            }
        });

        ticTacToeView.getGameButtons().get("Restart " + TICTACTOE).setOnAction(e -> ticTacToeView.getGameBoard().gameOver(4));
        reversiView.getGameButtons().get("Restart " + REVERSI).setOnAction(e -> reversiView.getGameBoard().gameOver(4));

        reversiView.getGameButtons().get("Exit " + REVERSI).setOnAction(e -> {
            reversiView.getGameBoard().resetBoard();
            if(ticTacToeView.getWindow().getTitle().contains(" tournament lobby")){
                applicationController.setInGame(false);
                applicationController.setInTournament(false);
                chooseGameModeView.getWindow().setScene(serverOptionsScene);
                chooseGameModeView.getWindow().setTitle("Reversi Online");
            }
            else {
                reversiView.getWindow().setScene(chooseGameModeScene);
                reversiView.getWindow().setTitle(REVERSI);
            }
        });
    }

    /**
     * This method resets the current board graphically.
     * @param game the corresponding game.
     */
    private void resetCurrentBoard(String game){
        Platform.runLater(() -> {
            if(game.equals(TICTACTOE)){
                ticTacToeView.getGameBoard().resetBoard();
            }
            else if(game.equals(REVERSI)){
                reversiView.getGameBoard().resetBoard();
            }
        });
    }

    /**
     * This method is meant to facilitate the online games. It disables the board, ergo no buttons can be pushed and
     * it sets the board initialized for an online game to true.
     * @param game the corresponding game.
     */
    private void disableBoardPlayability(String game){
        games.get(game).getGameBoard().unSetButtons();
        games.get(game).setRestartButton(true);
        applicationController.setBoardInitialized(true);

    }

    /**
     * This is a method to parse a String to an integer.
     * @param text the text to be parsed.
     * @return if successful the integer, else 0.
     */
    private int parseStringToInteger(String text){
        int temp;
        try {
            temp = Integer.parseInt(text);
        }
        catch(NumberFormatException nm){
            temp = 0;
        }
        return temp;
    }

    /**
     * This is a method to parse a String to float.
     *
     * @param text the text to be parsed.
     * @return if successful the integer, else 0.
     */
    private float parseStringToFloat(String text){
        float temp;
        try{
            temp = Float.parseFloat(text);
        }
        catch(NumberFormatException nm){
            temp = 0;
        }
        return temp;
    }
}
