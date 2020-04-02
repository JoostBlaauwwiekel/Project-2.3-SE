package project.mvc.view.mainscreen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // Een nieuwe stage
    Stage window;
    // Twee nieuwe scenes
    Scene mainScene;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        // Set de titel van de window
        window.setTitle("StartApp screen");
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
