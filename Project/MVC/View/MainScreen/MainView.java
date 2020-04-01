package Project.MVC.View.MainScreen;

import Project.MVC.View.AbstractView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainView extends AbstractView {

    private Button chooseGame;
    private Button options;
    private Button close;

    private Scene chooseGameScene;
    private AbstractView chooseGameView;

    public MainView(Stage window) {
        setSpacing(10);
        setPadding(new Insets(20,20,20,20));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #1da1f2;");

        Label title = new Label("Project 2.3 SE");
        title.setTextFill(Color.WHITE);
        title.setFont(new Font("Arial", 30));

        chooseGame = new Button("Play");
        options = new Button("Options");
        close = new Button("Exit");

        chooseGame.setMinWidth(100);
        options.setMinWidth(100);
        close.setMinWidth(100);

        chooseGameView = new ChooseGameView(window);
        chooseGameScene = new Scene(chooseGameView, 900, 600);

        chooseGame.setOnAction(e -> {
            window.setScene(chooseGameScene);
            window.setTitle("Choose a game");
        });

        close.setOnAction(e -> {
            closeApplication(window);
        });

        getChildren().addAll(title, chooseGame, options, close);
    }

    public Scene getChooseGameScene(){
        return chooseGameScene;
    }

    public AbstractView getChooseGameView(){
        return chooseGameView;
    }

    public Button getChooseGame() {
        return chooseGame;
    }

    public Button getOptions() {
        return options;
    }

    public Button getClose() {
        return close;
    }
}
