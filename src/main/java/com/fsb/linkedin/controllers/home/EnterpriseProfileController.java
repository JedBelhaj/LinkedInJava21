package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.FriendRequestDAO;
import com.fsb.linkedin.DAO.OfferDAO;
import com.fsb.linkedin.DAO.OtherAccountDAO;
import com.fsb.linkedin.entities.*;
import com.fsb.linkedin.utils.BrowserOpener;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class EnterpriseProfileController implements Initializable {
    @FXML
    public Button profile;

    @FXML
    public Button home;
    @FXML
    public Button chatroom;
    @FXML
    public Button notifications;

    public VBox joboffercontainer;

    public VBox stagecontainer;
    public ImageView profilePicture;
    public VBox followContainer;
    public Button follow;
    public Label username;
    public Label userPhoneNumber;
    public Label userEmail;
    public Label date;
    public Label enterpriseName;
    public Label website;

    private String friendshipStatus;
    public Button decline = new Button("Decline Follow Request");

    public void profile() throws IOException {
        SceneSwitcher.goTo(getClass(),"PersonalProfile",profile);

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

        OtherAccount o = OtherAccount.getInstance();
        username.setText(o.getFirstName()+" Corp.");
        userEmail.setText(o.getEmail());
        userPhoneNumber.setText(o.getPhoneNumber());
        date.setText(o.getDateOfBirth().toString());
        follow.setText("Follow "+o.getFirstName());
        enterpriseName.setText(o.getFirstName());
        website.setText(o.getWebsite());
        website.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if (website.getText().contains("https://")||(website.getText().contains("http://")))BrowserOpener.openWebpage(new URI(website.getText()));
                    else BrowserOpener.openWebpage(new URI("https://"+website.getText()));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        profilePicture.setImage(MediaConverter.getImage(o.getProfilePicture()));


        //friend request button
        int otherAccountID = OtherAccountDAO.loadUserID(userEmail.getText());
        friendshipStatus = FriendRequestDAO.getFriendRequestStatus(otherAccountID);
        System.out.println(friendshipStatus);
        switch (friendshipStatus) {
            case "AlreadyFriends":
                follow.setText("Unfollow");
                break;
            case "UserAlreadyRequesting":
                follow.setText("Remove Follow Request");
                break;
            case "OtherAlreadyRequesting":
                follow.setText("Accept Follow Request");
                decline.setId("decline");
                decline.setOnAction(event -> {
                    int otherID = OtherAccountDAO.loadUserID(userEmail.getText());
                    System.out.println("declining...");
                    FriendRequestDAO.removeFriendRequest(otherID);
                    friendshipStatus = "None";
                    follow.setText("Follow "+o.getFirstName());
                    followContainer.getChildren().remove(decline);
                });
                followContainer.getChildren().add(decline);

                break;
            default:
                break;
        }



        List<Post> offers = null;
        offers = OfferDAO.getOffers(OtherAccountDAO.loadUserID(userEmail.getText()),"Job Offer");

        try {
            for (Post post : offers) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                System.out.println(Arrays.toString(post.getImage()).equals("null"));
                String postTypeFXML;
                if (Arrays.toString(post.getImage()).equals("null")) postTypeFXML = "imagelessPost";
                else postTypeFXML = "post";
                System.out.println(postTypeFXML);
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/"+postTypeFXML+".fxml"));
                VBox vBox=fxmlLoader.load();
                System.out.println("im here");

                PostController postController=fxmlLoader.getController();
                postController.setData(post);
                joboffercontainer.getChildren().add(vBox);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        offers = OfferDAO.getOffers(OtherAccountDAO.loadUserID(userEmail.getText()),"Enternship Offer");
        System.out.println(offers);
        try {
            for (Post post : offers) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                System.out.println(Arrays.toString(post.getImage()).equals("null"));
                String postTypeFXML;
                if (Arrays.toString(post.getImage()).equals("null")) postTypeFXML = "imagelessPost";
                else postTypeFXML = "post";
                System.out.println(postTypeFXML);
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/"+postTypeFXML+".fxml"));
                VBox vBox=fxmlLoader.load();
                System.out.println("im here");

                PostController postController=fxmlLoader.getController();
                postController.setData(post);
                stagecontainer.getChildren().add(vBox);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void onFollow(ActionEvent event) {
        int otherID = OtherAccountDAO.loadUserID(userEmail.getText());
        FriendRequestDAO.sendFriendRequest(otherID);
        OtherAccount o = OtherAccount.getInstance();
        switch (friendshipStatus){
            case ("None"):
                FriendRequestDAO.sendFriendRequest(otherID);
                friendshipStatus = "UserAlreadyRequesting";
                follow.setText("Remove Follow Request");
                break;
            case ("UserAlreadyRequesting"):
                FriendRequestDAO.removeFriendRequest(otherID);
                friendshipStatus = "None";
                follow.setText("Follow "+o.getFirstName());
                break;
            case ("AlreadyFriends"):
                FriendRequestDAO.removeFriend(otherID);
                friendshipStatus = "None";
                follow.setText("Follow "+o.getFirstName());
                break;
            case ("OtherAlreadyRequesting"):
                FriendRequestDAO.acceptFriendRequest(otherID);
                friendshipStatus = "AlreadyFriends";
                followContainer.getChildren().remove(decline);
                follow.setText("Unfollow");
                break;
            default:
                break;
        }
    }
}
