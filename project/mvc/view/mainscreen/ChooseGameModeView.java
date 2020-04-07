package project.mvc.view.mainscreen;

import project.mvc.controller.ApplicationController;
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

public class ChooseGameModeView extends ScreenView {

    public ChooseGameModeView(Stage window, ApplicationController applicationController) {
        super(window);
        setSpacing(10);
        setPadding(new Insets(20,20,20,20));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #f2b353, #c39540)");

        Label chooseGameTitle = new Label("Choose a mode");
        chooseGameTitle.setTextFill(Color.WHITE);
        chooseGameTitle.setFont(new Font("Arial", 30));
        Button pva = new Button("Player vs AI");
        Button pvs = new Button("Player vs Server");
        Button back = new Button("Go back");

        super.getButtons().put(pva.getText(), pva);
        super.getButtons().put(pvs.getText(), pvs);
        super.getButtons().put(back.getText(), back);

        pva.setMinWidth(150);
        pvs.setMinWidth(150);
        back.setMinWidth(150);

        TicTacToeView ticTacToeView = new TicTacToeView(window, 500, applicationController);
        Scene ticTacToeScene = new Scene(ticTacToeView, 500, 700);

        ReversiView reversiView = new ReversiView(window, 500, applicationController);
        Scene reversiScene = new Scene(reversiView, 500, 700);

        super.getGameScenes().put("Tic Tac Toe", ticTacToeScene);
        super.getGameBoardViews().put("Tic Tac Toe", ticTacToeView);
        super.getGameScenes().put("Reversi", reversiScene);
        super.getGameBoardViews().put("Reversi", reversiView);

        getChildren().addAll(chooseGameTitle, pva, pvs, back);
    }

    public Scene getSceneUnderneath(){
        return null;
    }

    public ScreenView getViewUnderneath(){
        return null;
    }
}
