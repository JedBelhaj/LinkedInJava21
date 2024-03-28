package com.fsb.linkedin.DAO;

import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostDAO {
    private static final Connection connection = DataBaseConnection.getInstance();

    public static void addLike(int postID) {
        //increase total likes
        String sql = "UPDATE posts SET total_reactions = total_reactions + 1 WHERE post_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, postID);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error adding like: " + ex.getMessage());
        }
        //save the like
        sql = "INSERT INTO likes (post_id, account_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, postID);
            pstmt.setInt(2, AccountDAO.loadUserID(PersonalAccount.getInstance().getEmail()));
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error adding like: " + ex.getMessage());
        }
        //generate notification to the author
        String notificationSql = "INSERT INTO notifications (post_id, reactor_id) VALUES (?, ?)";
        try (PreparedStatement notificationStmt = connection.prepareStatement(notificationSql)) {
            int reactorID = AccountDAO.loadUserID(PersonalAccount.getInstance().getEmail());
            notificationStmt.setInt(1, postID);
            notificationStmt.setInt(2, reactorID);
            notificationStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error adding notification: " + ex.getMessage());
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
        sql = "DELETE FROM likes WHERE post_id = ? AND account_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, postID);
            pstmt.setInt(2, AccountDAO.loadUserID(PersonalAccount.getInstance().getEmail()));
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error removing like: " + ex.getMessage());
        }
        sql = "DELETE FROM notifications WHERE post_id = ? AND reactor_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int reactorID = AccountDAO.loadUserID(PersonalAccount.getInstance().getEmail());
            pstmt.setInt(1, postID);
            pstmt.setInt(2, reactorID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Notification removed successfully.");
            } else {
                System.out.println("No notification found to remove.");
            }
        } catch (SQLException ex) {
            System.out.println("Error removing notification: " + ex.getMessage());
        }
    }
    public static boolean likedBy(String email, int post_id){
        int account_id = AccountDAO.loadUserID(email);
        if (account_id == -1) {
            System.out.println("Invalid email");
            return false;
        }

        String sql = "SELECT COUNT(*) FROM likes WHERE account_id = ? AND post_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, account_id);
            pstmt.setInt(2, post_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException ex) {
            System.out.println("Error checking if post is liked by user: " + ex.getMessage());
        }
        return false;
    }
    private static int getPostAuthorID(int postID) {
        String sql = "SELECT account_id FROM posts WHERE post_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, postID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("account_id");
                } else {
                    System.out.println("Post with ID " + postID + " not found.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error getting post author ID: " + ex.getMessage());
        }
        return -1; // Return -1 if there's an error or the post is not found
    }
}
