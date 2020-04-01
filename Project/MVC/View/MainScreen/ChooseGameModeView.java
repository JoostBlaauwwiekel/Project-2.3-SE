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

public class ChooseGameModeView extends AbstractView {

    private Button pva;
    private Button pvs;
    private Button back;

    private TicTacToeView ticTacToeView;
    private Scene ticTacToeScene;

    public ChooseGameModeView(Stage window) {
        setSpacing(10);
        setPadding(new Insets(20,20,20,20));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #1da1f2;");

        Label chooseGameTitle = new Label("Choose a mode");
        chooseGameTitle.setTextFill(Color.WHITE);
        chooseGameTitle.setFont(new Font("Arial", 30));
        pva = new Button("Player vs AI");
        pvs = new Button("Player vs Server");
        back = new Button("Go back");

        pva.setMinWidth(150);
        pvs.setMinWidth(150);
        back.setMinWidth(150);

        ticTacToeView = new TicTacToeView(window, "Player vs Server");
        ticTacToeScene = new Scene(ticTacToeView, 900, 600);

        pva.setOnAction(e -> {
            window.setScene(ticTacToeScene);
            window.setTitle("Tic Tac Toe");
        });

        back.setOnAction(e -> {
            System.out.println("Moet terug naar ChooseGameView");
        });

        getChildren().addAll(chooseGameTitle, pva, pvs, back);
    }

    public Scene getTicTacToeScene(){
        return ticTacToeScene;
    }

    public TicTacToeView getTicTacToeView1(){
        return ticTacToeView;
    }

    public Button getPva() {
        return pva;
    }

    public Button getPvs() {
        return pvs;
    }

    public Button getBack() {
        return back;
    }
}
