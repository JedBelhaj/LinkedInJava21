package com.fsb.linkedin.controllers.login;

import com.fsb.linkedin.DAO.AccountDAO;
import com.fsb.linkedin.DAO.OtherAccountDAO;
import com.fsb.linkedin.entities.OtherAccount;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.utils.FieldVerifier;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LogInController {
    //TODO add cleanup temp when the app is shut down
    public ImageView image;
    private Stage stage;
    private Scene scene;

    private Image openEye = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imgs/eye-close-up.png")));
    private Image closedEye = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imgs/eyebrow.png")));
    private boolean passwordIsShown = false;

    @FXML
    private ImageView showPassword;
    @FXML
    private Label forgot;
    @FXML
    private TextField user;
    @FXML
    private PasswordField pass;
    @FXML
    private Button login;
    @FXML
    private Label messageLabel;
    @FXML
    protected void onShowPassword(){
        passwordIsShown = !passwordIsShown;
        if (passwordIsShown) {
            showPassword.setImage(openEye);
        } else {
            showPassword.setImage(closedEye);
        }

    }
    @FXML
    protected void onSubmit() throws IOException {
        boolean userIsValid = FieldVerifier.emailIsValid(user) || FieldVerifier.phoneIsValid(user);
        boolean passIsValid = FieldVerifier.isValid(pass);
        if (userIsValid && passIsValid){
            boolean loginIsValid = FieldVerifier.isValid(user, n -> AccountDAO.loginIsValid(user.getText(),pass.getText()));
            FieldVerifier.isValid(pass, n -> AccountDAO.loginIsValid(user.getText(),pass.getText()));
            if (loginIsValid){
                System.out.println("valid login!");
                AccountDAO.loadUser(user.getText());
                System.out.println(PersonalAccount.getInstance());

                SceneSwitcher.setSaveHistory(false);

                SceneSwitcher.goTo(getClass(),"homepage",login);
            }
            else {
                messageLabel.setText("Incorrect Credentials!");
            }
            if (OtherAccountDAO.isBanned(AccountDAO.loadUserID())){
                messageLabel.setText("Sorry, But your account is Banned!");
                OtherAccount.setInstance(null);
            }
        }
    }
    @FXML
    protected void onSignUp() throws Exception {
        SceneSwitcher.goTo(getClass(),"accountType",login);

    }
}