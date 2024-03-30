package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.utils.CVGenerator;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PersonalProfileController implements Initializable {
    public Button editprofile;
    public Button privacypolicy;
    public ImageView profilePicture;
    public Label ProfileName;
    public Label firstName;
    public Label lastName;
    public Label email;
    public Label phoneNumber;
    public Label lastNameLabel;
    public Button generateCV;

    public void editprofile() throws IOException {
        System.out.println("it is clicking");
        SceneSwitcher.goTo(getClass(),"EditProfile",editprofile);

    }

    public void privacypolicy() throws IOException {
        SceneSwitcher.goTo(getClass(),"PrivacyPolicy",privacypolicy);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(PersonalAccount.getInstance().getType().equals("Enterprise")){
            lastNameLabel.setText("Industry");
        }
        PersonalAccount p = PersonalAccount.getInstance();
        profilePicture.setImage(p.getProfileImage());
        ProfileName.setText(p.getFirstName());
        firstName.setText(p.getFirstName());
        lastName.setText(p.getLastName());
        email.setText(p.getEmail());
        phoneNumber.setText(p.getPhoneNumber());
    }

    public void onGenerateCV(ActionEvent event) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose a Location to Save your CV in.");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedDirectory = directoryChooser.showDialog(generateCV.getScene().getWindow());
        String directory = selectedDirectory.getPath()+"\\"+PersonalAccount.getInstance().getFirstName()
                +"_"+PersonalAccount.getInstance().getLastName()
                +"_CV.pdf";
        CVGenerator.createCV(directory);
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(directory));
            } else {
                System.out.println("Desktop is not supported.");
            }
        } catch (IOException e) {
            System.out.println("Error opening directory: " + e.getMessage());
        }
    }
}