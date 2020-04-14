package project.mvc.view.mainscreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.mvc.controller.ApplicationController;
import project.mvc.view.ScreenBorderPaneView;

public class OptionsView extends ScreenBorderPaneView {

    private Slider slider;

    /**
     * This is the constructor for the ScreenView class, the primary stage of a scene is given as a parameter.
     *
     * @param window the stage of a particular scene.
     */
    protected OptionsView(Stage window, ApplicationController controller) {
        super(window, controller);

        setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #1da1f2, #1281bb)");

        HBox header = new HBox();
        VBox centerLayout = new VBox();

        setTop(header);
        setCenter(centerLayout);

        header.setSpacing(20);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10,10,10,10));

        centerLayout.setPadding(new Insets(10,10,10,10));
        centerLayout.setSpacing(10);

        Label title = new Label("Options");
        title.setFont(Font.font("Calibri", 22));
        title.setTextFill(Color.WHITE);
        title.setAlignment(Pos.CENTER);

        Label ipAddressLabel = new Label("IP Address");
        ipAddressLabel.setTextFill(Color.WHITE);
        ipAddressLabel.setFont(Font.font("Calibri", 18));
        TextField ipAddress = new TextField("localhost");
        ipAddress.setId("IP Address");

        Label portLabel = new Label("Port");
        portLabel.setTextFill(Color.WHITE);
        portLabel.setFont(Font.font("Calibri", 18));
        TextField port = new TextField("7789");
        port.setId("Port");

        Label usernameLabel = new Label("Username");
        usernameLabel.setTextFill(Color.WHITE);
        usernameLabel.setFont(Font.font("Calibri", 18));
        TextField username = new TextField("BITM");
        username.setId("Username");

        Label aiTimeoutLabel = new Label("AI timeout:");
        aiTimeoutLabel.setTextFill(Color.WHITE);
        aiTimeoutLabel.setFont(Font.font("Calibri", 18));
        TextField aiTimeout = new TextField("10");
        aiTimeout.setId("Timeout");

        Label aiDifficultyLevelLabel = new Label("Set AI difficulty level:");
        aiDifficultyLevelLabel.setTextFill(Color.WHITE);
        aiDifficultyLevelLabel.setFont(Font.font("Calibri", 18));

        Label aiDifficultyExplained = new Label(
                "<-- easy                                                                                     " +
                "medium                                                                                      " +
                "hard -->");
        aiDifficultyExplained.setTextFill(Color.WHITE);
        aiDifficultyExplained.setFont(Font.font("Calibri", 18));

        slider = new Slider(0, 2, 2);
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);

        Button back = new Button("Go back");
        back.setMaxWidth(Double.MAX_VALUE);

        Button changeDetails = new Button("Change settings");
        changeDetails.setMaxWidth(Double.MAX_VALUE);

        super.getButtons().put(changeDetails.getText(), changeDetails);
        super.getButtons().put(back.getText(), back);

        super.getTextFields().put(ipAddress.getId(), ipAddress);
        super.getTextFields().put(port.getId(), port);
        super.getTextFields().put(username.getId(), username);
        super.getTextFields().put(aiTimeout.getId(), aiTimeout);

        header.getChildren().add(title);
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.getChildren().addAll(ipAddressLabel, ipAddress, portLabel, port, usernameLabel, username, aiTimeoutLabel, aiTimeout, aiDifficultyLevelLabel, aiDifficultyExplained, slider, new Label(), changeDetails, back);
    }

    @Override
    public Slider getSlider(){
        return slider;
    }
}