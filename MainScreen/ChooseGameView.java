package MainScreen;

import TicTacToe_Joost.TicTacToe;
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

        TicTacToeView ticTacToeView = new TicTacToeView();
        Scene TicTacToeScene = new Scene(ticTacToeView, 900, 600);

        chooseTTT.setOnAction(e -> {
            window.setScene(TicTacToeScene);
            window.setTitle("Tic Tac Toe");
        });

        back.setOnAction(e -> {
            System.out.println("Moet terug naar MainView");
        });

        getChildren().addAll(chooseGameTitle, chooseTTT, reversi, back);
    }
}
