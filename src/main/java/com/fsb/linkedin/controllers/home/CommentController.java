package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.*;
import com.fsb.linkedin.entities.Account;
import com.fsb.linkedin.entities.Comment;
import com.fsb.linkedin.entities.Notification;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CommentController {
    public HBox otherProfile;
    public HBox commentContainer;
    public ImageView profilePicture;
    public Text caption;
    public Button reply;
    public VBox profile;
    public Label like;
    public Label replyButton;
    public Label likes;
    public ImageView image;
    private boolean isLiked;
    private int likeCount;
    @FXML
    private ImageView imgProfile;
    private int commentID;
    private int parentID;
    private String likedColor = "cyan";
    public Comment commentWithImage;

    @FXML
    private ImageView imgComment;
    @FXML
    private Label username;

    @FXML
    private ImageView imgVerified;

    @FXML
    private Label date;
    private Account author;
    @FXML
    private ImageView audience;

    public void goToProfile() throws IOException {
        OtherAccountDAO.loadUser(commentWithImage.getAccount().getID());
        SceneSwitcher.goTo(getClass(),"profile",otherProfile);
    }
    public void setData(Comment comment){
        if (image != null){
            image.setImage(MediaConverter.getImage(comment.getImage()));
        }
        username.setText(comment.getAccount().getName());
        date.setText(comment.getDate());
        profilePicture.setImage(MediaConverter.getImage(comment.getAccount().getProfileImg()));
        caption.setText(comment.getCaption());
        isLiked = comment.isLiked();
        commentID = comment.getCommentID();
        likeCount = comment.getLikeCount();
        likes.setText(String.valueOf(comment.getLikeCount()));
        author = comment.getAccount();
        if (isLiked){
            like.setStyle("-fx-text-fill: #0078c4");
        }
        parentID = CommentSectionDAO.getParent_ID();
        System.out.println("Parent id is "+parentID);
    }

    public void onReply(MouseEvent event) throws IOException {
        CommentSectionDAO.setParent_id(commentID);
        SceneSwitcher.openNewWindow(getClass(),"commentSection","Reply To "+author.getName()+": "+caption.getText(),"priority");
    }

    public void onProfile(MouseEvent mouseEvent) {
    }

    public void onLike(MouseEvent mouseEvent) {
        CommentSectionDAO.setParent_id(this.parentID);
        if (!isLiked) {
            likes.setText(String.valueOf(Integer.parseInt(likes.getText()) + 1));
            like.setStyle("-fx-text-fill: #0078c4");
            CommentDAO.addLike(commentID);
            if (AccountDAO.loadUserID() != author.getID())
                NotificationDAO.createNotification(new Notification("CommentLike", AccountDAO.loadUserID(), author.getID(), commentID,caption.getText()));
            isLiked = true;
        }else {
            likes.setText(String.valueOf(Integer.parseInt(likes.getText()) - 1));
            like.setStyle("-fx-text-fill: black");
            CommentDAO.removeLike(commentID);
            if (AccountDAO.loadUserID() != author.getID())
                NotificationDAO.removeNotification(AccountDAO.loadUserID(),author.getID(),commentID,"CommentLike");
            isLiked = false;
        }
    }

    public void onHover(MouseEvent mouseEvent) {

    }

    public void onHoverExit(MouseEvent mouseEvent) {

    }


}
