package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.CommentSectionDAO;
import com.fsb.linkedin.DAO.OtherAccountDAO;
import com.fsb.linkedin.entities.BannedUser;
import com.fsb.linkedin.entities.Comment;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class BannedUsersContainerController implements Initializable {
    public VBox usersContainer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            for (int id : OtherAccountDAO.getBannedUsers()){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/bannedUser.fxml"));
                HBox vBox=fxmlLoader.load();
                BannedUserController commentController =fxmlLoader.getController();
                commentController.setData(id);
                usersContainer.getChildren().add(vBox);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
