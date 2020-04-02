package project.mvc.view.mainscreen;

import project.mvc.view.AbstractScreenView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.mvc.view.gamescreen.TicTacToeView;

public class ChooseGameModeView extends AbstractScreenView {

    private TicTacToeView ticTacToeView;
    private Scene ticTacToeScene;

    public ChooseGameModeView(Stage window) {
        super(window);
        setSpacing(10);
        setPadding(new Insets(20,20,20,20));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #1da1f2;");

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

        ticTacToeView = new TicTacToeView(window, "Player vs Server");
        ticTacToeScene = new Scene(ticTacToeView, 900, 600);

        //TODO add reversi scene to the gameScenes hashmap and add reversiView to the gameViews hashmap!
        super.getGameScenes().put("Tic Tac Toe", ticTacToeScene);
        super.getGameBoardViews().put("Tic Tac Toe", ticTacToeView);

        getChildren().addAll(chooseGameTitle, pva, pvs, back);
    }

    public Scene getSceneUnderneath(){
        return null;
    }

    public AbstractScreenView getViewUnderneath(){
        return null;
    }
}
