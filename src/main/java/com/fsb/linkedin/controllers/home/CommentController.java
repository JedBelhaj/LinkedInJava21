package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.OtherAccountDAO;
import com.fsb.linkedin.entities.Comment;
import com.fsb.linkedin.entities.PostAudience;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

public class CommentController {
    public HBox otherProfile;
    public HBox commentContainer;
    public ImageView profilePicture;
    public Text caption;
    public Button reply;

    @FXML
    private ImageView imgProfile;

    public Comment commentWithImage;

    @FXML
    private ImageView imgComment;
    @FXML
    private Label username;

    @FXML
    private ImageView imgVerified;

    @FXML
    private Label date;

    @FXML
    private ImageView audience;

    public void goToProfile() throws IOException {
        OtherAccountDAO.loadUser(commentWithImage.getAccount().getID());
        SceneSwitcher.goTo(getClass(),"profile",otherProfile);
    }
    public void setData(Comment comment){
        username.setText(comment.getAccount().getName());
        date.setText(comment.getDate());
        profilePicture.setImage(MediaConverter.getImage(comment.getAccount().getProfileImg()));
        caption.setText(comment.getCaption());
    }

    public void onReply(ActionEvent event) {
    }
}
