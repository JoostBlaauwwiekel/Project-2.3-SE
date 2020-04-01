package Project.MVC.View;

import Project.MVC.Controller.ApplicationController;
import Project.MVC.Model.ApplicationModel;
import Project.MVC.View.MainScreen.MainView;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * In the ApplicationView class all views be accessible. The primaryStage is set up and the necessary set-on-action
 * commands will be set.
 *
 */
public class ApplicationView {

    private ApplicationController applicationController;
    private ApplicationModel applicationModel;

    private Stage primaryStage;

    private Scene mainScene;
    private Scene chooseGameScene;

    private MainView mainView;


    public ApplicationView(ApplicationController applicationController, ApplicationModel applicationModel){
        this.applicationController = applicationController;
        this.applicationModel = applicationModel;
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void initializeMainScreen(){
        primaryStage.setTitle("StartApp screen");
        mainView = new MainView(primaryStage);

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            mainView.closeApplication(primaryStage);
        });

        mainScene = new Scene(mainView, 900, 600);


        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
