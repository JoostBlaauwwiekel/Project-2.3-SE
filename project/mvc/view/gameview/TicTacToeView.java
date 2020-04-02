package project.mvc.view.gameview;

import project.mvc.view.GameBoardView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.mvc.view.gameboard.TicTacToeBoard;

public class TicTacToeView extends GameBoardView {

    private String mode = "";

    public TicTacToeView(Stage window) {
        super(window);

        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20,20,20,20));
        sidebar.setStyle("-fx-background-color: #524D4E;");

        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(20,20,20,20));
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color: #A67D2A;");

        // Met de TicTacToeBoardLogic class moet je straks dus TicTacToeBoardLogic board = new .... kunnen doen en .setCenter(board)
        // Content (Dus hetgene in het midden)

        FlowPane centerLayout = new FlowPane();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setStyle("-fx-background-color: #524D4E;");
        centerLayout.setMaxSize(450, 450);

        Label gameMode = new Label("");
        gameMode.setTextFill(Color.WHITE);
        Label wins = new Label("Wins: 1");
        wins.setTextFill(Color.WHITE);
        Label placeholder = new Label("Hier moet bord komen");
        placeholder.setTextFill(Color.WHITE);

        if(mode.equals("pva")) {
            gameMode.setText("Player VS AI");
        } else {
            gameMode.setText("Player VS Server");
        }
        TicTacToeBoard board = new TicTacToeBoard(0);

        Button exitGame = new Button("Exit Tic Tac Toe");
        super.getGameButtons().put(exitGame.getText(), exitGame);

        sidebar.getChildren().addAll(wins, exitGame);
        topBar.getChildren().addAll(gameMode);
        centerLayout.getChildren().addAll(board.getTiles());

        setCenter(centerLayout);
        setLeft(sidebar);
        setTop(topBar);
    }

    public void setMode(String mode){
        this.mode = mode;
    }

}
