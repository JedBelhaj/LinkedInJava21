package com.fsb.linkedin.DAO;

import com.fsb.linkedin.entities.Account;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.entities.Reactionnotif;
import com.fsb.linkedin.entities.Reactions;
import com.fsb.linkedin.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    private static final Connection connection = DataBaseConnection.getInstance();

    public static List<Reactionnotif> getReactionNotifications() {
        int accountId = AccountDAO.loadUserID(PersonalAccount.getInstance().getEmail());
        List<Reactionnotif> notifications = new ArrayList<>();
        String query = "SELECT n.*, a.first_name, a.last_name, a.profilePicture, a.is_verified, p.caption " +
                "FROM notifications n " +
                "JOIN posts p ON p.post_id = n.post_id " +
                "JOIN accounts a ON n.reactor_id = a.account_id " +
                "WHERE p.account_id = ? " +
                "ORDER BY n.notification_date DESC " +
                "LIMIT 10";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getString("first_name")+" "+rs.getString("last_name"));
                    Reactionnotif notification = new Reactionnotif();
                    Account account = new Account();
                    account.setName(rs.getString("first_name")+" "+rs.getString("last_name"));
                    account.setProfileImg(rs.getBytes("profilePicture"));
                    account.setVerified(rs.getBoolean("is_verified"));
                    Reactionnotif reactionnotif = new Reactionnotif();
                    reactionnotif.setAccount(account);
                    reactionnotif.setDate(String.valueOf(rs.getDate("notification_date")));
                    reactionnotif.setCaption(rs.getString("caption"));
                    notifications.add(reactionnotif);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error fetching reaction notifications: " + ex.getMessage());
        }
        return notifications;
    }
}
