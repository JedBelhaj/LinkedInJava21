package com.fsb.linkedin.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class VideoConverter {

    public static Media byteToMedia(byte[] videoData) throws IOException, MediaException {
        // Create a temporary file to store the byte array
        File tempFile = createTempFile(videoData);

        // Create a Media object from the temporary file
        Media media = new Media(tempFile.toURI().toString());

        return media;
    }

    private static File createTempFile(byte[] data) throws IOException {
        File tempFile = File.createTempFile("temp", ".mp4"); // Change file extension as per your video format
        tempFile.deleteOnExit();
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(data);
        }
        return tempFile;
    }
}
