package project.mvc.view.mainscreen;

import project.mvc.controller.ApplicationController;
import project.mvc.view.ScreenBorderPaneView;
import project.mvc.view.ScreenView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.mvc.view.gameview.ReversiView;
import project.mvc.view.gameview.TicTacToeView;

/**
 * This is the class for the Choose Game Mode View.
 */
public class ChooseGameModeView extends ScreenView {

    private Scene serverOptionsScene;
    private ServerOptionsView serverOptionsView;

    /**
     * This is the default constructor for the choose game mode view.
     *
     * @param window the current window.
     * @param controller the MVC controller.
     */
    public ChooseGameModeView(Stage window, ApplicationController controller) {
        super(window);
        setSpacing(10);
        setPadding(new Insets(20,20,20,20));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #f2b353, #c39540)");

        Label chooseGameTitle = new Label("Choose a mode");
        chooseGameTitle.setTextFill(Color.WHITE);
        chooseGameTitle.setFont(new Font("Arial", 30));
        Button pva = new Button("Player vs AI");
        Button pvs = new Button("AI vs Server");
        Button back = new Button("Go back");

        super.getButtons().put(pva.getText(), pva);
        super.getButtons().put(pvs.getText(), pvs);
        super.getButtons().put(back.getText(), back);

        pva.setMinWidth(150);
        pvs.setMinWidth(150);
        back.setMinWidth(150);

        serverOptionsView = new ServerOptionsView(window, controller);
        serverOptionsScene = new Scene(serverOptionsView, 900,600);

        TicTacToeView ticTacToeView = new TicTacToeView(window, 500, controller);
        Scene ticTacToeScene = new Scene(ticTacToeView, 500, 700);

        ReversiView reversiView = new ReversiView(window, 500, controller);
        Scene reversiScene = new Scene(reversiView, 500, 700);

        super.getGameScenes().put("Tic-tac-toe", ticTacToeScene);
        super.getGameBoardViews().put("Tic-tac-toe", ticTacToeView);
        super.getGameScenes().put("Reversi", reversiScene);
        super.getGameBoardViews().put("Reversi", reversiView);

        getChildren().addAll(chooseGameTitle, pva, pvs, back);
    }

    /**
     * Getter for the previous scene
     *
     * @return the previous scene
     */
    @Override
    public Scene getSceneUnderneath(){
        return serverOptionsScene;
    }

    /**
     * Getter for the ServeroptionsView
     *
     * @return the view
     */
    @Override
    public ServerOptionsView getBorderPaneViewUnderneath() {
        return serverOptionsView;
    }
}
