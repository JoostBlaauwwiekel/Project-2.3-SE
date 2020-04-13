import javafx.application.Application;
import javafx.stage.Stage;
import project.mvc.controller.ApplicationController;
import project.mvc.model.ApplicationModel;

public class StartApp2 extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationModel applicationModel = new ApplicationModel();
        ApplicationController applicationController = new ApplicationController(applicationModel);
        applicationController.getApplicationView().initializeApplicationScreens(stage);
    }

}
