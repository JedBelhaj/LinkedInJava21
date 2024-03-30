package com.fsb.linkedin.DAO;

import com.fsb.linkedin.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommentDAO {
    private static Connection connection = DataBaseConnection.getInstance();
    public static void insertComment(int parentId, int postId, int authorId, String caption, boolean isReply) {
        String insertSQL = "INSERT INTO Comments (parent_id, post_id, author_id, caption, comment_type) VALUES (?, ?, ?, ?, ?)";

        try {
            // Establish a connection
            // Create a PreparedStatement object to send parameterized SQL statement to the database
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            // Set parameters for the PreparedStatement
            preparedStatement.setInt(1, parentId);
            preparedStatement.setInt(2, postId);
            preparedStatement.setInt(3, authorId);
            preparedStatement.setString(4, caption);
            if (isReply)
                preparedStatement.setString(5, "reply");
            else
                preparedStatement.setString(5, "normal");

            // Execute the PreparedStatement to insert the comment
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
