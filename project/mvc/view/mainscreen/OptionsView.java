package project.mvc.view.mainscreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.mvc.controller.ApplicationController;
import project.mvc.view.ScreenBorderPaneView;

public class OptionsView extends ScreenBorderPaneView {
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
        title.setTextFill(Color.WHITE);
        title.setAlignment(Pos.CENTER);
        Label ip_address_label = new Label("IP Address");
        ip_address_label.setTextFill(Color.WHITE);
        TextField ip_address = new TextField();
        ip_address.setId("IP Address");
        Label port_label = new Label("Port");
        port_label.setTextFill(Color.WHITE);
        TextField port = new TextField();
        port.setId("Port");
        Label username_label = new Label("Username");
        username_label.setTextFill(Color.WHITE);
        TextField username = new TextField();
        username.setId("Username");

        Button back = new Button("Go back");
        back.setMaxWidth(Double.MAX_VALUE);

        Button change_details = new Button("Change details");
        change_details.setMaxWidth(Double.MAX_VALUE);

        super.getButtons().put(change_details.getText(), change_details);
        super.getButtons().put(back.getText(), back);
        super.getTextFields().put(ip_address.getId(), ip_address);
        super.getTextFields().put(port.getId(), port);
        super.getTextFields().put(username.getId(), username);

        header.getChildren().add(title);
        centerLayout.getChildren().addAll(ip_address_label, ip_address, port_label, port, username_label, username, change_details, back);
    }
}