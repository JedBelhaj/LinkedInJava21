package com.fsb.linkedin;

import com.fsb.linkedin.entities.Account;
import com.fsb.linkedin.entities.Offer;
import com.fsb.linkedin.entities.Post;
import com.fsb.linkedin.entities.PostAudience;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    @FXML
    public Button profile;

    @FXML
    public Button home;
    @FXML
    public Button chatroom;
    @FXML
    public Button notifications;

    @FXML
    private VBox postcontainer;
    @FXML
    private VBox offercontainer;

    /*cho fy hathy hes el list mta3 el friends etdourhom ou etchouf elly 3anda post ethotha */
    public List<Post> getpostfriends(List<Account> l){
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

    public  List<Offer> getoffer(){
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

    public List<Post> getpost(){
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
            post.setImage("/imgs/img2.jpg");
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
            post.setImage("/imgs/img2.jpg");
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
        List<Post> posts = new ArrayList<>(getpost());
        List<Offer> offers = new ArrayList<>(getoffer());
        try {
            for (Post post : posts) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/test/post.fxml"));
                VBox vBox=fxmlLoader.load();
                PostController postController=fxmlLoader.getController();
                postController.setData(post);
                postcontainer.getChildren().add(vBox);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            for (Offer offer : offers) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/test/offer.fxml"));
                VBox vBox=fxmlLoader.load();
                OfferController offerController=fxmlLoader.getController();
                offerController.setData(offer);
                offercontainer.getChildren().add(vBox);
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
}
