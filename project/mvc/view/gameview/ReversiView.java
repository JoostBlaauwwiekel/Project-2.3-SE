package project.mvc.view.gameview;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.mvc.view.GameBoardView;

public class ReversiView extends GameBoardView {

    private String mode = "";

    public ReversiView(Stage window) {
        super(window);

        // Bovenbalk
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20,20,20,20));
        sidebar.setStyle("-fx-background-color: #524D4E;");

        // Zijbalk
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(20,20,20,20));
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color: #000000;");

        // Met de TicTacToeBoard class moet je straks dus TicTacToeBoard board = new .... kunnen doen en .setCenter(board)
        // Content (Dus hetgene in het midden)

        FlowPane centerLayout = new FlowPane();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setStyle("-fx-background-color: #3bbe3b;");
//        centerLayout.setMaxSize(450, 450);
//        centerLayout.setMaxWidth(450);

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

        //GameBoard board = new GameBoard(8, 8, centerLayout);

        //Als op Exit game word geklikt gaat deze terug naar ChooseGameView
        Button exitGame = new Button("Exit reversi game");
        super.getGameButtons().put(exitGame.getText(), exitGame);


        sidebar.getChildren().addAll(wins, exitGame);
        topBar.getChildren().addAll(gameMode);
//        centerLayout.getChildren().addAll(board.drawBoard());
        setCenter(centerLayout);
        setLeft(sidebar);
        setTop(topBar);
    }

    public void setMode(String mode){
        this.mode = mode;
    }
}