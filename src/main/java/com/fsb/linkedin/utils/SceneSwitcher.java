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
    private static final int MAX_STACK_SIZE = 10;
    private static Stack<Scene> previousStack = new Stack<>();
    private static Stack<Scene> nextStack = new Stack<>();

    public static void goTo(Class<?> c, String destination, Button button) throws IOException {
        if (nextStack.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(c.getResource(destination + ".fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) button.getScene().getWindow();
            if (!previousStack.isEmpty() && Objects.equals(scene, previousStack.peek())) {
                System.out.println("I ALREADY VISITED THIS!");
            }

            if (previousStack.size() >= MAX_STACK_SIZE) {
                previousStack.remove(0);
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