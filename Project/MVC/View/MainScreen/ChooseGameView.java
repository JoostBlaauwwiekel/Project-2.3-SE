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

public class ChooseGameView extends VBox {

    public ChooseGameView(Stage window) {
        setSpacing(10);
        setPadding(new Insets(20,20,20,20));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #1da1f2;");

        Label chooseGameTitle = new Label("Choose a game to play");
        chooseGameTitle.setTextFill(Color.WHITE);
        chooseGameTitle.setFont(new Font("Arial", 30));
        Button chooseTTT = new Button("Tic Tac Toe");
        Button reversi = new Button("Reversi");
        Button back = new Button("Go back");

        chooseTTT.setMinWidth(100);
        reversi.setMinWidth(100);
        back.setMinWidth(100);

        ChooseGameModeView chooseGameModeView = new ChooseGameModeView(window);
        Scene chooseGameModeViewScene = new Scene(chooseGameModeView, 900, 600);

        chooseTTT.setOnAction(e -> {
            window.setScene(chooseGameModeViewScene);
            window.setTitle("Tic Tac Toe");
        });

//        MainView mainView = new MainView(window);
//        Scene scene = new Scene(mainView);

        back.setOnAction(e -> {
//            window.setScene(scene);
            System.out.println("Moet terug naar MainView");
        });

        getChildren().addAll(chooseGameTitle, chooseTTT, reversi, back);
    }
}
