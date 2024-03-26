package com.fsb.linkedin.utils;

import com.fsb.linkedin.MainApplication;
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
    public static boolean saveHistory = true;
    private static final int MAX_STACK_SIZE = 10;
    private static final Stack<Scene> previousStack = new Stack<>();
    private static final Stack<Scene> nextStack = new Stack<>();

    public static void setSaveHistory(boolean on){
        saveHistory = on;
    }

    public static void goTo(Class<?> c, String destination, Button button) throws IOException {
        if (nextStack.isEmpty() || !saveHistory) {
            FXMLLoader loader = new FXMLLoader(c.getResource("/com/fsb/linkedin/"+destination + ".fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) button.getScene().getWindow();

            if (previousStack.size() >= MAX_STACK_SIZE) {
                previousStack.removeFirst();
            }

            previousStack.push(stage.getScene());
            stage.setScene(scene);
            stage.show();
        } else {
            Stage stage = (Stage) button.getScene().getWindow();
            previousStack.push(button.getScene());
            stage.setScene(nextStack.pop());
        }
    }

    public static void previous(Button button) {
        if (!previousStack.isEmpty()) {
            Stage stage = (Stage) button.getScene().getWindow();
            nextStack.push(button.getScene());
            stage.setScene(previousStack.pop());
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