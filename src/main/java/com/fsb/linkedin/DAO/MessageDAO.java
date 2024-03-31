package com.fsb.linkedin.DAO;

import com.fsb.linkedin.entities.Message;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.utils.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    private static final Connection connection = DataBaseConnection.getInstance();

    public static void sendMessage(Message message, int receiverId) {
        int senderId = AccountDAO.loadUserID(PersonalAccount.getInstance().getEmail());

        String sql = "INSERT INTO messages (sender_id, conversation_id, message, sent_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, senderId);
            if (message.getConvID() == -1){
                pstmt.setInt(2, getConversationId(receiverId));
            }
            else {
                pstmt.setInt(2, message.getConvID());
            }
            pstmt.setString(3, message.getCaption());
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            System.out.println("Message sent successfully.");
        } catch (SQLException ex) {
            System.out.println("Error sending message: " + ex.getMessage());
        }
    }

    public static int getConversationId(int receiverId) {
        // Check if a conversation between the sender and receiver already exists
        String checkConversationSql = "SELECT conversation_id FROM conversations " +
                "WHERE (user1_id = ? AND user2_id = ?) OR (user1_id = ? AND user2_id = ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(checkConversationSql)) {
            pstmt.setInt(1, AccountDAO.loadUserID());
            pstmt.setInt(2, receiverId);
            pstmt.setInt(3, receiverId);
            pstmt.setInt(4, AccountDAO.loadUserID());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // If a conversation exists, return its ID
                return rs.getInt("conversation_id");
            }
        } catch (SQLException ex) {
            System.out.println("Error checking conversation: " + ex.getMessage());
        }

        // If no conversation exists, create a new conversation and return its ID
        String createConversationSql = "INSERT INTO conversations (user1_id, user2_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(createConversationSql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, AccountDAO.loadUserID());
            pstmt.setInt(2, receiverId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the ID of the newly created conversation
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error creating conversation: " + ex.getMessage());
        }

        // Return -1 if there was an error creating the conversation
        return -1;
    }

    public static List<Message> loadConversation(int conversationId) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE conversation_id = ? ORDER BY sent_at ASC";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, conversationId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Message message = new Message();
                message.setSenderID(rs.getInt("sender_id"));
                message.setCaption(rs.getString("message"));
                messages.add(message);
            }
        } catch (SQLException ex) {
            System.out.println("Error loading conversation: " + ex.getMessage());
        }
        return messages;
    }

    public static int createConversation(int party_id, String conversationName) {
        // Assuming you have a Connection object named 'connection' for database connection

        String sql = "INSERT INTO conversations (user1_id ,user2_id, name) VALUES (?, ?, ?)";

        try {
            // Create a PreparedStatement with an additional argument to return generated keys
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            // Set the values for the parameters
            statement.setInt(1, AccountDAO.loadUserID());
            statement.setInt(2, party_id);
            statement.setString(3, conversationName);

            // Execute the query
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                // Get the generated keys (in this case, just one key)
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    // Return the generated conversation ID
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            // Handle any errors
            e.printStackTrace();
        }

        // If no conversation ID was generated or an exception occurred, return -1 or handle accordingly
        return -1;
    }
    public static void removeConversation(int conversationId) {

        String sql = "DELETE FROM conversations WHERE conversation_id = ?";

        try {
            // Create a PreparedStatement
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set the conversation ID as parameter
            statement.setInt(1, conversationId);

            // Execute the query
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Conversation deleted successfully!");
            } else {
                System.out.println("No conversation found with ID: " + conversationId);
            }
        } catch (SQLException e) {
            // Handle any errors
            e.printStackTrace();
        }
    }
}
