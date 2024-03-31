package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.OtherAccountDAO;
import com.fsb.linkedin.entities.OtherAccount;
import com.fsb.linkedin.utils.MediaConverter;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class FriendComponentController {
    private int friendID;
    public HBox contactContainer;
    public ImageView profilePicture;
    public Label userName;

    public void setData(int friendID) {
        this.friendID = friendID;
        OtherAccountDAO.loadUser(friendID);
        profilePicture.setImage(MediaConverter.getImage(OtherAccountDAO.getProfilePicture(friendID)));
        userName.setText(OtherAccount.getInstance().getFirstName());
    }
}
