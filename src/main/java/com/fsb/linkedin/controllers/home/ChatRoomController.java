package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.AccountDAO;
import com.fsb.linkedin.DAO.ChatRoomDAO;
import com.fsb.linkedin.DAO.OtherAccountDAO;
import com.fsb.linkedin.entities.*;
import com.fsb.linkedin.utils.FieldVerifier;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.MediaUploader;
import com.fsb.linkedin.utils.SceneSwitcher;
import com.fsb.linkedin.DAO.MessageDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

    public List<Contact> getContact(){
        List<Contact> ls =new ArrayList<>();

        Contact contact;

        for(int i =1;i<=10;i++){

            contact=new Contact();
            Account account = new Account();
            account.setName(" jed");
            account.setProfileImg("/imgs/user.png".getBytes());
            account.setVerified(true);
            contact.setAccount(account);
            contact.setMsg("wa el 7ob");
            contact.setDate("Feb 18, 2021 at 12:00 PM");
            ls.add(contact);
            System.out.println(contact.toString());
        }
        return ls;
    }
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
        List<Message> messages = MessageDAO.loadConversation(MessageDAO.getConversationId(recieverID, AccountDAO.loadUserID(PersonalAccount.getInstance().getEmail())));
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
