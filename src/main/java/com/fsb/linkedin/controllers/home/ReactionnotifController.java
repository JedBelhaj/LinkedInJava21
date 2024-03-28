package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.*;
import com.fsb.linkedin.entities.Reactionnotif;
import com.fsb.linkedin.utils.MediaConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ReactionnotifController {
    public Button profile;
    public Text captionLabel;
    @FXML
    private ImageView imgProfile;
    @FXML
    private Label username;
    @FXML
    private Label date;
    private Reactionnotif reactionnotif;

    public void setData( Reactionnotif reactionnotif){
        this.reactionnotif=reactionnotif;
        Image img;
        img = MediaConverter.getImage(reactionnotif.getAccount().getProfileImg());
        imgProfile.setImage(img);
        username.setText(reactionnotif.getAccount().getName());
        date.setText(reactionnotif.getDate());
        captionLabel.setText(captionLabel.getText() + " \"" + reactionnotif.getCaption()+"\".");

    }


}
