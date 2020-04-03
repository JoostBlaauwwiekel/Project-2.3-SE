package project.mvc.view.mainscreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label titleMessage = new Label(message);
        Button confirm = new Button("Yes, go on");
        Button deny = new Button("Cancel");

        confirm.setMinWidth(100);
        deny.setMinWidth(100);
        deny.setStyle("-fx-background-color: #EB4D4E; -fx-font-weight: bold;");
        deny.setTextFill(Color.WHITE);

        confirm.setOnAction(e -> {
            answer = true;
            window.close();
        });

        deny.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(20);
        layout.setStyle("-fx-background-color: #FFFFFF;");
        layout.setPadding(new Insets(50,50,50,50));
        layout.getChildren().addAll(titleMessage, confirm, deny);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);

        window.showAndWait();

        return answer;
    }
}
