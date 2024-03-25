package com.fsb.linkedin;

import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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

    public void editprofile() throws IOException {
        System.out.println("it is clicking");
        SceneSwitcher.goTo(getClass(),"EditProfile",editprofile);

    }

    public void privacypolicy() throws IOException {
        SceneSwitcher.goTo(getClass(),"PrivacyPolicy",privacypolicy);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PersonalAccount p = PersonalAccount.getInstance();
        profilePicture.setImage(p.getProfileImage());
        ProfileName.setText(p.getFirstName());
        firstName.setText(p.getFirstName());
        lastName.setText(p.getLastName());
        email.setText(p.getEmail());
        phoneNumber.setText(p.getPhoneNumber());
    }
}