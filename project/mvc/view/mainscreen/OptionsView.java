package project.mvc.view.mainscreen;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import project.mvc.view.ScreenBorderPaneView;

public class OptionsView extends ScreenBorderPaneView {
    /**
     * This is the constructor for the ScreenView class, the primary stage of a scene is given as a parameter.
     *
     * @param window the stage of a particular scene.
     */
    protected OptionsView(Stage window) {
        super(window);

        HBox layout = new HBox();

        setTop(layout);

        TextField ip_address = new TextField();
        Label text = new Label("Ff testen");

        layout.getChildren().addAll(text, ip_address);


    }
}
