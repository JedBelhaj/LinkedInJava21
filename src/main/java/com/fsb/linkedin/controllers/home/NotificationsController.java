package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.*;
import com.fsb.linkedin.DAO.FriendRequestDAO;
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
    public List<Friendrequest> getfriendrequestnotif(){
        List<Friendrequest>ls=new ArrayList<>();
        Friendrequest friendrequest;
        for (int i=0;i<10;i++){
            friendrequest=new Friendrequest();
            Account account=new Account();
            account.setName(" jed");
            account.setProfileImg("/imgs/user.png".getBytes());
            account.setVerified(true);
            friendrequest.setAccount(account);
            friendrequest.setDate("Feb 18, 2021 at 12:00 PM");
            ls.add(friendrequest);
        }
        return ls;
    }
    public List<Reactionnotif> getreactionnotif(){
        List<Reactionnotif> ls=new ArrayList<>();
        Reactionnotif reactionnotif;
        for(int i =1;i<=20;i++){

            reactionnotif=new Reactionnotif();
            Account account = new Account();
            account.setName(" jed");
            account.setProfileImg("/imgs/user.png".getBytes());
            account.setVerified(true);
            reactionnotif.setAccount(account);
            reactionnotif.setDate("Feb 18, 2021 at 12:00 PM");
            ls.add(reactionnotif);
        }



        return ls;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(FriendRequestDAO.getFriendRequests());
        List<Reactionnotif> reactionnotifs=new ArrayList<>(getreactionnotif());
        List<Friendrequest> friendrequests= FriendRequestDAO.getFriendRequests();
        System.out.println("b");
        try {
            System.out.println("a");
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
            System.out.println("a");
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
    }
}
