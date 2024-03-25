package com.fsb.linkedin.utils;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class FieldVerifier {
    public static String webRegex = "\\b(?:https?://)?(?:www\\.)?[a-zA-Z0-9-]+(?:\\.[a-zA-Z]{2,})+\\b";
    public static String phoneNumberRegex = "^\\d+$";
    public static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    public static boolean isValidField(String value, String regex) {
        if (regex == null && !value.isBlank()) return true;
        else if (regex!=null) return value.matches(regex);
        return false;
    }
    @FXML
    public static boolean areValid(TextInputControl... fields) {
        Stream.of(fields).forEach(f ->
                f.setStyle(f.getText().isBlank() ? "-fx-border-color: red" : "-fx-border-color: grey")
        );
        return Stream.of(fields).noneMatch(f -> f.getText().isBlank());
    }
    @FXML
    public static void updateFieldColor(TextInputControl field, String regex) {
        String value = field.getText();
        if (value.isBlank() || (regex != null && !isValidField(value, regex))) {
            field.setStyle("-fx-border-color: red");
        } else {
            field.setStyle("-fx-border-color: grey");
        }
    }
    @FXML
    public static boolean isValid(Node node, Predicate<Node> test){
        if (test.test(node)) {
            node.setStyle("-fx-border-color: grey");
            return true;
        }
        else node.setStyle("-fx-border-color: red");
        return false;
    }
    public static boolean isValid(TextField node){
        if (!node.getText().isEmpty()){
            node.setStyle("-fx-border-color: grey");
            return true;
        }else{
            node.setStyle("-fx-border-color: red");
            return false;
        }

    }
    @FXML
    public static boolean emailIsValid(TextField email){
        return isValid(email,node -> ((TextField)node).getText().matches(EMAIL_REGEX));
    }
    public static boolean phoneIsValid(TextField phoneNumber){
        return isValid(phoneNumber,node -> ((TextField)node).getText().matches(phoneNumberRegex));
    }
    public static boolean dateIsValid(DatePicker date){
        return isValid(date,node -> ((DatePicker)node).getValue() != null);
    }
    public static boolean choiceBoxIsValid(ComboBox<?> choice){
        return FieldVerifier.isValid(choice,node -> ((ComboBox<?>)node).getValue() != null);
    }
    public static boolean URLisValid(TextField URL){
        return FieldVerifier.isValid(URL,node -> ((TextField)node).getText().matches(webRegex));
    }
}

