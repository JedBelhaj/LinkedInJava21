package com.fsb.linkedin.DAO;

import com.fsb.linkedin.entities.Account;
import com.fsb.linkedin.entities.Friendrequest;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.utils.DataBaseConnection;
import com.fsb.linkedin.utils.MediaConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendRequestDAO {
    private static final Connection connection = DataBaseConnection.getInstance();
    private static final int userID = AccountDAO.loadUserID(PersonalAccount.getInstance().getEmail());

    public static void sendFriendRequest(int receiverID) {
        if (!isFriendRequestSent(receiverID)) {
            String sql = "INSERT INTO friend_requests (sender_id, receiver_id) VALUES (?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, userID);
                pstmt.setInt(2, receiverID);
                pstmt.executeUpdate();
                System.out.println("Friend request sent successfully.");
            } catch (SQLException ex) {
                System.out.println("Error sending friend request: " + ex.getMessage());
            }
        } else {
            System.out.println("Friend request already sent.");
        }
    }

    public static boolean isFriendRequestSent(int receiverID) {
        String sql = "SELECT * FROM friend_requests WHERE sender_id = ? AND receiver_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.setInt(2, receiverID);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // If there is a result, it means the request is already sent
            }
        } catch (SQLException ex) {
            System.out.println("Error checking friend request: " + ex.getMessage());
            return false; // Return false in case of an exception or error
        }
    }

    public static boolean isRequesting(int senderID) {
        String sql = "SELECT * FROM friend_requests WHERE sender_id = ? AND receiver_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, senderID);
            pstmt.setInt(2, userID);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // If there is a result, it means the sender sent a request to the current user
            }
        } catch (SQLException ex) {
            System.out.println("Error checking friend request: " + ex.getMessage());
            return false; // Return false in case of an exception or error
        }
    }

    public static void removeFriendRequest(int receiverID) {
        String sql = "DELETE FROM friend_requests WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.setInt(2, receiverID);
            pstmt.setInt(3, receiverID);
            pstmt.setInt(4, userID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Friend request removed successfully.");
            } else {
                System.out.println("No friend request found to remove.");
            }
        } catch (SQLException ex) {
            System.out.println("Error removing friend request: " + ex.getMessage());
        }
    }

    public static boolean areAlreadyFriends(int accountID2) {
        String sql = "SELECT COUNT(*) FROM friends WHERE (account_id1 = ? AND account_id2 = ?) OR (account_id1 = ? AND account_id2 = ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.setInt(2, accountID2);
            pstmt.setInt(3, accountID2);
            pstmt.setInt(4, userID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException ex) {
            System.out.println("Error checking friendship: " + ex.getMessage());
        }
        return false;
    }
    public static String getFriendRequestStatus(int otherUserID) {
        if (areAlreadyFriends(otherUserID)) {
            return "AlreadyFriends";
        } else if (isFriendRequestSent(otherUserID)) {
            return "UserAlreadyRequesting";
        } else if (isRequesting(otherUserID)) {
            return "OtherAlreadyRequesting";
        } else {
            return "None";
        }
    }

    public static void removeFriend(int receiverID) {
        String sql = "DELETE FROM friends WHERE (account_id1 = ? AND account_id2 = ?) OR (account_id1 = ? AND account_id2 = ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.setInt(2, receiverID);
            pstmt.setInt(3, receiverID);
            pstmt.setInt(4, userID);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Friend removed successfully.");
            } else {
                System.out.println("No friend found to remove.");
            }
        } catch (SQLException ex) {
            System.out.println("Error removing friend: " + ex.getMessage());
        }
    }
    public static void acceptFriendRequest(int senderID) {
        // Add sender as a friend
        addFriend(senderID);

        // Remove the friend request
        removeFriendRequest(senderID);
    }

    private static void addFriend(int friendID) {
        String sql = "INSERT INTO friends (account_id1, account_id2) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.setInt(2, friendID);
            pstmt.executeUpdate();
            System.out.println("Friend added successfully.");
        } catch (SQLException ex) {
            System.out.println("Error adding friend: " + ex.getMessage());
        }
    }
    public static List<Friendrequest> getFriendRequests() {
        List<Friendrequest> friendRequests = new ArrayList<>();

        String sql = "SELECT f.*, a.* FROM friend_requests f " +
                "JOIN accounts a ON f.sender_id = a.account_id " +
                "WHERE f.receiver_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, FriendRequestDAO.userID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Friendrequest friendRequest = new Friendrequest();
                    Account account = new Account();

                    // Populate Friendrequest object
                    friendRequest.setDate(rs.getString("created_at"));

                    // Populate Account object
                    account.setID(rs.getInt("account_id"));
                    account.setName(rs.getString("first_name") + " " + rs.getString("last_name"));
                    account.setProfileImg(rs.getBytes("profilePicture"));
                    // Set other properties of the Account object as needed

                    // Set Account object to Friendrequest
                    friendRequest.setAccount(account);

                    // Add Friendrequest to the list
                    friendRequests.add(friendRequest);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving friend requests: " + ex.getMessage());
        }

        return friendRequests;
    }
}
