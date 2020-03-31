package MainScreen;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class TicTacToeView extends BorderPane {

    public TicTacToeView(Stage window) {
        Scene TicTacToeScene = new Scene(createContent(), 900, 600);
        window.setScene(TicTacToeScene);
    }

    public static Parent createContent() {
        Pane board = new Pane();

        Button button = new Button("Content");
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 200);
                tile.setTranslateY(i * 200);

                board.getChildren().add(tile);
            }
        }
        return board;
    }



    public static class Tile extends StackPane {
        public Tile() {
            Rectangle border = new Rectangle();
            border.setFill(Color.WHITE);
            border.setStroke(Color.RED);

            setAlignment(Pos.CENTER);
            getChildren().addAll(border);
        }
    }

}
