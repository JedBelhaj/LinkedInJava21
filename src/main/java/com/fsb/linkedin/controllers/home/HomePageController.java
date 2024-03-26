package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.HomePageDAO;
import com.fsb.linkedin.entities.*;
import com.fsb.linkedin.utils.FieldVerifier;
import com.fsb.linkedin.utils.ImageConverter;
import com.fsb.linkedin.utils.ImageUploader;
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
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.PublicKey;
import java.util.ArrayList;
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
        Image profileImage = PersonalAccount.getInstance().getProfileImage();
        profilePicture.setImage(profileImage);
        profileName.setText(PersonalAccount.getInstance().getFirstName()+ " " + PersonalAccount.getInstance().getLastName());
        postProfilePicture.setImage(profileImage);
        List<Post> posts = HomePageDAO.getPosts();
        List<Offer> offers = HomePageDAO.getJobOffers();
        try {
            for (Post post : posts) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/post.fxml"));
                VBox vBox=fxmlLoader.load();
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

    public void getoffer() {
    }

    public void onPost() throws IOException {
        if (FieldVerifier.isValid(postField)){
            createPost.setCaption(postField.getText());
            createPost.setAudience(PostAudience.PUBLIC);
            if (createPostImage!=null) createPost.setImage(ImageConverter.convertFileToByteArray(createPostImage));
            //TODO add video uploading
            System.out.println(createPost);
            HomePageDAO.createPost(createPost);
        }
    }

    public void onUploadImage() throws IOException {
        createPostImage = ImageUploader.getImageAsFile(uploadImage);
        System.out.println("got the image "+createPostImage.getPath());
        createPost.setImage(ImageConverter.convertFileToByteArray(createPostImage));
    }

    public void onUploadVideo(MouseEvent mouseEvent) {
    }
}
