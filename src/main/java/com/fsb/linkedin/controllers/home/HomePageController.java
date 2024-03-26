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

    /*cho fy hathy hes el list mta3 el friends etdourhom ou etchouf elly 3anda post ethotha */
    public List<Post> getPostFriends(List<Account> l){
        List<Post> ls =new ArrayList<>();
        Post post;
        int counter=0;
        for(Account account:l){

            counter++;
            if(counter==20)
                break;
        }




        return ls;
    }

    public  List<Offer> getOffer(){
        List<Offer> ls =new ArrayList<>();

        Offer offer;

        for(int i =1;i<=10;i=i+2){

            offer=new Offer();
            Account account = new Account();
            account.setName(" facebook");
            account.setProfileImg("/imgs/user.png");
            account.setVerified(true);
            offer.setAccount(account);
            offer.setDate("Feb 18, 2021 at 12:00 PM");
            offer.setAudience(PostAudience.PUBLIC);
            offer.setCaption("we need workers .");
            offer.setImage("/imgs/img2.jpg");
            ls.add(offer);
            System.out.println("post nb "+i);
            System.out.println(account.toString());
            System.out.println(offer.toString());
            offer=new Offer();
            account = new Account();
            account.setName(" facebook");
            account.setProfileImg("/imgs/user.png");
            account.setVerified(true);
            offer.setAccount(account);
            offer.setDate("Feb 18, 2021 at 12:00 PM");
            offer.setAudience(PostAudience.PUBLIC);
            offer.setCaption("we need workers .");
            offer.setImage("/imgs/img2.jpg");
            ls.add(offer);
            System.out.println("post nb "+(i+1));


        }
        return ls;

    }

    public List<Post> getPost(){
        List<Post> ls =new ArrayList<>();

        Post post;

        for(int i =1;i<=10;i=i+2){

            post=new Post();
            Account account = new Account();
            account.setName(" jed");
            account.setProfileImg("/imgs/user.png");
            account.setVerified(true);
            post.setAccount(account);
            post.setDate("Feb 18, 2021 at 12:00 PM");
            post.setAudience(PostAudience.PUBLIC);
            post.setCaption("i like kids .");
            post.setTotalReactions(10);
            post.setNbComments(2);
            post.setNbShares(3);
            ls.add(post);
            System.out.println("post nb "+i);
            System.out.println(account.toString());
            System.out.println(post.toString());
            post=new Post();
            account = new Account();
            account.setName(" jasseur");
            account.setProfileImg("/imgs/user.png");
            account.setVerified(true);
            post.setAccount(account);
            post.setDate("Feb 18, 2021 at 12:00 PM");
            post.setAudience(PostAudience.PUBLIC);
            post.setCaption("so do i .");
            post.setTotalReactions(10);
            post.setNbComments(2);
            post.setNbShares(3);
            ls.add(post);
            System.out.println("post nb "+(i+1));


        }
        return ls;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image profileImage = PersonalAccount.getInstance().getProfileImage();
        profilePicture.setImage(profileImage);
        profileName.setText(PersonalAccount.getInstance().getFirstName()+ " " + PersonalAccount.getInstance().getLastName());
        postProfilePicture.setImage(profileImage);
        List<Post> posts = new ArrayList<>(getPost());
        List<Offer> offers = new ArrayList<>(getOffer());
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
        try {
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
        }


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
        //post_id 	account_id 	date 	audience 	caption 	image_url 	total_reactions 	nb_comments 	nb_shares
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
