package project.mvc.view.mainscreen;

import project.mvc.controller.ApplicationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.mvc.view.ScreenBorderPaneView;
import project.mvc.view.ScreenView;

/**
 * This is the MainView class, it serves as the start up screen.
 */
public class MainView extends ScreenView {

    private Scene chooseGameScene;
    private ChooseGameView chooseGameView;
    private Scene optionsScene;
    private ScreenBorderPaneView optionsView;

    /**
     * The MainView of the application
     *
     * @param window        The Stage of the view
     * @param controller    The controller of the MVC
     */
    public MainView(Stage window, ApplicationController controller) {
        super(window);

        setSpacing(10);
        setPadding(new Insets(20, 20, 20, 20));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #1da1f2, #1281bb)");

        Label title = new Label("Project 2.3 SE");
        title.setTextFill(Color.WHITE);
        title.setFont(new Font("Arial", 25));

        Label title2 = new Label("Universal Game Launcher");
        title2.setTextFill(Color.WHITE);
        title2.setFont(new Font("Arial", 30));

        Button chooseGame = new Button("Play");
        Button options = new Button("Options");
        Button close = new Button("Exit");

        super.getButtons().put(chooseGame.getText(), chooseGame);
        super.getButtons().put(options.getText(), options);
        super.getButtons().put(close.getText(), close);

        chooseGame.setMinWidth(100);
        options.setMinWidth(100);
        close.setMinWidth(100);

        chooseGameView = new ChooseGameView(window, controller);
        chooseGameScene = new Scene(chooseGameView, 900, 600);

        optionsView = new OptionsView(window, controller);
        optionsScene = new Scene(optionsView, 900, 600);

        getChildren().addAll(title2, title, new Label(), chooseGame, options, close);
    }

    /**
     * Getter for the previous scene
     *
     * @return the previous scene
     */
    @Override
    public Scene getSceneUnderneath() { return chooseGameScene; }

    /**
     * Getter for the previous View
     *
     * @return the view
     */
    @Override
    public ScreenView getViewUnderneath() { return chooseGameView; }

    /**
     * Getter for the next Scene
     *
     * @return the Scene
     */
    @Override
    public Scene getSceneOverhead() { return optionsScene; }

    /**
     * Method that returns the ScreenBorderPaneView
     *
     * @return  The ScreenBorderPaneView
     */
    @Override
    public ScreenBorderPaneView getBorderPaneViewUnderneath() {
        return optionsView;
    }


}