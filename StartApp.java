import project.mvc.controller.ApplicationController;
import project.mvc.model.ApplicationModel;
import project.mvc.view.mainscreen.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartApp extends Application {

    private Stage window;
    private Scene mainScene;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationModel applicationModel = new ApplicationModel();
        ApplicationController applicationController = new ApplicationController(applicationModel);

        window = stage;
        window.setTitle("StartApp screen");
        MainView mainView = new MainView(window);

        window.setOnCloseRequest(e -> {
            e.consume();
            mainView.closeApplication(window);
        });

        mainScene = new Scene(mainView, 900, 600);
        window.setScene(mainScene);
        window.show();
    }

}
