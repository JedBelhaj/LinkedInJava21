package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.FriendRequestDAO;
import com.fsb.linkedin.DAO.OtherAccountDAO;
import com.fsb.linkedin.entities.Experience;
import com.fsb.linkedin.entities.OtherAccount;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.entities.Project;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    boolean banned = false;
    public VBox addFriendContainer;
    private String friendshipStatus;

    public Button profile;

    public Button home;
    public Button decline = new Button("Decline Friend Request");

    public Button chatroom;
    public Button notifications;

    public Label username;
    public Label userlastname;
    public Label userphonenumber;
    public Label useremail;
    public Label userbirthdate;
    public Label experiencename;

    public VBox projectconainer;

    public VBox experiencecontainer;

    public Text experiencediscription;
    public ImageView userProfilePicture;
    public Label userFirstName;
    public Button addFriend;
    public Button blockAccount;

    private String followType = "Friend";

    public void profile() throws IOException {
        SceneSwitcher.goTo(getClass(),"PersonalProfile",profile);

    }

    @Override
    public String toString() {
        return "ProfileController{" +
                "profile=" + profile +
                ", home=" + home +
                ", chatroom=" + chatroom +
                ", notifications=" + notifications +
                ", username=" + username +
                ", userlastname=" + userlastname +
                ", userphonenumber=" + userphonenumber +
                ", useremail=" + useremail +
                ", userbirthdate=" + userbirthdate +
                '}';
    }

    public void home() throws IOException {
        SceneSwitcher.goTo(getClass(),"homepage",home);

    }
    public void chatroom() throws IOException {
        SceneSwitcher.goTo(getClass(),"chatroom",chatroom);

    }
    public void notifications() throws IOException {
        SceneSwitcher.goTo(getClass(),"notifications",notifications);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (PersonalAccount.getInstance().getType().equals("Enterprise"))
            followType="Follow";

        decline.setText("Decline "+followType+" request");

        OtherAccount p = OtherAccount.getInstance();

        if (PersonalAccount.getInstance().getType().equals("Admin")){
            if(PersonalAccount.getInstance().getType().equals("Admin")){
                Button banUser = new Button("Ban User");
                if (banned)
                    banUser.setText("Unban User");
                banUser.setStyle("-fx-border-color: red");
                banUser.setOnMouseClicked(event -> {
                    if (banned){
                        banUser.setText("Ban User");
                        banned = false;
                        OtherAccountDAO.unbanUser(OtherAccountDAO.loadUserID(p.getEmail()));
                    }else {
                        banUser.setText("UnBan User");
                        banned = true;
                        OtherAccountDAO.banUser(OtherAccountDAO.loadUserID(p.getEmail()));
                    }
                });
                addFriendContainer.getChildren().add(banUser);
            }
        }

        username.setText(p.getFirstName() + " " + p.getLastName());
        userFirstName.setText(p.getFirstName());
        userlastname.setText(p.getLastName());
        userphonenumber.setText(p.getPhoneNumber());
        useremail.setText(p.getEmail());
        userbirthdate.setText(p.getDateOfBirth().toString());
        userProfilePicture.setImage(MediaConverter.getImage(OtherAccount.getInstance().getProfilePicture()));

        //friend request button
        int otherAccountID = OtherAccountDAO.loadUserID(useremail.getText());
        friendshipStatus = FriendRequestDAO.getFriendRequestStatus(otherAccountID);
        System.out.println(friendshipStatus);
        switch (friendshipStatus) {
            case "AlreadyFriends":
                addFriend.setText("Remove "+followType);
                break;
            case "UserAlreadyRequesting":
                addFriend.setText("Remove "+followType+" Request");
                break;
            case "OtherAlreadyRequesting":
                addFriend.setText("Accept "+followType+" Request");
                decline.setId("decline");
                decline.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int otherID = OtherAccountDAO.loadUserID(useremail.getText());
                        System.out.println("declining...");
                        FriendRequestDAO.removeFriendRequest(otherID);
                        friendshipStatus = "None";
                        addFriend.setText("Add Friend");
                        addFriendContainer.getChildren().remove(decline);
                    }
                });
                addFriendContainer.getChildren().add(decline);

                break;
            default:
                break;
        }


        List<Project> projects = OtherAccount.getInstance().getProjects();
        List<Experience> experiences = OtherAccount.getInstance().getExperiences();

        try {
            for (Project project : projects) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/project.fxml"));
                VBox vBox = fxmlLoader.load();
                ProjectController projectController = fxmlLoader.getController();
                projectController.setproject(project);
                projectconainer.getChildren().add(vBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            for (Experience experience : experiences) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/project.fxml"));
                VBox vBox = fxmlLoader.load();
                ProjectController projectController = fxmlLoader.getController();
                projectController.setproject(experience);
                experiencecontainer.getChildren().add(vBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onAddFriend() {
        int otherID = OtherAccountDAO.loadUserID(useremail.getText());
        FriendRequestDAO.sendFriendRequest(otherID);
        switch (friendshipStatus){
            case ("None"):
                FriendRequestDAO.sendFriendRequest(otherID);
                friendshipStatus = "UserAlreadyRequesting";
                addFriend.setText("Remove "+followType+" Request");
                break;
            case ("UserAlreadyRequesting"):
                FriendRequestDAO.removeFriendRequest(otherID);
                friendshipStatus = "None";
                addFriend.setText("Add "+followType);
                break;
            case ("AlreadyFriends"):
                FriendRequestDAO.removeFriend(otherID);
                friendshipStatus = "None";
                addFriend.setText("Add "+followType);
                break;
            case ("OtherAlreadyRequesting"):
                FriendRequestDAO.acceptFriendRequest(otherID);
                friendshipStatus = "AlreadyFriends";
                addFriendContainer.getChildren().remove(decline);
                addFriend.setText("Remove "+followType);
                break;
            default:
                break;
        }
    }

    public void onBlockAccount(ActionEvent event) {

    }
}
