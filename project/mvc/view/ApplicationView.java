package project.mvc.view;

import project.mvc.controller.ApplicationController;
import project.mvc.model.ApplicationModel;
import project.mvc.view.mainscreen.MainView;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * In the ApplicationView class all views be accessible. The primaryStage is set up and the necessary set-on-action
 * commands will be set.
 *
 */
public class ApplicationView implements ObserverView {

    private static final String TICTACTOE = "Tic Tac Toe";
    private static final String REVERSI = "Reversi";

    private ApplicationController applicationController;
    private ApplicationModel applicationModel;

    private Stage primaryStage;

    private Scene mainScene;
    private Scene chooseGameScene;
    private Scene chooseGameModeScene;
    private Scene ticTacToeScene;
    private Scene reversiScene;

    private ScreenView mainView;
    private ScreenView chooseGameView;
    private ScreenView chooseGameModeView;

    private GameBoardView ticTacToeView;
    private GameBoardView reversiView;

    public ApplicationView(ApplicationController applicationController, ApplicationModel applicationModel){
        this.applicationController = applicationController;
        this.applicationModel = applicationModel;
    }

    public void update(){

    }

    public ScreenView getMainView(){
        return mainView;
    }

    public ScreenView getChooseGameView(){
        return chooseGameView;
    }

    public ScreenView getChooseGameModeView(){
        return chooseGameModeView;
    }

    public GameBoardView getTicTacToeView(){
        return ticTacToeView;
    }

    public GameBoardView getReversiView(){
        return reversiView;
    }

    public void initializeApplicationScreens(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle("StartApp screen");
        mainView = new MainView(primaryStage, applicationModel);

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            mainView.closeApplication(primaryStage);
        });

        // Declare and initialize the main scene.
        mainScene = new Scene(mainView, 900, 600);

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
        reversiView = chooseGameModeView.getGameBoardViews().get("Reversi");

        setOnActionAllButtons();

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void setOnActionAllButtons(){
        setOnActionMainViewButtons();
        setOnActionChooseGameViewButtons();
        setOnActionChooseGameModeViewButtons();
        setOnActionGameViewButtons();
    }

    private void setOnActionMainViewButtons(){
        mainView.getButtons().get("Play").setOnAction(e -> {
            mainView.getWindow().setScene(chooseGameScene);
            mainView.getWindow().setTitle("Choose a game");
        });

        mainView.getButtons().get("Exit").setOnAction(e -> mainView.closeApplication(mainView.getWindow()));
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
    }

    private void setOnActionChooseGameModeViewButtons(){
        chooseGameModeView.getButtons().get("Player vs AI").setOnAction(e -> {
            if(chooseGameModeView.getWindow().getTitle().equals(TICTACTOE)) {
                ticTacToeView.setMode("Player vs AI");
                chooseGameModeView.getWindow().setScene(ticTacToeScene);
                chooseGameModeView.getWindow().setTitle(TICTACTOE + "Player vs AI");
            }
            else{
                reversiView.setMode("Player vs AI");
                chooseGameModeView.getWindow().setScene(reversiScene);
                chooseGameModeView.getWindow().setTitle(REVERSI + "Player vs AI");
            }
        });

        chooseGameModeView.getButtons().get("Player vs Server").setOnAction(e -> {
            if(chooseGameModeView.getWindow().getTitle().equals(TICTACTOE)) {
                ticTacToeView.setMode("Player vs Server");
                chooseGameModeView.getWindow().setScene(ticTacToeScene);
                chooseGameModeView.getWindow().setTitle(TICTACTOE + "Player vs Server");
            }
            else{
                reversiView.setMode("Player vs Server");
                chooseGameModeView.getWindow().setScene(reversiScene);
                chooseGameModeView.getWindow().setTitle(REVERSI + "Player vs Server");
            }
        });

        chooseGameModeView.getButtons().get("Go back").setOnAction(e -> {
            chooseGameModeView.getWindow().setScene(chooseGameScene);
            chooseGameModeView.getWindow().setTitle("Choose a game");
        });
    }

    private void setOnActionGameViewButtons(){
        ticTacToeView.getGameButtons().get("Exit " + TICTACTOE).setOnAction(e -> {
            ticTacToeView.getWindow().setScene(chooseGameModeScene);
            ticTacToeView.getWindow().setTitle(TICTACTOE);
        });

        ticTacToeView.getGameButtons().get("Restart " + TICTACTOE).setOnAction(e -> ticTacToeView.getGameBoard().resetBoard());
        reversiView.getGameButtons().get("Restart " + REVERSI).setOnAction(e -> reversiView.getGameBoard().resetBoard());

        reversiView.getGameButtons().get("Exit " + REVERSI).setOnAction(e -> {
           reversiView.getWindow().setScene(chooseGameModeScene);
           reversiView.getWindow().setTitle(REVERSI);
        });
    }
}
