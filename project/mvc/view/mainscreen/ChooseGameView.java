package project.mvc.view.mainscreen;

import project.mvc.view.AbstractView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ChooseGameView extends AbstractView {

    private Button chooseTTT;
    private Button reversi;
    private Button back;

    private Scene chooseGameModeViewScene;
    private AbstractView chooseGameModeView;

    public ChooseGameView(Stage window) {
        setSpacing(10);
        setPadding(new Insets(20,20,20,20));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #1da1f2;");

        Label chooseGameTitle = new Label("Choose a game to play");
        chooseGameTitle.setTextFill(Color.WHITE);
        chooseGameTitle.setFont(new Font("Arial", 30));

        chooseTTT = new Button("Tic Tac Toe");
        reversi = new Button("Reversi");
        back = new Button("Go back");

        chooseTTT.setMinWidth(100);
        reversi.setMinWidth(100);
        back.setMinWidth(100);

        chooseGameModeView = new ChooseGameModeView(window);
        chooseGameModeViewScene = new Scene(chooseGameModeView, 900, 600);

        chooseTTT.setOnAction(e -> {
            window.setScene(chooseGameModeViewScene);
            window.setTitle("Tic Tac Toe");
        });

        getChildren().addAll(chooseGameTitle, chooseTTT, reversi, back);
    }

    public Scene getChooseGameModeViewScene(){
        return chooseGameModeViewScene;
    }

    public AbstractView getChooseGameModeView(){
        return chooseGameModeView;
    }

    public Button getChooseTTT() {
        return chooseTTT;
    }

    public Button getReversi() {
        return reversi;
    }

    public Button getBack() {
        return back;
    }
}
