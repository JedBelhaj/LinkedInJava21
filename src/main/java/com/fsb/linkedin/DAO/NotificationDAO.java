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
    public static List<Notification> getNotifications() {
        List<Notification> notifications= new ArrayList<>();
        String sql = "SELECT a.account_id, a.first_name, a.last_name, a.profilePicture, a.is_verified, n.created_at, n.message, n.type, n.source_id " +
                "FROM notifications n " +
                "JOIN accounts a ON n.source_id = a.account_id " +
                "WHERE n.destination_id = "+ AccountDAO.loadUserID() + " "+
                "ORDER BY n.created_at DESC LIMIT 10"; // Assuming you want to limit to 10 notifications
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Notification notification = new Notification();
                notification.setFirstName(rs.getString("first_name"));
                notification.setProfilePicture(rs.getBytes("profilePicture"));
                notification.setDate(rs.getDate("created_at").toLocalDate());
                notification.setMessage(rs.getString("message"));
                notification.setSource_id(rs.getInt("source_id"));
                notification.setType(rs.getString("type"));
                notifications.add(notification);
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching reaction notifications: " + ex.getMessage());
        }
        return notifications;
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
    public static void removeNotification(int sourceID, int destinationID, String type){
        switch (type){
            case ("Reaction"):
                removeReactionNotification(sourceID,destinationID);
                break;
            case ("Request"):
                removeRequestNotification(sourceID,destinationID);
                break;
            default:
                break;
        }
    }
    public static void removeRequestNotification(int sourceID,int destinationID){
        String sql = "DELETE FROM notifications WHERE source_id = ? AND destination_id = ? and type = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, sourceID);
            pstmt.setInt(2, destinationID);
            pstmt.setString(3, "Request");
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
    public static void removeAcceptNotifications(int userID){
        String sql = "DELETE FROM notifications WHERE destination_id = ? and type = 'Accept'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
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
