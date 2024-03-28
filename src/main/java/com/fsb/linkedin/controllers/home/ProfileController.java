package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.AccountDAO;
import com.fsb.linkedin.DAO.NotificationDAO;
import com.fsb.linkedin.DAO.OtherAccountDAO;
import com.fsb.linkedin.entities.Experience;
import com.fsb.linkedin.entities.OtherAccount;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.entities.Project;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.MediaUploader;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public VBox addFriendContainer;
    private String friendshipStatus;

    public Button profile;

    public Button home;
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

        OtherAccount p = OtherAccount.getInstance();


        username.setText(p.getFirstName() + " " + p.getLastName());
        userFirstName.setText(p.getFirstName());
        userlastname.setText(p.getLastName());
        userphonenumber.setText(p.getPhoneNumber());
        useremail.setText(p.getEmail());
        userbirthdate.setText(p.getDateOfBirth().toString());
        userProfilePicture.setImage(MediaConverter.getImage(OtherAccount.getInstance().getProfilePicture()));

        //friend request button
        int otherAccountID = OtherAccountDAO.loadUserID(useremail.getText());
        friendshipStatus = NotificationDAO.getFriendRequestStatus(otherAccountID);

        switch (friendshipStatus){
            case "AlreadyFriends":
                addFriend.setText("Remove Friend");
                break;
            case "UserAlreadyRequesting":
                addFriend.setText("Remove Friend Request");
                break;
            case "OtherAlreadyRequesting":
                addFriend.setText("Accept Friend Request");
                Button decline = new Button("Decline Friend Request");
                decline.setId("decline");
                decline.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("declining...");
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


        List<Project> projects= OtherAccount.getInstance().getProjects();
        List<Experience> experiences= OtherAccount.getInstance().getExperiences();

        try {
            for(Project project:projects){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/project.fxml"));
                VBox vBox=fxmlLoader.load();
                ProjectController projectController=fxmlLoader.getController();
                projectController.setproject(project);
                projectconainer.getChildren().add(vBox);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            for(Experience experience:experiences){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/project.fxml"));
                VBox vBox=fxmlLoader.load();
                ProjectController projectController=fxmlLoader.getController();
                projectController.setproject(experience);
                experiencecontainer.getChildren().add(vBox);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void onAddFriend() {
        int otherID = OtherAccountDAO.loadUserID(useremail.getText());
        NotificationDAO.sendFriendRequest(otherID);
        switch (friendshipStatus){
            case ("None"):
                NotificationDAO.sendFriendRequest(otherID);
                friendshipStatus = "UserAlreadyRequesting";
                addFriend.setText("Remove Friend Request");
                break;
            case ("UserAlreadyRequesting"):
                NotificationDAO.removeFriendRequest(otherID);
                friendshipStatus = "None";
                addFriend.setText("Add Friend");
                break;
            case ("AlreadyFriends"):
                NotificationDAO.removeFriend(otherID);
                friendshipStatus = "None";
                addFriend.setText("Add Friend");
                break;
            case ("OtherAlreadyRequesting"):
                NotificationDAO.acceptFriendRequest(otherID);
                friendshipStatus = "AlreadyFriends";
                addFriend.setText("Remove Friend");
                break;
            default:
                break;
        }
    }

    public void onBlockAccount(ActionEvent event) {

    }
}
