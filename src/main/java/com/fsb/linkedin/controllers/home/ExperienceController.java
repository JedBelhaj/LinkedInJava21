package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.entities.Experience;
import com.fsb.linkedin.entities.Project;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ExperienceController {

    @FXML
    public Label experiencename;
    @FXML
    public Label experiencediscription;

    public Experience experience;

    public Label getExperiencename() {
        return experiencename;
    }

    public void setExperiencename(Label experiencename) {
        this.experiencename = experiencename;
    }

    public Label getExperiencediscription() {
        return experiencediscription;
    }

    public void setExperiencediscription(Label experiencediscription) {
        this.experiencediscription = experiencediscription;
    }



    public  void setexperience(Experience experience){
        this.experience= experience;
        experiencename.setText(experience.getTitle());
        experiencediscription.setText(experience.getDescription());
    }

    public ExperienceController(Label experiencename, Label experiencediscription) {
        this.experiencename = experiencename;
        this.experiencediscription = experiencediscription;
    }

    @Override
    public String toString() {
        return "ExperienceController{" +
                "experiencename=" + experiencename +
                ", experiencediscription=" + experiencediscription +
                ", experience=" + experience +
                '}';
    }
}
