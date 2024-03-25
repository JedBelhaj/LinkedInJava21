package com.fsb.linkedin;

import com.fsb.linkedin.utils.ComboUtils;
import com.fsb.linkedin.utils.FieldVerifier;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpAdminController implements Initializable {
    public TextField firstName;
    public TextField lastName;
    public TextField phoneNumber;
    public DatePicker dateOfBirth;
    public TextField email;
    public ComboBox<String> gender;
    public ComboBox<String> country;
    public PasswordField adminCode;
    public Button back;
    public Button next;

    public void onBack() {
        SceneSwitcher.previous(back);
    }

    public void onNext() {
        boolean fieldsAreValid = FieldVerifier.areValid(firstName, lastName);
        boolean emailIsValid = FieldVerifier.emailIsValid(email);
        boolean phoneIsValid = FieldVerifier.phoneIsValid(phoneNumber);
        boolean dateIsValid = FieldVerifier.dateIsValid(dateOfBirth);
        boolean genderIsValid = FieldVerifier.choiceBoxIsValid(gender);
        boolean countryIsValid = FieldVerifier.choiceBoxIsValid(country);

        if (fieldsAreValid && emailIsValid && phoneIsValid && dateIsValid && genderIsValid && countryIsValid){
            System.out.println("good");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        country.getItems().addAll(ComboUtils.countries);
        gender.getItems().addAll("Male","Female");
    }
}
