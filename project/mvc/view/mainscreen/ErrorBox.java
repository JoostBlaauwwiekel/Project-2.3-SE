package project.mvc.view.mainscreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorBox {

    private Button confirm;
    private Stage window;

    /**
     * Constructor, it shows a error message when the application
     * failed to join a server
     */
    public ErrorBox(){
        confirm = new Button("Okay, I understand");
        confirm.setMinWidth(100);
    }

    /**
     * Displays the message
     * @param title     The title of the Message
     * @param message   The message itself
     */
    public void display(String title, String message) {
        window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label titleMessage = new Label(message);

        VBox layout = new VBox(20);
        layout.setStyle("-fx-background-color: #FFFFFF;");
        layout.setPadding(new Insets(50,50,50,50));

        layout.getChildren().addAll(titleMessage, confirm);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);

        window.showAndWait();
    }

    /**
     * Method to close the windows
     */
    public void closeWindow(){
        window.close();
    }

    /**
     * Method that returns the confirm button
     * @return  The button
     */
    public Button getConfirm(){
        return confirm;
    }
}
