package com.fsb.linkedin.controllers.signup;

import com.fsb.linkedin.DAO.AccountDAO;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SignUpEnterpriseController implements Initializable {
    private File profilePicture;
    public TextField companyName;
    public TextField Industry;
    public TextField websiteURL;
    public DatePicker dateOfFoundation;
    public TextField email;
    public ComboBox<String> country;
    public Button back;
    public Button next;
    public TextField phoneNumber;
    public Button uploadImg;
    public ImageView profileImg;
    public PasswordField password;
    public PasswordField confirmPassword;
    public Label messageLabel;

    public void onBack() {
        SceneSwitcher.previous(back);
    }

    public void onNext() throws IOException {
        List<Boolean> allFieldsAreValid = new ArrayList<>();
        allFieldsAreValid.add(FieldVerifier.URLisValid(websiteURL));
        allFieldsAreValid.add(FieldVerifier.areValid(companyName,Industry));
        allFieldsAreValid.add(FieldVerifier.dateIsValid(dateOfFoundation));
        allFieldsAreValid.add(FieldVerifier.emailIsValid(email) && FieldVerifier.isValid(email, n -> AccountDAO.AttributeIsUnique("email",email.getText())));
        allFieldsAreValid.add(FieldVerifier.choiceBoxIsValid(country));
        allFieldsAreValid.add(FieldVerifier.isValid(password , (p) -> ((PasswordField)p).getText().length()>=8));
        allFieldsAreValid.add(FieldVerifier.isValid(confirmPassword , (p) -> password.getText().equals(confirmPassword.getText()) && ((PasswordField)p).getText().length()>=8));
        allFieldsAreValid.add(FieldVerifier.phoneIsValid(phoneNumber) && FieldVerifier.isValid(phoneNumber, n -> AccountDAO.AttributeIsUnique("phone_number",phoneNumber.getText())));
        allFieldsAreValid.add(FieldVerifier.isValid(uploadImg, n -> profileImg.getImage() != null));

        if (allFieldsAreValid.stream().reduce((a,b) -> a && b).orElse(false)){
            messageLabel.setText("");
            PersonalAccount p = PersonalAccount.getInstance();
            p.setFirstName(companyName.getText());
            p.setLastName(Industry.getText());
            p.setPhoneNumber(phoneNumber.getText());
            p.setEmail(email.getText());
            p.setCountry(country.getValue());
            p.setGender(null);
            p.setDateOfBirth(dateOfFoundation.getValue());
            p.setPassword(password.getText());
            p.setProfilePicture(MediaConverter.convertFileToByteArray(profilePicture));
            p.setType("Enterprise");
            p.setWebsite(websiteURL.getText());
            PersonalAccount.setInstance(p);
            AccountDAO.save(p);
        }else {
            if (!AccountDAO.AttributeIsUnique("email",email.getText()) || !AccountDAO.AttributeIsUnique("phone_number",phoneNumber.getText()) && FieldVerifier.emailIsValid(email) && FieldVerifier.phoneIsValid(phoneNumber)){
                messageLabel.setText("Email or Phone number is already in use");
            }else {
                messageLabel.setText("");
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        country.getItems().addAll(ComboUtils.countries);
    }

    public void onUploadImg(ActionEvent event) {
        profilePicture = MediaUploader.getMediaAsFile(uploadImg);
        if (profilePicture!=null){
            profileImg.setImage(MediaUploader.getImage(profilePicture));
        }
    }
}
