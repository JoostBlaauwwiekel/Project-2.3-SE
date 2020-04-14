package project.mvc.view.mainscreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ErrorBox {

    private Button confirm;
    private Stage window;

    public ErrorBox(){
        confirm = new Button("Okay, I understand");
        confirm.setMinWidth(100);
    }

    public void display(String title, String message) {
        window = new Stage();
        window.initStyle(StageStyle.UTILITY);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label mainMessage = new Label("Cannot connect to server. Make sure:");
        mainMessage.setFont(new Font("Arial", 24));
        mainMessage.setTextFill(Color.WHITE);

        Label titleMessage = new Label(message);
        titleMessage.setFont(new Font("Arial", 18));
        titleMessage.setTextFill(Color.WHITE);

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(50,50,50,50));

        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(mainMessage, titleMessage, confirm);

        layout.setStyle("-fx-background-color: #FBC74A;");
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public void closeWindow(){
        window.close();
    }

    public Button getConfirm(){
        return confirm;
    }
}
