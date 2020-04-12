package project.mvc.view.mainscreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project.mvc.controller.ApplicationController;
import project.mvc.view.ScreenBorderPaneView;

public class ServerOptionsView extends ScreenBorderPaneView {
    /**
     * This is the constructor for the ScreenView class, the primary stage of a scene is given as a parameter.
     *
     * @param window the stage of a particular scene.
     */
    public ListView<String> playerList;
    public ListView<String> challengeList;

    private Label actualAction;

    public ServerOptionsView(Stage window, ApplicationController controller) {
        super(window, controller);
        setPadding(new Insets(20,20,20,20));
        setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #34cb8a, #50a480)");

        VBox leftMenu = new VBox();
        VBox rightMenu = new VBox();
        VBox topHeader = new VBox();
        VBox centerMenu = new VBox();

        centerMenu.setAlignment(Pos.CENTER);
        centerMenu.setSpacing(15);

        leftMenu.setAlignment(Pos.CENTER);
        leftMenu.setSpacing(10);

        rightMenu.setAlignment(Pos.CENTER);
        rightMenu.setSpacing(10);

        setTop(topHeader);
        setLeft(leftMenu);
        setRight(rightMenu);
        setCenter(centerMenu);

        Label title = new Label("Server Lobby ");
        title.setFont(Font.font("Cambria", 30));
        title.setTextFill(Color.WHITE);

        Text eventText =new Text("Current action:");
        eventText.setStyle("-fx-font-weight: bold");
        eventText.setFont(Font.font("Cambria", 20));

        actualAction = new Label("Idle...");
        actualAction.setFont(Font.font("Cambria", 18));
        actualAction.setPadding(new Insets(25));

        super.setEventLabel(actualAction);

        topHeader.setAlignment(Pos.CENTER);
        topHeader.getChildren().add(title);

        Label playerListLabel = new Label("Player list");
        playerListLabel.setFont(Font.font("Cambria", 20));

        playerList = new ListView<>();
        playerList.setMaxWidth(200);
        playerList.setMaxHeight(520);

        Label challengeListLabel = new Label("Challenge list");
        challengeListLabel.setFont(Font.font("Cambria", 20));

        challengeList = new ListView<>();
        challengeList.setMaxWidth(200);
        challengeList.setMaxHeight(520);

        Button joinTournamentLobby = new Button("Join Tournament Lobby");
        Button challengeButton = new Button("Challenge!");
        Button acceptChallengeButton = new Button("Accept challenge");
        Button refreshButton = new Button("Refresh list");
        Button subscribeButton = new Button("Subscribe");
        Button back = new Button("Go back");

        super.getButtons().put(joinTournamentLobby.getText(), joinTournamentLobby);
        super.getButtons().put(challengeButton.getText(), challengeButton);
        super.getButtons().put(acceptChallengeButton.getText(), acceptChallengeButton);
        super.getButtons().put(refreshButton.getText(), refreshButton);
        super.getButtons().put(subscribeButton.getText(), subscribeButton);
        super.getButtons().put(back.getText(), back);

        super.getButtons().forEach((k,v) -> v.setMinWidth(150));

        leftMenu.getChildren().addAll(playerListLabel, playerList, challengeButton);
        rightMenu.getChildren().addAll(challengeListLabel, challengeList, acceptChallengeButton);
        centerMenu.getChildren().addAll(eventText, actualAction, joinTournamentLobby, refreshButton, subscribeButton, back);

        super.getListViews().put("PlayerList", playerList);
        super.getListViews().put("ChallengeList", challengeList);
    }

    public ListView<String> getPlayerListView() { return playerList; }

    public ListView<String> getChallengeList() { return challengeList; }

}
