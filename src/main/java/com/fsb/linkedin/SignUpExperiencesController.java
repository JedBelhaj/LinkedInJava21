package com.fsb.linkedin;

import com.fsb.linkedin.entities.Experience;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.utils.FieldVerifier;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SignUpExperiencesController {
    public Button remove;
    public Button update;
    public Button back;
    public Button next;
    public ListView<Experience> experiencesList;
    private List<Experience> experiences = new ArrayList<>();
    public TextField type;
    public TextField title;
    public TextField mission;
    public TextField technology;
    public DatePicker dateStart;
    public DatePicker dateFinish;
    public TextArea description;
    public Button clear;
    public Button add;

    public Experience onRemove() {
        if (!experiencesList.getItems().isEmpty()){
            Experience selectedExperience = experiencesList.getSelectionModel().getSelectedItem();
            experiencesList.getItems().remove(selectedExperience);
            experiences.remove(selectedExperience);
            return selectedExperience;
        }
        return null;
    }

    public void onUpdate() {
        if (!experiencesList.getItems().isEmpty()){
            Experience updateExperience = experiencesList.getSelectionModel().getSelectedItem();
            if (updateExperience!=null){
                type.setText(updateExperience.getType());
                title.setText(updateExperience.getTitle());
                mission.setText(updateExperience.getMission());
                technology.setText(updateExperience.getTechnology());
                dateStart.setValue(updateExperience.getStartDate());
                dateFinish.setValue(updateExperience.getFinishDate());
                description.setText(updateExperience.getDescription());
            }
            onRemove();
        }
    }

    public void onBack() {
        SceneSwitcher.previous(back);
    }

    public void onNext() throws IOException {
        PersonalAccount p = PersonalAccount.getInstance();
        p.setExperiences(experiences);
        PersonalAccount.setInstance(p);
        SceneSwitcher.goTo(getClass(),"signupProjects",next);
    }

    public void onClear() {
        Stream.of(type,title,mission,technology,description).forEach(TextInputControl::clear);
        dateStart.setValue(null);
        dateFinish.setValue(null);
        Stream.of(type,title,mission,technology,description,dateStart,dateFinish).forEach(n -> n.setStyle("-fx-border-color: grey"));
    }

    public void onAdd() {
        boolean fieldsAreValid = FieldVerifier.areValid(type,title,mission,technology,description);
        boolean dateStartIsValid = FieldVerifier.dateIsValid(dateStart);
        boolean dateFinishIsValid = FieldVerifier.dateIsValid(dateFinish);
        if (fieldsAreValid && dateStartIsValid && dateFinishIsValid){
            Experience experience = new Experience(
                    title.getText(),
                    technology.getText(),
                    dateStart.getValue(),
                    dateFinish.getValue(),
                    description.getText(),
                    type.getText(),
                    mission.getText()
            );
            experiencesList.getItems().add(experience);
            experiences.add(experience);

            onClear();
        }
        System.out.println();
        experiences.forEach(System.out::println);
    }
}
