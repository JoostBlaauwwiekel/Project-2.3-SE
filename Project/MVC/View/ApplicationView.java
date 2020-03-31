package Project.MVC.View;

import Project.MVC.Controller.ApplicationController;
import Project.MVC.Model.ApplicationModel;

public class ApplicationView implements AbstractView {

    private ApplicationController applicationController;
    private ApplicationModel applicationModel;

    public ApplicationView(ApplicationController applicationController, ApplicationModel applicationModel){
        this.applicationController = applicationController;
        this.applicationModel = applicationModel;
    }
}
