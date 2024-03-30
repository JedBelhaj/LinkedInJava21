package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.AccountDAO;
import com.fsb.linkedin.DAO.CommentDAO;
import com.fsb.linkedin.DAO.CommentSectionDAO;
import com.fsb.linkedin.entities.Account;
import com.fsb.linkedin.entities.Comment;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.utils.FieldVerifier;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.MediaUploader;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CommentSectionController implements Initializable {
    public VBox inputContainer;
    public ImageView profilePicture;
    public TextField commentCaption;
    public Button send;
    public Button attachment;
    public VBox commentsContainer;
    private File image;
    public void onSend(ActionEvent event) throws IOException {
        if (FieldVerifier.isValid(commentCaption) || image!=null){
            FieldVerifier.isValid(commentCaption, n-> true);
            CommentDAO.insertComment(CommentSectionDAO.getParent_ID(), CommentSectionDAO.getPost_id(), AccountDAO.loadUserID(), commentCaption.getText(), CommentSectionDAO.isIsReply());
            commentCaption.setText("");
            image = null;
            SceneSwitcher.goTo(getClass(),"commentSection",send);
        }
    }

    public void onAttachment(ActionEvent event) {
        image = MediaUploader.getMediaAsFile(attachment);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        profilePicture.setImage(MediaConverter.getImage(PersonalAccount.getInstance().getProfilePicture()));
        try {
            for (Comment comment : CommentSectionDAO.getComments()){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/comment.fxml"));
                HBox vBox=fxmlLoader.load();
                CommentController commentController =fxmlLoader.getController();
                commentController.setData(comment);
                commentsContainer.getChildren().add(vBox);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
