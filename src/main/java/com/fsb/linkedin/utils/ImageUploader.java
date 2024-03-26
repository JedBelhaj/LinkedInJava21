package com.fsb.linkedin.utils;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class ImageUploader {
    public static File getImageAsFile(Node uploadImg){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")+ "/Desktop"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG Image","*.jpg"), new FileChooser.ExtensionFilter("PNG Image", "*.png"), new FileChooser.ExtensionFilter("All image files","*.jpg","*.png"));
        return fileChooser.showOpenDialog(uploadImg.getScene().getWindow());
    }
    public static Image getImage(File imageFile){
        return new Image(imageFile.toURI().toString());
    }
    public static byte[] getImageAsByteArray(File imageFile) throws IOException {
        return ImageConverter.convertFileToByteArray(imageFile);
    }
}
