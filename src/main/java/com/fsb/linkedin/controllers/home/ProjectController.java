package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.entities.Project;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.w3c.dom.Text;

public class ProjectController {

    @FXML
    public Label projectname;
    @FXML
    public Label projectdiscription;

    public Project project;

    public Label getProjectname() {
        return projectname;
    }

    public void setProjectname(Label projectname) {
        this.projectname = projectname;
    }

    public Label getProjectdiscription() {
        return projectdiscription;
    }

    public void setProjectdiscription(Label projectdiscription) {
        this.projectdiscription = projectdiscription;
    }
    public  void setproject(Project project){
        this.project= project;
        projectname.setText(project.getTitle());
        projectdiscription.setText(project.getDescription());
    }
    @Override
    public String toString() {
        return "ProjectController{" +
                "projectname=" + projectname +
                ", projectdiscription=" + projectdiscription +
                '}';
    }
}
