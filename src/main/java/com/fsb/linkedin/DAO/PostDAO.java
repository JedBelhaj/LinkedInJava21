package com.fsb.linkedin.DAO;

import com.fsb.linkedin.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostDAO {
    private static final Connection connection = DataBaseConnection.getInstance();

    public static void addLike(int postID) {
        String sql = "UPDATE posts SET total_reactions = total_reactions + 1 WHERE post_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, postID);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error adding like: " + ex.getMessage());
        }
    }
    public static void removeLike(int postID) {
        String sql = "UPDATE posts SET total_reactions = total_reactions - 1 WHERE post_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, postID);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error removing like: " + ex.getMessage());
        }
    }

}
