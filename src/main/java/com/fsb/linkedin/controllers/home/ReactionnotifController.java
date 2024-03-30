package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.*;
import com.fsb.linkedin.DAO.OtherAccountDAO;
import com.fsb.linkedin.entities.Notification;
import com.fsb.linkedin.entities.OtherAccount;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.entities.Reactionnotif;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class ReactionnotifController {
    public Button profile;
    public Text captionLabel;
    public VBox notificationContainer;
    @FXML
    private ImageView imgProfile;
    @FXML
    private Label username;
    @FXML
    private Label date;

    public void setData(Notification notification){
        Image img = MediaConverter.getImage(notification.getProfilePicture());
        imgProfile.setImage(img);
        username.setText(notification.getFirstName());
        date.setText(notification.getDate());
        switch (notification.getType()){
            case ("Reaction"):
                captionLabel.setText(captionLabel.getText() + " \"" + notification.getMessage()+"\".");
                break;
            case ("Request"):
                System.out.println("SOURCE ID : "+notification.getSource_id());
                OtherAccountDAO.loadUser(notification.getSource_id());
                System.out.println("is this null? "+OtherAccount.getInstance());
                if (OtherAccount.getInstance().getType().equals("Enterprise")){
                    captionLabel.setText(notification.getFirstName()+ " Corp. sent you a Follow Request");
                    notificationContainer.setOnMouseClicked(mouseEvent -> {
                        try {
                            OtherAccountDAO.loadUser(notification.getSource_id());
                            SceneSwitcher.goTo(getClass(),"enterpriseprofile",notificationContainer);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } else if (PersonalAccount.getInstance().getType().equals("Enterprise")) {
                    captionLabel.setText(notification.getFirstName()+ " sent you a Follow Request");
                    notificationContainer.setOnMouseClicked(mouseEvent -> {
                        try {
                            OtherAccountDAO.loadUser(notification.getSource_id());
                            SceneSwitcher.goTo(getClass(),"profile",notificationContainer);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } else {
                    OtherAccountDAO.loadUser(notification.getSource_id());
                    System.out.println(OtherAccount.getInstance().getFirstName()+" IM GOING HERE");
                    captionLabel.setText(notification.getFirstName()+ " sent you a Friend Request");
                    notificationContainer.setOnMouseClicked(mouseEvent -> {
                        try {
                            SceneSwitcher.goTo(getClass(),"profile",notificationContainer);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                break;
            case ("Accept"):
                OtherAccountDAO.loadUser(notification.getSource_id());
                if (OtherAccount.getInstance().getType().equals("Enterprise")){
                    captionLabel.setText(notification.getFirstName()+ " Corp. accepted your Follow Request");
                }
                else {
                    captionLabel.setText(notification.getFirstName()+ " accepted your Friend Request");
                }
                break;
            case ("CommentLike"):
                OtherAccountDAO.loadUser(notification.getSource_id());
                if (OtherAccount.getInstance().getType().equals("Enterprise")){
                    captionLabel.setText(notification.getFirstName()+ " Corp. liked your comment \""+ notification.getMessage()+"\"");
                }
                else {
                    captionLabel.setText(notification.getFirstName()+ " liked your comment \""+ notification.getMessage()+"\"");
                }
                break;
            case ("CommentReply"):
                OtherAccountDAO.loadUser(notification.getSource_id());
                if (OtherAccount.getInstance().getType().equals("Enterprise")){
                    captionLabel.setText(notification.getFirstName()+ " Corp. replied to your comment \""+ notification.getMessage()+"\"");
                }
                else {
                    captionLabel.setText(notification.getFirstName()+ " replied to your comment \""+ notification.getMessage()+"\"");
                }
                break;
            case ("Comment"):
                OtherAccountDAO.loadUser(notification.getSource_id());
                if (OtherAccount.getInstance().getType().equals("Enterprise")){
                    captionLabel.setText(notification.getFirstName()+ " Corp. commented on your post \""+ notification.getMessage()+"\"");
                }
                else {
                    captionLabel.setText(notification.getFirstName()+ " commented on your post \""+ notification.getMessage()+"\"");
                }
                break;
            default:
                break;
        }
    }


}
