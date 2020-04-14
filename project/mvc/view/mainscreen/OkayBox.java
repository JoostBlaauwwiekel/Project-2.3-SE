package project.mvc.view.mainscreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OkayBox {

    static boolean answer;

    public static boolean display(String title, String message) {
        Stage window = new Stage();

        window.initStyle(StageStyle.UTILITY);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label titleMessage = new Label(message);
        Button okay = new Button("Okay");

        okay.setMinWidth(100);

        okay.setOnAction(e -> {
            window.close();
        });

        VBox layout = new VBox(20);
        layout.setStyle("-fx-background-color: #FFFFFF;");
        layout.setPadding(new Insets(50,50,50,50));
        layout.getChildren().addAll(titleMessage, new Label(), okay);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);

        window.showAndWait();

        return answer;
    }
}
