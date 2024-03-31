package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.ChatRoomDAO;
import com.fsb.linkedin.DAO.OtherAccountDAO;
import com.fsb.linkedin.entities.*;
import com.fsb.linkedin.utils.FieldVerifier;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.MediaUploader;
import com.fsb.linkedin.utils.SceneSwitcher;
import com.fsb.linkedin.DAO.MessageDAO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChatRoomController implements Initializable {

    public ScrollPane scrollPane;
    private File createMessageImage;
    private File createMessageVideo;
    private Message createMessage = new Message();
    private static int recieverID = -1;


    public VBox contactcontainer;
    public VBox messagecontainer;
    public Button send;
    public TextField userMessage;
    public ImageView userPic;
    public HBox uploadVideo;
    public ImageView imgProfile;
    public Label username;
    public Label email;
    public Label phoneNumber;
    public HBox uploadImage;
    @FXML
    private Button moreinfo;

    private int currentConvID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userPic.setImage(MediaConverter.getImage(PersonalAccount.getInstance().getProfilePicture()));
        email.setText("");
        phoneNumber.setText("");
        List<Contact> contacts= ChatRoomDAO.getContacts();
        try {
            for (Contact contact : contacts) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/contact.fxml"));
                VBox vBox=fxmlLoader.load();
                vBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        OtherAccountDAO.loadUser(recieverID);
                        recieverID = contact.getID();
                        messagecontainer.getChildren().removeAll(messagecontainer.getChildren());
                        currentConvID = contact.getConvID();
                        System.out.println("current convID "+currentConvID);
                        loadConversation();
                    }
                });
                ContactController contactController=fxmlLoader.getController();
                contactController.setContact(contact);
                contactcontainer.getChildren().add(vBox);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void moreinfo() throws IOException {
        if (recieverID!=-1) {
            OtherAccountDAO.loadUser(recieverID);
            if (OtherAccount.getInstance().getType().equals("Enterprise")){
                SceneSwitcher.goTo(getClass(), "enterpriseprofile", moreinfo);

            }else
                SceneSwitcher.goTo(getClass(), "profile", moreinfo);
        }
    }

    public void onSend() {
        if (FieldVerifier.isValid(userMessage)) {
            createMessage.setCaption(userMessage.getText());
            userMessage.setText("");
            MessageDAO.sendMessage(createMessage, recieverID);
            System.out.println("Message sent to " + recieverID + " : " + createMessage.getCaption());
            loadConversation();
        }
    }

    public void onUploadImage() throws IOException {
        createMessageImage = MediaUploader.getMediaAsFile(uploadImage);
        System.out.println("got the image "+createMessageImage.getPath());
        createMessage.setAttachment(MediaConverter.convertFileToByteArray(createMessageImage));
    }

    public void onUploadVideo(MouseEvent mouseEvent) {
        createMessageVideo = MediaUploader.getMediaAsFile(uploadVideo);

    }
    public void loadConversation(){
        OtherAccountDAO.loadUser(recieverID);
        imgProfile.setImage(MediaConverter.getImage(OtherAccount.getInstance().getProfilePicture()));
        username.setText(OtherAccount.getInstance().getFirstName()+" "+OtherAccount.getInstance().getLastName());
        email.setText(OtherAccount.getInstance().getEmail());
        phoneNumber.setText(OtherAccount.getInstance().getPhoneNumber());
        List<Message> messages = MessageDAO.loadConversation(currentConvID);
        messagecontainer.getChildren().removeAll(messagecontainer.getChildren());
        try {
            for (Message message : messages) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/message.fxml"));
                VBox hBox=fxmlLoader.load();
                MessageController messageController=fxmlLoader.getController();
                messageController.setData(message);
                messagecontainer.getChildren().add(hBox);
                scrollPane.vvalueProperty().bind(messagecontainer.heightProperty());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
