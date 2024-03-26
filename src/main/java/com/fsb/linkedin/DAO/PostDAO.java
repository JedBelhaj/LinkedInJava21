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
        String sql = "UPDATE posts SET total_reactions = total_reactions + 1 WHERE post_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, postID);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error adding like: " + ex.getMessage());
        }
        sql = "INSERT INTO likes (post_id, account_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, postID);
            pstmt.setInt(2, AccountDAO.loadUserID(PersonalAccount.getInstance().getEmail()));
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
        sql = "DELETE FROM likes WHERE post_id = ? AND account_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, postID);
            pstmt.setInt(2, AccountDAO.loadUserID(PersonalAccount.getInstance().getEmail()));
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error removing like: " + ex.getMessage());
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
}
