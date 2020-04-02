package project.mvc.view;

import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public abstract class GameBoard extends FlowPane {

    private int gameBoardWidth;
    private int gameBoardHeight;

    private int turn;
    private int counter;
    private Button button;

    public GameBoard(int width, int height) {
        gameBoardWidth = width;
        gameBoardHeight = height;
        turn = 1;
        counter = 0;
        drawBoard();
    }

    public void drawBoard() {
        for (int i = 0; i < this.gameBoardWidth; i++) {
            for (int j = 0; j < this.gameBoardHeight; j++) {
                button = new Button("iets");
                button.setMinSize(125, 125);
                this.getChildren().add(button);
            }
        }
    }
}
