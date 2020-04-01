package Project.MVC.View.MainScreen;
import GameBoard.TicTacToe;
import Project.GameModules.TicTacToeGame.TicTacToeBoard;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class TicTacToeView extends BorderPane {

    public TicTacToeView(Stage window, String mode) {

        // Bovenbalk
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20,20,20,20));
        sidebar.setStyle("-fx-background-color: #524D4E;");

        // Zijbalk
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(20,20,20,20));
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color: #A67D2A;");

        // Met de TicTacToeBoard class moet je straks dus TicTacToeBoard board = new .... kunnen doen en .setCenter(board)
        // Content (Dus hetgene in het midden)
        FlowPane centerLayout = new FlowPane();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setStyle("-fx-background-color: #524D4E;");
        centerLayout.setMaxSize(300, 300);

        Label gameMode = new Label("lmao");
        gameMode.setTextFill(Color.WHITE);
        Label wins = new Label("Wins: 1");
        wins.setTextFill(Color.WHITE);
        Label placeholder = new Label("Hier moet bord komen");
        placeholder.setTextFill(Color.WHITE);

        if(mode == "pva") {
            gameMode.setText("Player VS AI");
        } else {
            gameMode.setText("Player VS Server");
        }

        //Maak tictactoe board
        TicTacToeBoard board = new TicTacToeBoard();
        //Maak array met buttons
        Button[] tiles = new Button[9];
        //For loop geeft elke button eigenschappen
        for(int i = 0; i < 9; i++){
            tiles[i] = new Button("");
            tiles[i].setMinSize(100, 100);
            Button btn = tiles[i];
            //Zorgt voor text op de button nadat deze geklikt is
            btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    btn.setText("X");
                }
            });
        }



        Button exitGame = new Button("Exit game");

        exitGame.setOnAction(e -> {
            System.out.println("Spel moet stoppen en terug naar ChooseGameView");
        });

        sidebar.getChildren().addAll(wins, exitGame);
        topBar.getChildren().addAll(gameMode);
        centerLayout.getChildren().addAll(tiles);

        setCenter(centerLayout);
        setLeft(sidebar);
        setTop(topBar);
    }
}
