package com.fsb.linkedin;

import com.fsb.linkedin.DAO.AccountDAO;
import com.fsb.linkedin.DAO.OtherAccountDAO;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.utils.FieldVerifier;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.SceneSwitcher;
import com.fsb.linkedin.utils.VideoConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;

public class testController {

    public TextField user;
    public TextField pass;
    public Button login;
    public MediaView video;

    @FXML protected void onLogin() throws IOException, SQLException {
        boolean userValid = FieldVerifier.emailIsValid(user);
        boolean passValid = FieldVerifier.isValid(pass);
        if (userValid && passValid){
            boolean loginIsValid = FieldVerifier.isValid(user, n -> AccountDAO.loginIsValid(user.getText(),pass.getText()));
            FieldVerifier.isValid(pass, n -> AccountDAO.loginIsValid(user.getText(),pass.getText()));
            if (loginIsValid){
                AccountDAO.loadUser(user.getText());
                OtherAccountDAO.loadUser("vid@gmail.com");
                SceneSwitcher.goTo(getClass(),"profile",login);
            }
        }
    }
}
