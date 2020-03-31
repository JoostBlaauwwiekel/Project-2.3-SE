package Project.MVC.View.MainScreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainView extends VBox {
    public MainView(Stage window) {
        setSpacing(10);
        setPadding(new Insets(20,20,20,20));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #1da1f2;");

        Label title = new Label("Project 2.3 SE");
        title.setTextFill(Color.WHITE);
        title.setFont(new Font("Arial", 30));

        Button chooseGame = new Button("Play");
        Button options = new Button("Options");
        Button close = new Button("Exit");

        chooseGame.setMinWidth(100);
        options.setMinWidth(100);
        close.setMinWidth(100);

        // Wanneer men een game kiest veranderd de scene naar een nieuwe scene
        ChooseGameView chooseGameView = new ChooseGameView(window);
        Scene chooseGameScene = new Scene(chooseGameView, 900, 600);

        chooseGame.setOnAction(e -> {
            window.setScene(chooseGameScene);
            window.setTitle("Choose a game");
        });

        close.setOnAction(e -> {
            closeApplication(window);
        });

        getChildren().addAll(title, chooseGame, options, close);
    }

    public static void closeApplication(Stage window)
    {
        Boolean answer = ConfirmBox.display("Are you sure?", "Are you sure you want to close the application?");

        if(answer) {
            window.close();
        }
    }
}
