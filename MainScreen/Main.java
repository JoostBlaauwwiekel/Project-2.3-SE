package MainScreen;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

    // Een nieuwe stage
    Stage window;
    // Twee nieuwe scenes
    Scene mainScene, chooseGameScene;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        // Set de titel van de window
        window.setTitle("Main screen");
        // Wanneer er op het kruisje word gedrukt. Laat dan de closeApplication() methode
        window.setOnCloseRequest(e -> {
            // Gebruiker wil sluiten maar wij doen dat niet. Is hetzelfde e.g als PreventDefault
            e.consume();
            closeApplication();
        });

        // Nieuwe vbox layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20,20,20,20));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: #1da1f2;");

        Label title = new Label("Project 2.3 SE");
        title.setTextFill(Color.WHITE);
        title.setFont(new Font("Arial", 30));

        Button chooseGame = new Button("Play");
        Button options = new Button("Options");
        Button close = new Button("Exit");

        chooseGame.setMinWidth(100);
        options.setMinWidth(100);
        close.setMinWidth(100);

        // Wanneer men een game kiest veranderd de scene naar een nieuwe scene
        chooseGame.setOnAction(e -> {
            window.setScene(chooseGameScene);
            window.setTitle("Choose a game");
        });

        close.setOnAction(e -> {
            closeApplication();
        });

        mainLayout.getChildren().addAll(title, chooseGame, options, close);

        VBox chooseGameLayout = new VBox(10);
        chooseGameLayout.setPadding(new Insets(20,20,20,20));
        chooseGameLayout.setAlignment(Pos.CENTER);
        chooseGameLayout.setStyle("-fx-background-color: #1da1f2;");

        Label chooseGameTitle = new Label("Choose a game to play");
        chooseGameTitle.setTextFill(Color.WHITE);
        chooseGameTitle.setFont(new Font("Arial", 30));
        Button chooseTTT = new Button("Tic Tac Toe");
        Button reversi = new Button("Reversi");
        Button back = new Button("Go back");

        back.setOnAction(e -> {
            window.setScene(mainScene);
            window.setTitle("Main screen");
        });

        chooseTTT.setMinWidth(100);
        reversi.setMinWidth(100);
        back.setMinWidth(100);

        chooseGameLayout.getChildren().addAll(chooseGameTitle, chooseTTT, reversi, back);

        chooseGameScene = new Scene(chooseGameLayout, 900,600);

        mainScene = new Scene(mainLayout, 900, 600);
        window.setScene(mainScene);
        window.show();
    }

    /**
     *  Als men op deny drukt return hij false, als men op accept druk return hij true
     */
    private void closeApplication()
    {
        Boolean answer = ConfirmBox.display("Are you sure?", "Are you sure you want to close the application?");

        if(answer) {
            window.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
