package com.fsb.linkedin.DAO;

import com.fsb.linkedin.entities.*;
import com.fsb.linkedin.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    private static final Connection connection = DataBaseConnection.getInstance();

    public static void createNotification(Notification notification) {
        String sql = "INSERT INTO notifications (source_id, message, type, post_id, destination_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, notification.getSource_id());
            pstmt.setString(2, notification.getMessage());
            pstmt.setString(3, notification.getType());
            pstmt.setInt(4, notification.getPost_id());
            pstmt.setInt(5, notification.getDestination_id());
            pstmt.executeUpdate();
            System.out.println("Notification created successfully.");
        } catch (SQLException ex) {
            System.out.println("Error creating notification: " + ex.getMessage());
        }
    }
    public static List<Reactionnotif> getReactionNotifications() {
        List<Reactionnotif> reactionNotifications = new ArrayList<>();
        String sql = "SELECT a.account_id, a.first_name, a.last_name, a.profilePicture, a.is_verified, n.created_at, n.message " +
                "FROM notifications n " +
                "JOIN accounts a ON n.source_id = a.account_id " +
                "WHERE n.type = 'reaction' AND n.destination_id = "+ AccountDAO.loadUserID() + " "+
                "ORDER BY n.created_at DESC LIMIT 10"; // Assuming you want to limit to 10 notifications
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setID(rs.getInt("account_id"));
                account.setName(rs.getString("first_name"));
                account.setProfileImg(rs.getBytes("profilePicture"));
                account.setVerified(rs.getBoolean("is_verified"));

                Reactionnotif reactionnotif = new Reactionnotif();
                reactionnotif.setCaption(rs.getString("message"));
                reactionnotif.setAccount(account);
                reactionnotif.setDate(rs.getString("created_at"));

                reactionNotifications.add(reactionnotif);
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching reaction notifications: " + ex.getMessage());
        }
        return reactionNotifications;
    }
    public static void removeReactionNotification(int sourceID, int postID) {
        String sql = "DELETE FROM notifications WHERE source_id = ? AND post_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, sourceID);
            pstmt.setInt(2, postID);
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
}
