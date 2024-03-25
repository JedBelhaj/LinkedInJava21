package com.fsb.linkedin;

import com.fsb.linkedin.DAO.AccountDAO;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.utils.ComboUtils;
import com.fsb.linkedin.utils.FieldVerifier;
import com.fsb.linkedin.utils.ImageConverter;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    File profilePicture;
    public ComboBox<String> country;
    public Button next;
    public Button back;
    public PasswordField password;
    public PasswordField confirmPassword;
    public Label messageLabel;
    public Button uploadImg;
    public ImageView profileImg;
    @FXML
    private LogInController mainController;
    public TextField firstName;
    public TextField lastName;
    public TextField phoneNumber;
    public DatePicker dateOfBirth;
    public TextField email;
    public ComboBox<String> gender;

    @FXML
    protected void onBack() throws IOException {
        SceneSwitcher.previous(back);
    }
    @FXML
    protected void onNext() throws IOException {
        //check validity of all fields

        boolean passwordIsValid = FieldVerifier.isValid(password , (p) -> ((PasswordField)p).getText().length()>=8);
        boolean passwordsMatch = FieldVerifier.isValid(confirmPassword , (p) -> password.getText().equals(confirmPassword.getText()) && ((PasswordField)p).getText().length()>=8);
        /*TODO :
           check if the email already exist in the database -- DONE
           add password regex (currently it only checks if the password is more or equal to 8 chars
           add profile picture uploading (and the option to have it on default)
           thats it lol
        */

        boolean fieldsAreValid = FieldVerifier.areValid(firstName, lastName);
        boolean emailIsValid = FieldVerifier.emailIsValid(email) && FieldVerifier.isValid(email, n -> AccountDAO.AttributeIsUnique("email",email.getText()));
        boolean phoneIsValid = FieldVerifier.phoneIsValid(phoneNumber) && FieldVerifier.isValid(phoneNumber, n -> AccountDAO.AttributeIsUnique("phone_number",phoneNumber.getText()));
        boolean dateIsValid = FieldVerifier.dateIsValid(dateOfBirth);
        boolean genderIsValid = FieldVerifier.choiceBoxIsValid(gender);
        boolean countryIsValid = FieldVerifier.choiceBoxIsValid(country);
        boolean imageIsValid = FieldVerifier.isValid(uploadImg, n -> profilePicture!=null);
        if (fieldsAreValid && emailIsValid && phoneIsValid && dateIsValid && genderIsValid && countryIsValid && passwordIsValid && passwordsMatch && imageIsValid){
            messageLabel.setText("");
            PersonalAccount p = PersonalAccount.getInstance();
            p.setFirstName(firstName.getText());
            p.setLastName(lastName.getText());
            p.setPhoneNumber(phoneNumber.getText());
            p.setEmail(email.getText());
            p.setCountry(country.getValue());
            p.setGender(gender.getValue());
            p.setDateOfBirth(dateOfBirth.getValue());
            p.setPassword(password.getText());
            p.setProfilePicture(ImageConverter.convertFileToByteArray(profilePicture));
            PersonalAccount.setInstance(p);

            SceneSwitcher.goTo(getClass(),"signupQualifications",next);
        }
        else {
            if (!AccountDAO.AttributeIsUnique("email",email.getText()) || !AccountDAO.AttributeIsUnique("phone_number",phoneNumber.getText()) && emailIsValid && phoneIsValid){
                messageLabel.setText("Email or Phone number is already in use");
            }else {
                messageLabel.setText("");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        country.getItems().addAll(ComboUtils.countries);
        gender.getItems().addAll("Male","Female");
    }
    @FXML
    protected void onUploadImg(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")+ "/Desktop"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG Image","*.jpg"), new FileChooser.ExtensionFilter("PNG Image", "*.png"), new FileChooser.ExtensionFilter("All image files","*.jpg","*.png"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file","*.txt"));
        profilePicture = fileChooser.showOpenDialog(uploadImg.getScene().getWindow());
        if (profilePicture!=null){
            Image image = new Image(profilePicture.getPath());
            profileImg.setImage(image);
        }
    }
}
