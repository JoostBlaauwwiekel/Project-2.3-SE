package project.mvc.view;

import javafx.scene.control.Button;
import project.gameframework.GameBoardView;
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
public class ApplicationView {

    private static final String TICTACTOE = "Tic Tac Toe";

    private ApplicationController applicationController;
    private ApplicationModel applicationModel;

    private Stage primaryStage;

    private Scene mainScene;
    private Scene chooseGameScene;
    private Scene chooseGameModeScene;
    private Scene ticTacToeScene;

    private AbstractScreenView mainView;
    private AbstractScreenView chooseGameView;
    private AbstractScreenView chooseGameModeView;

    private GameBoardView ticTacToeView;
    private GameBoardView reversiView;

    public ApplicationView(ApplicationController applicationController, ApplicationModel applicationModel){
        this.applicationController = applicationController;
        this.applicationModel = applicationModel;
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void initializeApplicationScreens(){
        primaryStage.setTitle("StartApp screen");
        mainView = new MainView(primaryStage);

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

        chooseGameView.getButtons().get("Previous scene").setOnAction(e -> {
            chooseGameView.getWindow().setScene(mainScene);
            chooseGameView.getWindow().setTitle("StartApp screen");
        });
    }

    private void setOnActionChooseGameModeViewButtons(){
        chooseGameModeView.getButtons().get("Player vs AI").setOnAction(e -> {
            chooseGameModeView.getWindow().setScene(ticTacToeScene);
            chooseGameModeView.getWindow().setTitle(TICTACTOE);
        });

        chooseGameModeView.getButtons().get("Player vs Server").setOnAction(e -> {
            chooseGameModeView.getWindow().setScene(ticTacToeScene);
            chooseGameModeView.getWindow().setTitle(TICTACTOE);
        });

        chooseGameModeView.getButtons().get("Go back").setOnAction(e -> {
            chooseGameModeView.getWindow().setScene(chooseGameScene);
            chooseGameModeView.getWindow().setTitle("Choose a game");
        });
    }

    private void setOnActionGameViewButtons(){
        ticTacToeView.getGameButtons().get("Exit Tic Tac Toe").setOnAction(e -> {
            ticTacToeView.getWindow().setScene(chooseGameModeScene);
            ticTacToeView.getWindow().setTitle("Choose a game");
        });
    }
}
