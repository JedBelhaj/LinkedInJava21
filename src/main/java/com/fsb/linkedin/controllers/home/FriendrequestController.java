package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.*;
import com.fsb.linkedin.entities.Friendrequest;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.MediaUploader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FriendrequestController {
    @FXML
    private ImageView imgProfile;

    @FXML
    private Label username;
    @FXML
    private Label date;

    public void setData( Friendrequest friendrequest){
        Image img;
        img = MediaConverter.getImage(friendrequest.getAccount().getProfileImg());
        imgProfile.setImage(img);
        username.setText(friendrequest.getAccount().getName());
        date.setText(friendrequest.getDate());
    }
}
