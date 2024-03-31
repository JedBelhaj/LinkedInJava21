package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.*;
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
    private boolean applied = false;
    public HBox otherProfile;
    public HBox applyContainer;

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
    private int convID;
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

    private Reactions currentReaction;
    private Post post;

    public void commentsection() throws IOException {
        CommentSectionDAO.setIsReply(false);
        CommentSectionDAO.setPost_id(post.getPostID());
        CommentSectionDAO.setParent_id(-1);
        SceneSwitcher.openNewWindow(getClass(),"commentSection","Comments");
    }
    @FXML
    public void onReactionImgPressed(MouseEvent me){
        if(currentReaction==Reactions.LIKE){
            setReaction(Reactions.NON);
            NotificationDAO.removeReactionNotification(AccountDAO.loadUserID(), post.getPostID());
        }else{
            setReaction(Reactions.LIKE);
            NotificationDAO.createNotification(new Notification(post.getCaption(), "Reaction",AccountDAO.loadUserID(), post.getPostID(), post.getAccount().getID()));
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
        if (!Objects.equals(post.getPostType(), "Normal")){
            applyContainer.setPrefWidth(200);
            OfferApplication offerApplication = new OfferApplication();
            if (OfferDAO.applied(post.getPostID())){
                applied = true;
            }
            Button apply = new Button("Apply to "+post.getPostType());
            if (applied)
                apply.setText("Remove Application");
            Button visit = new Button("Visit Profile");
            apply.setOnMouseClicked(event -> {
                if (applied){
                    System.out.println("removing..");
                    OfferDAO.removeApplication(post.getPostID());
                    applied = false;
                    OfferDAO.insertApplication(post.getPostID());
                    apply.setText("Apply to "+post.getPostType());


                    OfferDAO.removeApplication(post.getPostID());
                    MessageDAO.removeConversation(convID);
                }else {
                    System.out.println("applying..");
                    OfferDAO.insertApplication(post.getPostID());
                    applied = true;
                    apply.setText("Remove Application");

                    convID = MessageDAO.createConversation(PostDAO.getPostAuthorID(post.getPostID()), post.getPostType()+" "+ post.getAccount().getName());
                    Message message = new Message();
                    message.setConvID(convID);
                    message.setSenderID(AccountDAO.loadUserID());
                    message.setCaption("Hey, I applied to your "+post.getPostType());
                    MessageDAO.sendMessage(message, convID);
                    try {
                        SceneSwitcher.openNewWindow(getClass(),"chatroom","Chatroom");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            visit.setOnMouseClicked(event -> {
                try {
                    OtherAccountDAO.loadUser(PostDAO.getPostAuthorID(post.getPostID()));
                    SceneSwitcher.goTo(getClass(),"enterpriseprofile",visit);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            applyContainer.getChildren().addAll(apply,visit);
        }
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
        if (OtherAccount.getInstance().getType().equals("Enterprise")){
            SceneSwitcher.goTo(getClass(),"enterpriseprofile",otherProfile);
        }
        else {
            SceneSwitcher.goTo(getClass(),"profile",otherProfile);
        }
    }
}

