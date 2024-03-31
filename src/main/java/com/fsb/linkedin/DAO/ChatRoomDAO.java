package com.fsb.linkedin.DAO;

import com.fsb.linkedin.entities.Account;
import com.fsb.linkedin.entities.Contact;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomDAO {
    private static final Connection connection = DataBaseConnection.getInstance();

    public static List<Contact> getContacts() {
        int userID = AccountDAO.loadUserID(PersonalAccount.getInstance().getEmail());
        List<Contact> contacts = new ArrayList<>();

        String sql = "SELECT " +
                "m.conversation_id, " +
                "m.message as message, " +
                "c.name as conversation_name , " +
                "MAX(m.sent_at) AS date, " +
                "IF(c.user1_id = ?, c.user2_id, c.user1_id) AS friend_id, " +
                "a.first_name AS friend_name, " +
                "a.last_name AS friend_last_name, " +
                "a.profilePicture AS friend_profile_image, " +
                "a.is_verified AS friend_verification_status, " +
                "a.type AS friend_type " +
                "FROM " +
                "messages m " +
                "INNER JOIN " +
                "conversations c ON m.conversation_id = c.conversation_id " +
                "INNER JOIN " +
                "accounts a ON IF(c.user1_id = ?, c.user2_id, c.user1_id) = a.account_id " +
                "WHERE " +
                "c.user1_id = ? OR c.user2_id = ? " +
                "GROUP BY " +
                "m.conversation_id " +
                "ORDER BY date DESC;";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.setInt(2, userID);
            pstmt.setInt(3, userID);
            pstmt.setInt(4, userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                Contact contact = new Contact();
                if (!(rs.getString("conversation_name") == null)){
                    account.setName(rs.getString("conversation_name"));
                }
                else if (rs.getString("friend_type").equals("Enterprise")){
                    account.setName(rs.getString("friend_name")+" Corp.");
                }else {
                    account.setName(rs.getString("friend_name")+" "+rs.getString("friend_last_name"));
                }
                account.setProfileImg(rs.getBytes("friend_profile_image"));
                account.setVerified(rs.getBoolean("friend_verification_status"));
                contact.setAccount(account);
                contact.setType(rs.getString("friend_type"));
                contact.setDate(String.valueOf(rs.getDate("date")));
                contact.setMsg("");
                contact.setId(rs.getInt("friend_id"));
                contact.setConvID(rs.getInt("conversation_id"));
                contacts.add(contact);
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving contacts: " + ex.getMessage());
        }

        return contacts;
    }

}
