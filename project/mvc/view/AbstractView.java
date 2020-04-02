package project.mvc.view;

import project.mvc.view.mainscreen.ConfirmBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * All GUI components that are not related to gameBoards will extend the abstractView class.
 *
 */
public abstract class AbstractView extends VBox {

    public static void closeApplication(Stage window) {
        boolean answer = ConfirmBox.display("Are you sure?", "Are you sure you want to close the application?");
        if(answer)
            window.close();
    }

    public void setNextOrPreviousScene(Button button, Stage window, Scene scene){
        button.setOnAction(e -> window.setScene(scene));
    }
}
