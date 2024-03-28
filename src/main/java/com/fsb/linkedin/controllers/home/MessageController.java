package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.OtherAccountDAO;
import com.fsb.linkedin.entities.Message;
import com.fsb.linkedin.entities.OtherAccount;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.MediaUploader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class MessageController {
    public HBox otherProfile;
    public ImageView imgProfile;
    public Label username;
    public ImageView imgVerified;
    public Label date;
    public ImageView audience;
    public Label caption;

    public void setData(Message message){
        OtherAccountDAO.loadUser(message.getSenderID());
        imgProfile.setImage(MediaConverter.getImage(OtherAccount.getInstance().getProfilePicture()));
        username.setText(OtherAccount.getInstance().getFirstName());
        date.setText("date");
        caption.setText(message.getCaption());
    }
}
