package project.mvc.view.mainscreen;

import project.mvc.controller.ApplicationController;
import project.mvc.view.ApplicationView;
import project.mvc.view.ScreenView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainView extends ScreenView {

    private Scene chooseGameScene;
    private ChooseGameView chooseGameView;
    private ApplicationController applicationController;

    public MainView(Stage window, ApplicationController applicationController) {
        super(window);

        setSpacing(10);
        setPadding(new Insets(20, 20, 20, 20));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #1da1f2, #1281bb)");

        Label title = new Label("Project 2.3 SE");
        title.setTextFill(Color.WHITE);
        title.setFont(new Font("Arial", 30));

        Button chooseGame = new Button("Play");
        Button options = new Button("Options");
        Button close = new Button("Exit");

        super.getButtons().put(chooseGame.getText(), chooseGame);
        super.getButtons().put(options.getText(), options);
        super.getButtons().put(close.getText(), close);

        chooseGame.setMinWidth(100);
        options.setMinWidth(100);
        close.setMinWidth(100);

        chooseGameView = new ChooseGameView(window, applicationController);
        chooseGameScene = new Scene(chooseGameView, 900, 600);

        getChildren().addAll(title, chooseGame, options, close);
    }

    public Scene getSceneUnderneath() { return chooseGameScene; }

    public ScreenView getViewUnderneath() { return chooseGameView; }
}