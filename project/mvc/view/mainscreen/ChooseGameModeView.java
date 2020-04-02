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
import project.mvc.view.gameview.ReversiView;
import project.mvc.view.gameview.TicTacToeView;

public class ChooseGameModeView extends AbstractScreenView {

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

        TicTacToeView ticTacToeView = new TicTacToeView(window);
        Scene ticTacToeScene = new Scene(ticTacToeView, 900, 600);

        ReversiView reversiView = new ReversiView(window);
        Scene reversiScene = new Scene(reversiView, 900, 600);

        super.getGameScenes().put("Tic Tac Toe", ticTacToeScene);
        super.getGameBoardViews().put("Tic Tac Toe", ticTacToeView);
        super.getGameScenes().put("Reversi", reversiScene);
        super.getGameBoardViews().put("Reversi", reversiView);

        getChildren().addAll(chooseGameTitle, pva, pvs, back);
    }

    public Scene getSceneUnderneath(){
        return null;
    }

    public AbstractScreenView getViewUnderneath(){
        return null;
    }
}
