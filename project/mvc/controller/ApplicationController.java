package project.mvc.controller;

import project.mvc.model.ApplicationModel;
import project.mvc.view.ApplicationView;

public class ApplicationController {

    private ApplicationModel applicationModel;
    private ApplicationView applicationView;

    public ApplicationController(ApplicationModel applicationModel){
        this.applicationModel = applicationModel;
        applicationView = new ApplicationView(this, applicationModel);
    }

    public ApplicationView getApplicationView(){
        return applicationView;
    }
}
