package com.fsb.linkedin;

import com.fsb.linkedin.DAO.AccountDAO;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.utils.FieldVerifier;
import com.fsb.linkedin.utils.ImageConverter;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditProfileController implements Initializable {
    public Button profile;
    public Button Privacypolicy;
    public ImageView profilePicture;
    public Label ProfileName;
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField phoneNumber;
    public Button cancel;
    public Button saveChanges;
    @FXML protected void onSaveChanges(){
        boolean emailIsValid = FieldVerifier.emailIsValid(email);
        boolean emailIsUnique = AccountDAO.AttributeIsUnique("email",email.getText()) || Objects.equals(PersonalAccount.getInstance().getEmail(), email.getText());
        FieldVerifier.isValid(email, n -> emailIsUnique);
        boolean phoneNumberIsValid = FieldVerifier.phoneIsValid(phoneNumber);
        boolean phoneNumberIsUnique = FieldVerifier.isValid(phoneNumber, n -> AccountDAO.AttributeIsUnique("phone_number",phoneNumber.getText()) || Objects.equals(PersonalAccount.getInstance().getPhoneNumber(), phoneNumber.getText()));
        boolean fieldsAreValid = FieldVerifier.areValid(firstName,lastName);
        if (emailIsUnique && emailIsValid && phoneNumberIsUnique && phoneNumberIsValid && fieldsAreValid){
            AccountDAO.editProfile(PersonalAccount.getInstance().getEmail(),phoneNumber.getText(),firstName.getText(),lastName.getText(),email.getText());
        }
    }
    @FXML protected void onCancel(){

    }

    public void profile() throws IOException {
        SceneSwitcher.goTo(getClass(),"PersonalProfile",profile);

    }
    public void Privacypolicy() throws IOException {
        SceneSwitcher.goTo(getClass(),"PrivacyPolicy",Privacypolicy);

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
