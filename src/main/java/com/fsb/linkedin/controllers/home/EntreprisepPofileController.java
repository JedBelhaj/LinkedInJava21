package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.*;
import com.fsb.linkedin.entities.Account;
import com.fsb.linkedin.entities.Offer;
import com.fsb.linkedin.entities.PostAudience;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.MediaUploader;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EntreprisepPofileController implements Initializable {
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
    public List<Offer> getJoboffer(){
        List<Offer> ls =new ArrayList<>();
        Offer offer;
        for(int i=0;i<10;i++){

            offer=new Offer();
            Account account = new Account();
            account.setName(" jed");
            account.setProfileImg("/imgs/user.png".getBytes());
            account.setVerified(true);
            offer.setAccount(account);
            offer.setDate("Feb 18, 2021 at 12:00 PM");
            offer.setAudience(PostAudience.PUBLIC);
            offer.setCaption("i like kids .");

            ls.add(offer);
        }

        return ls;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Offer> offers =new ArrayList<>(getJoboffer());
        try {
            for(Offer offer:offers){
                System.out.println("a");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/offer.fxml"));
                VBox hBox=fxmlLoader.load();
                OfferController offerController=fxmlLoader.getController();
                offerController.setData(offer);
                joboffercontainer.getChildren().add(hBox);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            for (Offer offer : offers) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/offer.fxml"));
                VBox hBox = fxmlLoader.load();
                OfferController offerController = fxmlLoader.getController();
                offerController.setData(offer);
                stagecontainer.getChildren().add(hBox);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
