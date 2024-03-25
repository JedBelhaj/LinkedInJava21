package com.fsb.linkedin.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

public class SceneSwitcher {
    /*TODO add a functionality that saves the previous next page
       meaning if u do next(save) and back(load) and next (load)*/
    private static final int MAX_STACK_SIZE = 10;
    private static Stack<Scene> sceneStack = new Stack<>();

    public static void goTo(Class<?> c, String destination, Button button) throws IOException {
        FXMLLoader loader = new FXMLLoader(c.getResource(destination + ".fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        Stage stage = (Stage) button.getScene().getWindow();
        if (!sceneStack.isEmpty() && Objects.equals(scene, sceneStack.peek())){
            System.out.println("I ALREADY VISITED THIS!");
        }

        if (sceneStack.size() >= MAX_STACK_SIZE) {
            sceneStack.remove(0);
        }

        sceneStack.push(stage.getScene());
        stage.setScene(scene);
        stage.show();
    }

    public static void previous(Button button) {
        if (!sceneStack.isEmpty()) {
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(sceneStack.pop());
        }
    }

    public static void openNewWindow(Class<?> c, String destination, String priority) throws IOException {
        FXMLLoader loader = new FXMLLoader(c.getResource(destination + ".fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        Stage newStage = new Stage();
        newStage.setScene(scene);

        // Set the modality of the new window if specified
        if (priority.equals("priority")) {
            newStage.initModality(Modality.APPLICATION_MODAL);
        }

        newStage.show();
    }
    public static void openNewWindow(Class<?> c, String destination) throws IOException {
        FXMLLoader loader = new FXMLLoader(c.getResource(destination + ".fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        Stage newStage = new Stage();
        newStage.setScene(scene);

        newStage.show();
    }
    public static void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}