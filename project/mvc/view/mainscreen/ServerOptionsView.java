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
import javafx.stage.Stage;
import project.mvc.model.ApplicationModel;
import project.mvc.view.ScreenView;

import java.util.List;

public class ServerOptionsView extends ScreenView {
    /**
     * This is the constructor for the ScreenView class, the primary stage of a scene is given as a parameter.
     *
     * @param window the stage of a particular scene.
     */
    public ListView<String> playerList;
    public ListView<String> challengeList;


    protected ServerOptionsView(Stage window,  ApplicationModel model) {
        super(window);
        setPadding(new Insets(20,20,20,20));
        setSpacing(10);
        setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #34cb8a, #50a480)");

        Label playerListLabel = new Label("Player list");

        playerList = new ListView<String>();
        playerList.getItems().add("Speler 1");
        playerList.getItems().add("Speler 2");

        Label challengeListLabel = new Label("Challenge list");

        challengeList = new ListView<String>();
        challengeList.getItems().add("je moeder");

        Button challengeButton = new Button("Challenge!");
        Button refreshButton = new Button("Refresh list");
        Button subscribeButton = new Button("Subscribe");
        Button back = new Button("Go back");

        challengeButton.setAlignment(Pos.TOP_LEFT);
        refreshButton.setAlignment(Pos.TOP_RIGHT);

        super.getButtons().put(challengeButton.getText(), challengeButton);
        super.getButtons().put(refreshButton.getText(), refreshButton);
        super.getButtons().put(subscribeButton.getText(), subscribeButton);
        super.getButtons().put(back.getText(), back);

        super.getListViews().put("PlayerList", playerList);
        super.getListViews().put("ChallengeList", challengeList);

        getChildren().addAll(playerListLabel, playerList, challengeListLabel, challengeList,  challengeButton, refreshButton, subscribeButton, back);
    }

    @Override
    public Scene getSceneUnderneath() {
        return null;
    }

    @Override
    public ScreenView getViewUnderneath() {
        return null;
    }

    public ListView<String> getPlayerListView() { return playerList; }

    public ListView<String> getChallengeList() { return challengeList; }

}
