package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.*;
import com.fsb.linkedin.DAO.FriendRequestDAO;
import com.fsb.linkedin.DAO.NotificationDAO;
import com.fsb.linkedin.entities.Account;
import com.fsb.linkedin.entities.Friendrequest;
import com.fsb.linkedin.entities.Reactionnotif;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NotificationsController implements Initializable {
    public VBox notificationcontainer;
    @FXML
    private Label date;
    @FXML
    private ImageView imgProfile;

    @FXML
    private Label username;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(FriendRequestDAO.getFriendRequests());
        List<Reactionnotif> reactionnotifs= NotificationDAO.getReactionNotifications();
        System.out.println(reactionnotifs);
        List<Friendrequest> friendrequests= FriendRequestDAO.getFriendRequests();
        try {
            for (Reactionnotif reactionnotif : reactionnotifs) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/reactionnotif.fxml"));
                VBox vBox=fxmlLoader.load();
                ReactionnotifController reactionnotifController=fxmlLoader.getController();
                reactionnotifController.setData(reactionnotif);
                notificationcontainer.getChildren().add(vBox);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            for (Friendrequest friendrequest : friendrequests) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/friendrequest.fxml"));
                VBox vBox=fxmlLoader.load();
                FriendrequestController friendrequestController=fxmlLoader.getController();
                friendrequestController.setData(friendrequest);
                notificationcontainer.getChildren().add(vBox);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        notificationcontainer.getChildren().sorted();
    }
}
