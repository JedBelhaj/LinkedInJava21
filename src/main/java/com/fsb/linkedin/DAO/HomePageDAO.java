package com.fsb.linkedin.DAO;

import com.fsb.linkedin.entities.Account;
import com.fsb.linkedin.entities.Offer;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.entities.Post;
import com.fsb.linkedin.utils.DataBaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HomePageDAO {

    private static final Connection connection = DataBaseConnection.getInstance();

    public static void createPost(Post post) {
        String sql = "INSERT INTO posts (account_id, date, audience, caption, image_url) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, AccountDAO.loadUserID(PersonalAccount.getInstance().getEmail()));
            pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(3, post.getAudience().getAudienceAsString());
            pstmt.setString(4, post.getCaption());
            pstmt.setBytes(5, post.getImage());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static List<Post> getPosts() {

        return null;
    }

    public static List<Offer> getJobOffers() {

        return null;
    }
}
