package com.fsb.linkedin.controllers.signup;

import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.entities.Qualification;
import com.fsb.linkedin.utils.FieldVerifier;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class SignUpQualificationsController{
    public Button remove;
    public Button update;
    public Button back;
    public Button next;
    public TextField diploma;
    public TextField title;
    public TextField institution;
    public TextField technology;
    public DatePicker dateStart;
    public DatePicker dateFinish;
    public TextArea description;
    public Button clear;
    public Button add;
    public static List<Qualification> qualifications = new ArrayList<>();
    public ListView<Qualification> qualificationsList;

    @FXML protected void onAdd() throws IOException {
        boolean fieldsAreValid = FieldVerifier.areValid(diploma,title,institution,technology,description);
        boolean dateStartIsValid = FieldVerifier.dateIsValid(dateStart);
        boolean dateFinishIsValid = FieldVerifier.dateIsValid(dateFinish);
        if (fieldsAreValid && dateStartIsValid && dateFinishIsValid){
            Qualification qualification = new Qualification(diploma.getText(), title.getText(), institution.getText(), technology.getText(), dateStart.getValue(), dateFinish.getValue(), description.getText());

            qualificationsList.getItems().add(qualification);
            qualifications.add(qualification);

            onClear();
        }

        System.out.println();
        qualifications.forEach(System.out::println);
    }
    @FXML protected void onClear(){
        Stream.of(diploma,title,institution,technology,description).forEach(TextInputControl::clear);
        dateStart.setValue(null);
        dateFinish.setValue(null);
        Stream.of(diploma,title,institution,technology,description,dateStart,dateFinish).forEach(n -> n.setStyle("-fx-border-color: grey"));
    }



    @FXML protected Qualification onRemove(){
        if (!qualificationsList.getItems().isEmpty()){
            Qualification selectedQualification = qualificationsList.getSelectionModel().getSelectedItem();
            qualificationsList.getItems().remove(selectedQualification);
            qualifications.remove(selectedQualification);
            return selectedQualification;
        }
        return null;
    }
    @FXML protected void onUpdate(){
        if (!qualificationsList.getItems().isEmpty()){
            Qualification updateQualification = qualificationsList.getSelectionModel().getSelectedItem();
            if (updateQualification!=null){
                diploma.setText(updateQualification.getDiploma());
                title.setText(updateQualification.getTitle());
                institution.setText(updateQualification.getInstitution());
                technology.setText(updateQualification.getTechnology());
                dateStart.setValue(updateQualification.getStartDate());
                dateFinish.setValue(updateQualification.getFinishDate());
                description.setText(updateQualification.getDescription());
            }
            onRemove();
        }
    }
    @FXML protected void onBack(){
        SceneSwitcher.previous(back);
    }
    @FXML protected void onNext() throws IOException {

        PersonalAccount p = PersonalAccount.getInstance();
        p.setQualifications(qualifications);
        PersonalAccount.setInstance(p);

        SceneSwitcher.goTo(getClass(),"signupExperiences",next);
    }
}
