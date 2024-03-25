package com.fsb.linkedin;

import com.fsb.linkedin.utils.ComboUtils;
import com.fsb.linkedin.utils.FieldVerifier;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpEntrepriseController implements Initializable {

    public TextField companyName;
    public TextField Industry;
    public TextField websiteURL;
    public DatePicker dateOfFoundation;
    public TextField email;
    public ComboBox<String> country;
    public Button back;
    public Button next;

    public void onBack() {
        SceneSwitcher.previous(back);
    }

    public void onNext() {
        boolean webSiteIsValid = FieldVerifier.URLisValid(websiteURL);
        boolean fieldsAreValid = FieldVerifier.areValid(companyName,Industry);
        boolean dateIsValid = FieldVerifier.dateIsValid(dateOfFoundation);
        boolean emailIsValid = FieldVerifier.emailIsValid(email);
        boolean countryIsValid = FieldVerifier.choiceBoxIsValid(country);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        country.getItems().addAll(ComboUtils.countries);
    }
}
