package com.fsb.linkedin.controllers.signup;

import com.fsb.linkedin.DAO.AccountDAO;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.utils.FieldVerifier;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;

public class SignUpVideo {

    public Button playpause;
    public Button reset;
    public Button upload;
    public Button back;
    public Button done;
    public MediaView video;
    private MediaPlayer mediaPlayer;
    private boolean playMedia = false;
    @FXML protected void onPlayPause(){
        if (!playMedia){
            mediaPlayer.play();
            playMedia = true;
            playpause.setText("Pause");
        }
        else {
            mediaPlayer.pause();
            playMedia=false;
            playpause.setText("Play");
        }
    }

    @FXML protected void onReset(){
        mediaPlayer.seek(Duration.seconds(0.0));
    }

    @FXML protected void onUpload(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Video");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")+ "/Desktop"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mp4 video","*.mp4"));
        File videoCV = fileChooser.showOpenDialog(upload.getScene().getWindow());
        if (videoCV !=null){
            Media media = new Media(videoCV.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            video.setMediaPlayer(mediaPlayer);
        }
    }

    @FXML protected void onBack(){
        SceneSwitcher.previous(back);
        mediaPlayer.pause();
    }

    @FXML protected void  onNext(){
        if (FieldVerifier.isValid(upload,n -> mediaPlayer!=null)){
            mediaPlayer.pause();
            PersonalAccount p = PersonalAccount.getInstance();
            //save video
            PersonalAccount.setInstance(p);
            AccountDAO.saveAccount(p);
        }
    }
}
