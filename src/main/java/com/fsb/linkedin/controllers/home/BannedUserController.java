package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.OtherAccountDAO;
import com.fsb.linkedin.entities.OtherAccount;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class BannedUserController {
    public VBox goToProfile;
    public ImageView profilePicture;
    public Label username;
    public Label date;
    public Button unbanUser;
    private int account_id;


    public void onUnban(ActionEvent event) throws IOException {
        OtherAccountDAO.unbanUser(account_id);
        SceneSwitcher.goTo(getClass(),"bannedUsersContainer",unbanUser);
    }

    public void setData(int id) {
        OtherAccountDAO.loadUser(id);
        account_id = id;
        profilePicture.setImage(MediaConverter.getImage(OtherAccount.getInstance().getProfilePicture()));
        username.setText(OtherAccount.getInstance().getEmail());
        date.setText(OtherAccountDAO.getBanDate(id));
    }
}
