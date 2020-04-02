package project.mvc.view.mainscreen;

import project.mvc.view.ScreenView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ChooseGameView extends ScreenView {

    private Scene chooseGameModeViewScene;
    private ChooseGameModeView chooseGameModeView;

    public ChooseGameView(Stage window) {
        super(window);
        setSpacing(10);
        setPadding(new Insets(20,20,20,20));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #1da1f2;");

        Label chooseGameTitle = new Label("Choose a game to play");
        chooseGameTitle.setTextFill(Color.WHITE);
        chooseGameTitle.setFont(new Font("Arial", 30));

        Button chooseTTT = new Button("Tic Tac Toe");
        Button reversi = new Button("Reversi");
        Button back = new Button("Previous scene");

        super.getButtons().put(chooseTTT.getText(), chooseTTT);
        super.getButtons().put(reversi.getText(), reversi);
        super.getButtons().put(back.getText(), back);

        chooseTTT.setMinWidth(100);
        reversi.setMinWidth(100);
        back.setMinWidth(100);

        chooseGameModeView = new ChooseGameModeView(window);
        chooseGameModeViewScene = new Scene(chooseGameModeView, 900, 600);

        getChildren().addAll(chooseGameTitle, chooseTTT, reversi, back);
    }

    public Scene getSceneUnderneath(){
        return chooseGameModeViewScene;
    }

    public ScreenView getViewUnderneath() {
        return chooseGameModeView;
    }

}
