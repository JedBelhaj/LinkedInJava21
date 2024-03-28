package com.fsb.linkedin.utils;

import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;

public class MediaConverter {
    public static byte[] convertFileToByteArray(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] fileData = new byte[(int) file.length()];
            fis.read(fileData);
            return fileData;
        }
    }
    public static InputStream convertByteArrayToInputStream(byte[] byteArray) {
        return new ByteArrayInputStream(byteArray);
    }
    public static File convertBlobToFile(Blob blob) throws SQLException, IOException {
        File file = File.createTempFile("temp", null);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            // Get the input stream from the BLOB
            InputStream inputStream = blob.getBinaryStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            // Write the data from the input stream to the file output stream
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return file;
    }
}
