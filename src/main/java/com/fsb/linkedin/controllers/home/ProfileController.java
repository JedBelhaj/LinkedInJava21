package com.fsb.linkedin.controllers.home;

import com.fsb.linkedin.entities.Experience;
import com.fsb.linkedin.entities.OtherAccount;
import com.fsb.linkedin.entities.Project;
import com.fsb.linkedin.utils.MediaConverter;
import com.fsb.linkedin.utils.MediaUploader;
import com.fsb.linkedin.utils.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public Button profile;

    public Button home;
    public Button chatroom;
    public Button notifications;

    public Label username;
    public Label userlastname;
    public Label userphonenumber;
    public Label useremail;
    public Label userbirthdate;
    public Label experiencename;

    public VBox projectconainer;

    public VBox experiencecontainer;

    public Text experiencediscription;
    public ImageView userProfilePicture;
    public Label userFirstName;


    public void profile() throws IOException {
        SceneSwitcher.goTo(getClass(),"PersonalProfile",profile);

    }

    @Override
    public String toString() {
        return "ProfileController{" +
                "profile=" + profile +
                ", home=" + home +
                ", chatroom=" + chatroom +
                ", notifications=" + notifications +
                ", username=" + username +
                ", userlastname=" + userlastname +
                ", userphonenumber=" + userphonenumber +
                ", useremail=" + useremail +
                ", userbirthdate=" + userbirthdate +
                '}';
    }

    public void home() throws IOException {
        SceneSwitcher.goTo(getClass(),"homepage",home);

    }
    public void chatroom() throws IOException {
        SceneSwitcher.goTo(getClass(),"chatroom",chatroom);

    }
    public void notifications() throws IOException {
        SceneSwitcher.goTo(getClass(),"notifications",notifications);

    }

    public List<Experience> getExperience(){
        List<Experience> ls =new ArrayList<>();

        Experience experience;

        for(int i=0;i<10;i++){

            experience=new Experience("wwowoow","apgjapgnapghapighapghapgihvpahpatbabfpanprbairbapfbapfbbapbiabfpabfap", LocalDate.of(2019, 6, 1),LocalDate.of(2024, 6, 1),"Developed web applications using Spring Boot framework.","Full-time","Develop scalable and efficient software solutions.");
            System.out.println(experience.toString());
            ls.add(experience);
        }

        return ls;
    }
    public List<Project> getProject(){
        List<Project> ls =new ArrayList<>();

        Project project;

        for(int i=0;i<10;i++){

            project=new Project("wwowoow","apgjapgnapghapighapghapgihvpahpatbabfpanprbairbapfbapfbbapbiabfpabfap");
            System.out.println(project.toString());
            ls.add(project);
        }

        return ls;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OtherAccount p = OtherAccount.getInstance();
        username.setText(p.getFirstName() + " " + p.getLastName());
        userFirstName.setText(p.getFirstName());
        userlastname.setText(p.getLastName());
        userphonenumber.setText(p.getPhoneNumber());
        useremail.setText(p.getEmail());
        userbirthdate.setText(p.getDateOfBirth().toString());
        userProfilePicture.setImage(MediaConverter.getImage(OtherAccount.getInstance().getProfilePicture()));

        List<Project> projects= OtherAccount.getInstance().getProjects();
        List<Experience> experiences= OtherAccount.getInstance().getExperiences();
        System.out.println("x");
        try {
            for(Project project:projects){
                System.out.println("a");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/project.fxml"));
                VBox vBox=fxmlLoader.load();
                ProjectController projectController=fxmlLoader.getController();
                projectController.setproject(project);
                projectconainer.getChildren().add(vBox);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            for(Experience experience:experiences){
                System.out.println("a");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/fsb/linkedin/project.fxml"));
                VBox vBox=fxmlLoader.load();
                ProjectController projectController=fxmlLoader.getController();
                projectController.setproject(experience);
                experiencecontainer.getChildren().add(vBox);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
