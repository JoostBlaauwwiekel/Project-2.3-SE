package project.mvc.view.mainscreen;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.mvc.model.ApplicationModel;
import project.mvc.view.ScreenBorderPaneView;
import project.mvc.view.ScreenView;

import java.util.Iterator;
import java.util.List;

public class ServerOptionsView extends ScreenBorderPaneView {
    /**
     * This is the constructor for the ScreenView class, the primary stage of a scene is given as a parameter.
     *
     * @param window the stage of a particular scene.
     */
    public ListView<String> playerList;
    public ListView<String> challengeList;


    protected ServerOptionsView(Stage window) {
        super(window);
        setPadding(new Insets(20,20,20,20));
        setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #34cb8a, #50a480)");

        VBox leftMenu = new VBox();
        VBox rightMenu = new VBox();
        VBox topHeader = new VBox();
        VBox centerMenu = new VBox();

        centerMenu.setAlignment(Pos.CENTER);
        centerMenu.setSpacing(10);
        setTop(topHeader);
        setLeft(leftMenu);
        setRight(rightMenu);
        setCenter(centerMenu);

        Label title = new Label("Titel of this shit");
        title.setTextFill(Color.WHITE);
        title.setFont(new Font("Arial", 30));

        topHeader.setAlignment(Pos.CENTER);
        topHeader.getChildren().add(title);

        Label playerListLabel = new Label("Player list");

        playerList = new ListView<String>();
        playerList.setMaxWidth(200);
        playerList.setMaxHeight(400);
        playerList.getItems().add("Speler 1");
        playerList.getItems().add("Speler 2");

        Label challengeListLabel = new Label("Challenge list");

        challengeList = new ListView<String>();
        challengeList.setMaxWidth(200);
        challengeList.setMaxHeight(400);
        challengeList.getItems().add("je moeder");

        Button challengeButton = new Button("Challenge!");
        Button acceptChallengeButton = new Button("Accept challenge");
        Button refreshButton = new Button("Refresh list");
        Button subscribeButton = new Button("Subscribe");
        Button back = new Button("Go back");

        super.getButtons().put(challengeButton.getText(), challengeButton);
        super.getButtons().put(acceptChallengeButton.getText(), acceptChallengeButton);
        super.getButtons().put(refreshButton.getText(), refreshButton);
        super.getButtons().put(subscribeButton.getText(), subscribeButton);
        super.getButtons().put(back.getText(), back);

        super.getButtons().forEach((k,v) -> v.setMinWidth(150));

        leftMenu.getChildren().addAll(playerListLabel, playerList);
        rightMenu.getChildren().addAll(challengeListLabel, challengeList);
        centerMenu.getChildren().addAll(challengeButton, acceptChallengeButton, refreshButton, subscribeButton, back);

        super.getListViews().put("PlayerList", playerList);
        super.getListViews().put("ChallengeList", challengeList);
    }

    public ListView<String> getPlayerListView() { return playerList; }

    public ListView<String> getChallengeList() { return challengeList; }

}
