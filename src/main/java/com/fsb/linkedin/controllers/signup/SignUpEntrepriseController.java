package com.fsb.linkedin.controllers.signup;

import com.fsb.linkedin.utils.ComboUtils;
import com.fsb.linkedin.utils.FieldVerifier;
import com.fsb.linkedin.utils.MediaUploader;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class SignUpEntrepriseController implements Initializable {
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

    public void onBack() {
        SceneSwitcher.previous(back);
    }

    public void onNext() {
        List<Boolean> allFieldsAreValid = new ArrayList<>();
        allFieldsAreValid.add(FieldVerifier.URLisValid(websiteURL));
        allFieldsAreValid.add(FieldVerifier.areValid(companyName,Industry));
        allFieldsAreValid.add(FieldVerifier.dateIsValid(dateOfFoundation));
        allFieldsAreValid.add(FieldVerifier.emailIsValid(email));
        allFieldsAreValid.add(FieldVerifier.choiceBoxIsValid(country));
        allFieldsAreValid.add(FieldVerifier.isValid(password , (p) -> ((PasswordField)p).getText().length()>=8));
        allFieldsAreValid.add(FieldVerifier.isValid(confirmPassword , (p) -> password.getText().equals(confirmPassword.getText()) && ((PasswordField)p).getText().length()>=8));
        allFieldsAreValid.add(FieldVerifier.phoneIsValid(phoneNumber));
        allFieldsAreValid.add(FieldVerifier.isValid(uploadImg, n -> profileImg.getImage() != null));

        if (allFieldsAreValid.stream().reduce((a,b) -> a && b).orElse(false)){

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
