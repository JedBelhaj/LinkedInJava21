package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.*;
import com.fsb.linkedin.entities.Offer;
import com.fsb.linkedin.entities.Reactions;
import com.fsb.linkedin.utils.MediaConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class OfferController {
    @FXML
    private Button offerbutton;

    @FXML
    private ImageView imgProfile;

    @FXML
    private Label username;

    @FXML
    private ImageView imgVerified;

    @FXML
    private Label date;

    @FXML
    private ImageView audience;

    @FXML
    private Label caption;

    @FXML
    private ImageView imgPost;

    @FXML
    private Label nbReactions;

    @FXML
    private Label nbComments;

    @FXML
    private Label nbShares;

    @FXML
    private HBox reactionsContainer;

    @FXML
    private Button commentcontainer;
    @FXML
    private HBox likeContainer;

    @FXML
    private ImageView imgReaction;

    @FXML
    private Label reactionName;

    private long startTime = 0;
    private Reactions currentReaction;
    private Offer offer;


    public void setData(Offer offer){
        this.offer = offer;
        Image img;
        username.setText(offer.getAccount().getName());
        if(offer.getAccount().isVerified()){
            imgVerified.setVisible(true);
        }else{
            imgVerified.setVisible(false);
        }

        date.setText(offer.getDate());




        if(offer.getImage() != null){
        }else{
            imgPost.setVisible(false);
            imgPost.setManaged(false);
        }
    }
    public void offerbutton() throws IOException {
        System.out.println("it is clicking");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("commentSection.fxml"));
        Parent root = loader.load();

        // Create a new stage for the comment scene
        Stage commentStage = new Stage();
        commentStage.setTitle("Comment");
        commentStage.setScene(new Scene(root));

        // Show the comment stage
        commentStage.show();    }


}
