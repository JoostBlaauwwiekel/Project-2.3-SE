package project.mvc.view;

import javafx.application.Platform;
import project.mvc.controller.ApplicationController;
import project.mvc.model.ApplicationModel;
import project.mvc.view.mainscreen.ErrorBox;
import project.mvc.view.mainscreen.MainView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * In the ApplicationView class all views be accessible. The primaryStage is set up and the necessary set-on-action
 * commands will be set.
 *
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

    public ApplicationView(ApplicationController applicationController, ApplicationModel applicationModel){
        this.applicationController = applicationController;
        this.applicationModel = applicationModel;
        games = new HashMap<>();
    }

    public void update(){
        int currentMove = applicationModel.getCurrentMove();
        String game = applicationModel.getCurrentGame();
        int turn = applicationModel.getCurrentTurn();

        if((applicationModel.getGameStatus() == 5) && (!applicationController.getOffline())){
            Platform.runLater(() -> {
                serverOptionsView.getListViews().get("ChallengeList").getItems().remove(applicationModel.getCurrentChallenger() + ". Challenge number is: " + applicationModel.getCurrentChallengerNr());
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
                    reversiView.getWindow().setTitle("Configure your settings for an online Reversi game");
                }
                else if(game.equals(TICTACTOE)){
                    ticTacToeView.getWindow().setScene(serverOptionsScene);
                    ticTacToeView.getWindow().setTitle("Configure your settings for an online Tic Tac Toe game");
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
            games.get(game).getGameBoard().setAImove();
        } else {
            if(currentMove != -1) {
                games.get(game).getGameBoard().setMoveForEitherParty(turn);
            }
        }
    }

    public ScreenBorderPaneView getServerOptionsView(){
        return serverOptionsView;
    }

    public void initializeApplicationScreens(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle("StartApp screen");
        mainView = new MainView(primaryStage, applicationController);

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            mainView.closeApplication(primaryStage);
        });

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

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void setOnActionAllButtons(){
        setOnActionMainViewButtons();
        setOnActionOptionsViewButtons();
        setOnActionChooseGameViewButtons();
        setOnActionChooseGameModeViewButtons();
        setOnServerOptionsViewButtons();
        setOnActionGameViewButtons();
    }

    private void setOnActionMainViewButtons(){
        mainView.getButtons().get("Play").setOnAction(e -> {
            mainView.getWindow().setScene(chooseGameScene);
            mainView.getWindow().setTitle("Choose a game");
        });

        mainView.getButtons().get("Options").setOnAction(e -> {
             mainView.getWindow().setScene(optionsScene);
             mainView.getWindow().setTitle("Options");
        });

        mainView.getButtons().get("Exit").setOnAction(e -> mainView.closeApplication(mainView.getWindow()));
    }

    private void setOnActionOptionsViewButtons() {
        optionsView.getButtons().get("Change details").setOnAction(e -> {
            int port = parsePortNumber();
            applicationController.setSettings(optionsView.getTextFields().get("Username").getText().strip(), optionsView.getTextFields().get("IP Address").getText().strip(), port);
        });

        optionsView.getButtons().get("Go back").setOnAction(e -> {
            optionsView.getWindow().setScene(mainScene);
            optionsView.getWindow().setTitle("Main screen");
        });
    }

    private void setOnActionChooseGameViewButtons(){
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
            chooseGameView.getWindow().setTitle("StartApp screen");
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

    private void setOnActionChooseGameModeViewButtons(){
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
                chooseGameModeView.getWindow().setTitle(TICTACTOE + "Player vs AI");
            }
            else{
                reversiView.setMode("Player vs AI");
                applicationController.initializeGame(REVERSI);
                reversiView.getGameBoard().setPlayersHBox("Player", "AI");
                reversiView.getGameBoard().resetScores();
                reversiView.getGameBoard().setButtons();
                reversiView.setRestartButton(false);
                chooseGameModeView.getWindow().setScene(reversiScene);
                chooseGameModeView.getWindow().setTitle(REVERSI + "Player vs AI");
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
                    chooseGameModeView.getWindow().setTitle("Configure your settings for an online Tic Tac Toe game");
                } else if (chooseGameModeView.getWindow().getTitle().equals(REVERSI)) {
                    applicationController.initializeGame(REVERSI);
                    chooseGameModeView.getWindow().setTitle("Configure your settings for an online Reversi game");
                }
                applicationController.joinLobby();
            }
            else{
                mainView.showErrorWindow(errorBox);
            }
        });

        chooseGameModeView.getButtons().get("Go back").setOnAction(e -> {
            chooseGameModeView.getWindow().setScene(chooseGameScene);
            chooseGameModeView.getWindow().setTitle("Choose a game");
        });
    }

    private void setOnServerOptionsViewButtons(){
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

    private void setOnActionGameViewButtons(){
        ticTacToeView.getGameButtons().get("Exit " + TICTACTOE).setOnAction(e -> {
            ticTacToeView.getGameBoard().resetBoard();
            if(ticTacToeView.getWindow().getTitle().contains("tournament lobby")){
                applicationController.setInGame(false);
                applicationController.setInTournament(false);
                chooseGameModeView.getWindow().setScene(serverOptionsScene);
                chooseGameModeView.getWindow().setTitle("Configure your settings for an online Tic Tac Toe game");
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
                chooseGameModeView.getWindow().setTitle("Configure your settings for an online Reversi game");
            }
            else {
                reversiView.getWindow().setScene(chooseGameModeScene);
                reversiView.getWindow().setTitle(REVERSI);
            }
        });
    }

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

    private void disableBoardPlayability(String game){
        games.get(game).getGameBoard().unSetButtons();
        games.get(game).setRestartButton(true);
        applicationController.setBoardInitialized(true);

    }

    private int parsePortNumber(){
        int port;
        try {
            port = Integer.parseInt(optionsView.getTextFields().get("Port").getText());
        }
        catch(NumberFormatException nm){
            port = 0;
        }
        return port;
    }
}
