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

/**
 * This is the class for the Choose Game View view.
 */
public class ChooseGameView extends ScreenView {

    private Scene chooseGameModeViewScene;
    private ChooseGameModeView chooseGameModeView;

    /**
     * This is constructor for the Choose Game View view.
     *
     * @param window the current window.
     * @param controller the MVC controller.
     */
    public ChooseGameView(Stage window, ApplicationController controller) {
        super(window);
        setSpacing(10);
        setPadding(new Insets(20,20,20,20));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #1da1f2, #1281bb)");

        Label chooseGameTitle = new Label("Choose a game to play");
        chooseGameTitle.setTextFill(Color.WHITE);
        chooseGameTitle.setFont(new Font("Arial", 30));

        Button chooseTTT = new Button("Tic Tac Toe");
        Button reversi = new Button("Reversi");
        Button back = new Button(" Go back");

        super.getButtons().put("Tic-tac-toe", chooseTTT);
        super.getButtons().put(reversi.getText(), reversi);
        super.getButtons().put(back.getText(), back);

        chooseTTT.setMinWidth(100);
        reversi.setMinWidth(100);
        back.setMinWidth(100);

        chooseGameModeView = new ChooseGameModeView(window, controller);
        chooseGameModeViewScene = new Scene(chooseGameModeView, 900, 600);

        getChildren().addAll(chooseGameTitle, chooseTTT, reversi, back);
    }

    /**
     * Getter for the previous scene
     * @return the previous scene
     */
    @Override
    public Scene getSceneUnderneath(){
        return chooseGameModeViewScene;
    }

    /**
     * Getter for the ServeroptionsView
     * @return the view
     */
    @Override
    public ScreenView getViewUnderneath() {
        return chooseGameModeView;
    }

}
