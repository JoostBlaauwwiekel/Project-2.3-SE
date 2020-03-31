package Project.MVC.Controller;

import Project.MVC.Model.ApplicationModel;
import Project.MVC.View.ApplicationView;

public class ApplicationController {

    private ApplicationModel applicationModel;
    private ApplicationView applicationView;

    public ApplicationController(ApplicationModel applicationModel){
        this.applicationModel = applicationModel;
        applicationView = new ApplicationView(this, applicationModel);
    }
}
