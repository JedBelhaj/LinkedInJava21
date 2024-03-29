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

        String sql = "SELECT DISTINCT " +
                "    a.first_name AS friend_name, " +
                "    a.last_name AS friend_last_name ," +
                "    a.profilePicture AS friend_profile_image, " +
                "    a.is_verified AS friend_verification_status ," +
                "    a.account_id AS friend_id ," +
                "    a.type AS friend_type " +
                "FROM " +
                "    friends f " +
                "JOIN " +
                "    accounts a ON (f.account_id1 = a.account_id OR f.account_id2 = a.account_id) " +
                "WHERE " +
                "    (f.account_id1 = ? OR f.account_id2 = ?) " +
                "    AND a.account_id <> ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.setInt(2, userID);
            pstmt.setInt(3, userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                Contact contact = new Contact();
                if (rs.getString("friend_type").equals("Enterprise")){
                    account.setName(rs.getString("friend_name")+" Corp.");
                }else {
                    account.setName(rs.getString("friend_name")+" "+rs.getString("friend_last_name"));
                }
                account.setProfileImg(rs.getBytes("friend_profile_image"));
                account.setVerified(rs.getBoolean("friend_verification_status"));
                contact.setAccount(account);
                contact.setType(rs.getString("friend_type"));
                contact.setDate("date");
                contact.setMsg("message");
                contact.setId(rs.getInt("friend_id"));
                contacts.add(contact);
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving contacts: " + ex.getMessage());
        }

        return contacts;
    }

}
