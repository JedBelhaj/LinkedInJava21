package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.ChatRoomDAO;
import com.fsb.linkedin.DAO.HomePageDAO;
import com.fsb.linkedin.DAO.OtherAccountDAO;
import com.fsb.linkedin.entities.*;
import com.fsb.linkedin.utils.FieldVerifier;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.MediaUploader;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    public HBox uploadImage;
    public HBox uploadVideo;
    public HBox buttonContainer;
    public ImageView recherche;
    public HBox navContainer;
    public Button searchFriends;
    public VBox friendsContainer;
    private File createPostImage;
    private File createPostVideo;
    private Post createPost = new Post();
    public Button profile;
    public Button home;
    public Button chatroom;
    public Button notifications;
    public ImageView profilePicture;
    public ImageView postProfilePicture;
    public Label profileName;
    public VBox postContainer;
    public VBox offerContainer;
    public TextField postField;
    public ComboBox<String> postTypeCombo = new ComboBox<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            for (int friendID : HomePageDAO.getFriends()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/friendComponent.fxml"));
                HBox vBox=fxmlLoader.load();
                vBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        OtherAccountDAO.loadUser(friendID);
                        try {
                            if (OtherAccount.getInstance().getType().equals("Enterprise"))
                                SceneSwitcher.openNewWindow(getClass(),"enterpriseprofile","profile");
                            else
                                SceneSwitcher.openNewWindow(getClass(),"profile","profile");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                FriendComponentController controller=fxmlLoader.getController();
                controller.setData(friendID);
                friendsContainer.getChildren().add(vBox);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        Button bannedUsers = new Button("Banned Users");
        if (PersonalAccount.getInstance().getType().equals("Admin")) {
            navContainer.getChildren().add(bannedUsers);
            bannedUsers.setStyle("-fx-border-color: red");
            bannedUsers.setOnMouseClicked(event -> {
                try {
                    SceneSwitcher.openNewWindow(getClass(),"bannedUsersContainer","Banned Users");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        if (PersonalAccount.getInstance().getType().equals("Enterprise")){
            postTypeCombo.getItems().addAll("Normal Post","Job Offer","Internship Offer");
            postTypeCombo.setPromptText("Post Type");
            buttonContainer.getChildren().add(postTypeCombo);
        }else{
            postContainer.getChildren().remove(postTypeCombo);
        }
        String postTypeFXML;
        Image profileImage = PersonalAccount.getInstance().getProfileImage();
        profilePicture.setImage(profileImage);
        System.out.println(PersonalAccount.getInstance().getType());
        if(Objects.equals(PersonalAccount.getInstance().getType(), "Enterprise")){
            profileName.setText(PersonalAccount.getInstance().getFirstName()+" Corp.");
        }
        else{
            profileName.setText(PersonalAccount.getInstance().getFirstName()+ " " + PersonalAccount.getInstance().getLastName());
        }

        postProfilePicture.setImage(profileImage);
        List<Post> posts = HomePageDAO.getPosts();
        try {
            for (Post post : posts) {
                if (!OtherAccountDAO.isBanned(post.getAccount().getID())) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    System.out.println(Arrays.toString(post.getImage()).equals("null"));
                    if (Arrays.toString(post.getImage()).equals("null")) postTypeFXML = "imagelessPost";
                    else postTypeFXML = "post";
                    System.out.println(postTypeFXML);
                    fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/" + postTypeFXML + ".fxml"));
                    VBox vBox = fxmlLoader.load();
                    System.out.println("im here");

                    PostController postController = fxmlLoader.getController();
                    postController.setData(post);
                    postContainer.getChildren().add(vBox);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        List<Post> offers = HomePageDAO.getJobOffers();
        try {
            for (Post offer : offers) {
                if (!OtherAccountDAO.isBanned(offer.getAccount().getID())) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    System.out.println(Arrays.toString(offer.getImage()).equals("null"));
                    if (Arrays.toString(offer.getImage()).equals("null")) postTypeFXML = "imagelessPost";
                    else postTypeFXML = "post";
                    System.out.println(postTypeFXML);
                    fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/" + postTypeFXML + ".fxml"));
                    VBox vBox = fxmlLoader.load();
                    System.out.println("im here");

                    PostController postController = fxmlLoader.getController();
                    postController.setData(offer);
                    offerContainer.getChildren().add(vBox);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }



    }
    public void profile() throws IOException {
        SceneSwitcher.openNewWindow(getClass(),"PersonalProfile","Profile");

    }
    public void home() throws IOException {
        SceneSwitcher.goTo(getClass(),"homepage",home);

    }
    public void chatroom() throws IOException {
        SceneSwitcher.openNewWindow(getClass(),"chatroom","Chatroom");


    }
    public void notifications() throws IOException {
        SceneSwitcher.openNewWindow(getClass(),"notificationContainer","Notifications");

    }

    public void getoffer() {
    }

    public void onPost() throws IOException {
        boolean validFields = true;
        if (!postTypeCombo.getItems().isEmpty()){
            System.out.println(postTypeCombo.getItems());
            System.out.println("checking if combobox is good");
            validFields = FieldVerifier.choiceBoxIsValid(postTypeCombo);
        }
        if (FieldVerifier.isValid(postField) || validFields){
            if (!postTypeCombo.getItems().isEmpty()) {
                System.out.println("Enterprise post");
                createPost.setPostType(postTypeCombo.getValue());
            }
            else{
                createPost.setPostType("Normal");
                System.out.println("normal post");
            }
            createPost.setCaption(postField.getText());
            postField.setText("");
            createPostImage = null;
            createPostVideo = null;
            createPost.setAudience(PostAudience.PUBLIC);
            if (createPostImage!=null) createPost.setImage(MediaConverter.convertFileToByteArray(createPostImage));
            //TODO add video uploading
            System.out.println(createPost);
            HomePageDAO.createPost(createPost);
            SceneSwitcher.goTo(getClass(),"homepage",home);
        }
    }

    public void onUploadImage() throws IOException {
        createPostImage = MediaUploader.getMediaAsFile(uploadImage);
        if (createPostImage!=null) {
            System.out.println("got the image " + createPostImage.getPath());
            createPost.setImage(MediaConverter.convertFileToByteArray(createPostImage));
        }
    }

    public void onUploadVideo(MouseEvent mouseEvent) {
        createPostVideo = MediaUploader.getMediaAsFile(uploadVideo);

    }

    public void onRecherche(MouseEvent mouseEvent) throws IOException {
        System.out.println("clicked");
        SceneSwitcher.openNewWindow(getClass(),"recherche", "Search for Job or Internship Offers");
    }

    public void onLogOut(MouseEvent mouseEvent) throws IOException {
        PersonalAccount.setInstance(null);
        OtherAccount.setInstance(null);
        SceneSwitcher.goTo(getClass(),"login",postField);
    }

    public void onSearchFriends(ActionEvent event) throws IOException {
        SceneSwitcher.openNewWindow(getClass(),"searchFriends","Search For Accounts");
    }
}
