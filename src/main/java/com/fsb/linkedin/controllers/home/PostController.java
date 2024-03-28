package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.AccountDAO;
import com.fsb.linkedin.DAO.OtherAccountDAO;
import com.fsb.linkedin.DAO.PostDAO;
import com.fsb.linkedin.entities.*;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;


import java.io.IOException;
import java.util.Objects;

public class PostController{
    public HBox otherProfile;

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
        SceneSwitcher.openNewWindow(getClass(),"comment","Comments");
    }
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
            PostDAO.addLike(post.getPostID());
        }

        currentReaction = reaction;

        if(currentReaction == Reactions.NON){
            post.setTotalReactions(post.getTotalReactions() - 1);
            PostDAO.removeLike(post.getPostID());
        }

        nbReactions.setText(String.valueOf(post.getTotalReactions()));
    }
    public void setData(Post post){
        this.post = post;
        Image img = new Image(MediaConverter.convertByteArrayToInputStream(post.getAccount().getProfileImg()));
        imgProfile.setImage(img);
        if (imgPost == null){
            System.out.println("imageless post");
        }else {
            imgPost.setImage(new Image(MediaConverter.convertByteArrayToInputStream(post.getImage())));
        }
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


        nbReactions.setText(String.valueOf(post.getTotalReactions()));
        nbComments.setText(post.getNbComments() + " comments");
        nbShares.setText(post.getNbShares()+" shares");

        if (PostDAO.likedBy(PersonalAccount.getInstance().getEmail(), post.getPostID()))
            currentReaction = Reactions.LIKE;
        else
            currentReaction = Reactions.NON;
        setReaction(currentReaction);
    }


    public void goToProfile() throws IOException {
        OtherAccountDAO.loadUser(post.getAccount().getID());
        SceneSwitcher.goTo(getClass(),"profile",otherProfile);
    }
}

