package project.mvc.view.mainscreen;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import project.mvc.controller.ApplicationController;
import project.mvc.view.ScreenBorderPaneView;

public class OptionsView extends ScreenBorderPaneView {
    /**
     * This is the constructor for the ScreenView class, the primary stage of a scene is given as a parameter.
     *
     * @param window the stage of a particular scene.
     */
    public OptionsView(Stage window, ApplicationController controller) {
        super(window, controller);

        HBox layout = new HBox();
        setTop(layout);
        TextField ip_address = new TextField();
        Label text = new Label("Ff testen");

        layout.getChildren().addAll(text, ip_address);
    }
}
