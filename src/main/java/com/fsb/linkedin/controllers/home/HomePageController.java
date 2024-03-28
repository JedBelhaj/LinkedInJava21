package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.HomePageDAO;
import com.fsb.linkedin.entities.*;
import com.fsb.linkedin.utils.FieldVerifier;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.MediaUploader;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    public HBox uploadImage;
    public HBox uploadVideo;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String postType;
        Image profileImage = PersonalAccount.getInstance().getProfileImage();
        profilePicture.setImage(profileImage);
        profileName.setText(PersonalAccount.getInstance().getFirstName()+ " " + PersonalAccount.getInstance().getLastName());
        postProfilePicture.setImage(profileImage);
        List<Post> posts = HomePageDAO.getPosts();
        List<Offer> offers = HomePageDAO.getJobOffers();
        try {
            for (Post post : posts) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                System.out.println(Arrays.toString(post.getImage()).equals("null"));
                if (Arrays.toString(post.getImage()).equals("null")) postType = "imagelessPost";
                else postType = "post";
                System.out.println(postType);
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/"+postType+".fxml"));
                VBox vBox=fxmlLoader.load();
                System.out.println("im here");

                PostController postController=fxmlLoader.getController();
                postController.setData(post);
                postContainer.getChildren().add(vBox);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        /*try {
            for (Offer offer : offers) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/offer.fxml"));
                VBox vBox=fxmlLoader.load();
                OfferController offerController=fxmlLoader.getController();
                offerController.setData(offer);
                offerContainer.getChildren().add(vBox);
            }
        }catch (IOException e){
            e.printStackTrace();
        }*/


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
        if (FieldVerifier.isValid(postField)){
            createPost.setCaption(postField.getText());
            postField.setText("");
            createPostImage = null;
            createPostVideo = null;
            createPost.setAudience(PostAudience.PUBLIC);
            if (createPostImage!=null) createPost.setImage(MediaConverter.convertFileToByteArray(createPostImage));
            //TODO add video uploading
            System.out.println(createPost);
            HomePageDAO.createPost(createPost);
        }
    }

    public void onUploadImage() throws IOException {
        createPostImage = MediaUploader.getMediaAsFile(uploadImage);
        System.out.println("got the image "+createPostImage.getPath());
        createPost.setImage(MediaConverter.convertFileToByteArray(createPostImage));
    }

    public void onUploadVideo(MouseEvent mouseEvent) {
        createPostVideo = MediaUploader.getMediaAsFile(uploadVideo);

    }
}
