package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.DAO.HomePageDAO;
import com.fsb.linkedin.DAO.OtherAccountDAO;
import com.fsb.linkedin.entities.OtherAccount;
import com.fsb.linkedin.utils.ComboUtils;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FriendSearchController implements Initializable {
    public TextField recherche;
    public ComboBox<String> type;
    public Button search;
    public VBox resultContainer;
    public ComboBox<String> country;

    public void onSearch(ActionEvent event) {
        resultContainer.getChildren().removeAll(resultContainer.getChildren());
        String countyString = "";
        String typeString = "";
        String keyword = recherche.getText();
        if (country.getValue() != null){
            if (country.getValue().equals("Any")){
                countyString = "";
            }else{
                countyString = country.getValue();
            }
        }
        if (type.getValue() != null){
            if (type.getValue().equals("Enterprise")){
                typeString = "Enterprise";
            }else if (type.getValue().equals("Any")){
                typeString = "";
            }else {
                typeString = "Normal";
            }

        }

        try {
            for (int friendID : HomePageDAO.getAccounts(countyString,typeString,keyword)) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/friendComponent.fxml"));
                HBox vBox=fxmlLoader.load();
                vBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        OtherAccountDAO.loadUser(friendID);
                        try {
                            if (OtherAccount.getInstance().getType().equals("Enterprise"))
                                SceneSwitcher.openNewWindow(getClass(),"enterpriseprofile","profile");
                            else
                                SceneSwitcher.openNewWindow(getClass(),"profile","profile");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                FriendComponentController controller=fxmlLoader.getController();
                controller.setData(friendID);
                resultContainer.getChildren().add(vBox);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        type.getItems().addAll("Person", "Enterprise","Any");
        type.setPromptText("Any");
        country.getItems().addAll(ComboUtils.countries);
        country.getItems().addAll("Any");
        country.setPromptText("Any");

    }
}
