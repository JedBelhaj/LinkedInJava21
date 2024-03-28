package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.entities.Contact;
import com.fsb.linkedin.utils.MediaConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ContactController {

    @FXML
    private ImageView imgProfile;

    @FXML
    private Label username;
    @FXML
    private Label msg;
    @FXML
    private Label date;

    public void setContact(Contact contact){
        Image img = new Image(MediaConverter.convertByteArrayToInputStream(contact.getAccount().getProfileImg()));
        imgProfile.setImage(img);
        username.setText(contact.getAccount().getName());
        date.setText(contact.getDate());
        msg.setText(contact.getMsg());
    }
}
