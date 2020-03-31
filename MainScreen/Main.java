package MainScreen;

import Project.TicTacToe_Joost.TicTacToe;
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
    Scene mainScene;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        // Set de titel van de window
        window.setTitle("Main screen");
        // Wanneer er op het kruisje word gedrukt. Laat dan de closeApplication() methode
        MainView mainView = new MainView(window);

        window.setOnCloseRequest(e -> {
            // Gebruiker wil sluiten maar wij doen dat niet. Is hetzelfde e.g als PreventDefault
            e.consume();
            mainView.closeApplication(window);
        });

        mainScene = new Scene(mainView, 900, 600);
        window.setScene(mainScene);
        window.show();
    }

    /**
     *  Als men op deny drukt return hij false, als men op accept druk return hij true
     */


    public static void main(String[] args) {
        launch(args);
    }
}
