package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.entities.Post;
import com.fsb.linkedin.entities.PostAudience;
import com.fsb.linkedin.entities.Reactions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;

public class PostController{
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
    private Post post;

    public void commentsection() throws IOException {
        System.out.println("it is clicking");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("comment.fxml"));
        Parent root = loader.load();

        // Create a new stage for the comment scene
        Stage commentStage = new Stage();
        commentStage.setTitle("Comment");
        commentStage.setScene(new Scene(root));

        // Show the comment stage
        commentStage.show();    }
    @FXML
    public void onReactionImgPressed(MouseEvent me){
        if(currentReaction==Reactions.LIKE){
            setReaction(Reactions.NON);
        }else{
            setReaction(Reactions.LIKE);


    }}

    public void setReaction(Reactions reaction){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(reaction.getImgSrc())));
        imgReaction.setImage(image);
        reactionName.setText(reaction.getName());
        reactionName.setTextFill(Color.web(reaction.getColor()));

        if(currentReaction == Reactions.NON){
            post.setTotalReactions(post.getTotalReactions() + 1);
        }

        currentReaction = reaction;

        if(currentReaction == Reactions.NON){
            post.setTotalReactions(post.getTotalReactions() - 1);
        }

        nbReactions.setText(String.valueOf(post.getTotalReactions()));
    }
    public void setData(Post post){
        this.post = post;
        Image img;
        img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(post.getAccount().getProfileImg())));
        imgProfile.setImage(img);
        username.setText(post.getAccount().getName());
        if(post.getAccount().isVerified()){
            imgVerified.setVisible(true);
        }else{
            imgVerified.setVisible(false);
        }

        date.setText(post.getDate());
        if(post.getAudience() == PostAudience.PUBLIC){
            img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PostAudience.PUBLIC.getImgSrc())));
        }else{
            img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PostAudience.FRIENDS.getImgSrc())));
        }
        audience.setImage(img);

        if(post.getCaption() != null && !post.getCaption().isEmpty()){
            caption.setText(post.getCaption());
        }else{
            caption.setManaged(false);
        }

        if(post.getImage() != null && !post.getImage().isEmpty()){
            img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(post.getImage())));
            imgPost.setImage(img);
        }else{
            imgPost.setVisible(false);
            imgPost.setManaged(false);
        }

        nbReactions.setText(String.valueOf(post.getTotalReactions()));
        nbComments.setText(post.getNbComments() + " comments");
        nbShares.setText(post.getNbShares()+" shares");

        currentReaction = Reactions.NON;
    }



    }

