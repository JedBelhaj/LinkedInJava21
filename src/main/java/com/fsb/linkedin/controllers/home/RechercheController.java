package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.HomePageDAO;
import com.fsb.linkedin.DAO.RechercheDAO;
import com.fsb.linkedin.entities.Offer;
import com.fsb.linkedin.entities.Post;
import com.fsb.linkedin.utils.FieldVerifier;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class RechercheController implements Initializable {

    public TextField recherche;
    public ComboBox<String> type;
    public Button search;
    public VBox offersContainer;

    public void onSearch(ActionEvent event) {
        offersContainer.getChildren().removeAll(offersContainer.getChildren());
        String keywords = recherche.getText();
        String offerType = type.getValue();
        List<Post> posts = RechercheDAO.findPosts(keywords,offerType);
        try {
            for (Post post : posts) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                System.out.println(Arrays.toString(post.getImage()).equals("null"));
                String postTypeFXML;
                if (Arrays.toString(post.getImage()).equals("null")) postTypeFXML = "imagelessPost";
                else postTypeFXML = "post";
                System.out.println(postTypeFXML);
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/"+postTypeFXML+".fxml"));
                VBox vBox=fxmlLoader.load();
                System.out.println("im here");

                PostController postController=fxmlLoader.getController();
                postController.setData(post);
                offersContainer.getChildren().add(vBox);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        type.getItems().addAll("Internship Offer", "Job Offer","Any");
        type.setPromptText("Any");

    }
}
