package com.fsb.linkedin.controllers.signup;

import com.fsb.linkedin.utils.FieldVerifier;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class accountTypeController implements Initializable {
    public ComboBox<String> accountType;
    public Button next;
    public Button back;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountType.getItems().addAll("Enterprise","Personal","Admin");
    }
    @FXML
    protected void onNext() throws IOException {
        if (FieldVerifier.choiceBoxIsValid(accountType)){
            String destination = "accountType";
            switch (accountType.getValue()){
                case "Personal":
                    destination = "signup";
                    break;
                case "Enterprise":
                    destination = "signupEnterprise";
                    break;
                case "Admin":
                    destination = "signupAdmin";
                    break;
                default:
                    break;
            }
            SceneSwitcher.goTo(getClass(),destination,next);
        }
    }
    @FXML
    protected void onBack() {
        SceneSwitcher.previous(back);
    }
}
