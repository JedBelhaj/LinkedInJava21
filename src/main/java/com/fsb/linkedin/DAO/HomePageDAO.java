package com.fsb.linkedin.DAO;

import com.fsb.linkedin.entities.*;
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
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT p.*, a.first_name AS account_name, " +
                "a.profilePicture AS account_profile_img, " +
                "a.is_verified AS account_verified " +
                "FROM posts p " +
                "INNER JOIN accounts a ON p.account_id = a.account_id " +
                "INNER JOIN friends f ON (a.account_id = f.account_id1 OR a.account_id = f.account_id2) " +
                "WHERE (f.account_id1 = ? OR f.account_id2 = ?) " +
                "AND (a.account_id != ?) " +
                "ORDER BY p.date DESC";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int userId = AccountDAO.loadUserID(PersonalAccount.getInstance().getEmail());
            pstmt.setInt(1, userId);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();

                post.setPostID(rs.getInt("post_id"));
                post.setDate(rs.getString("date"));
                post.setCaption(rs.getString("caption"));
                post.setImage(rs.getBytes("image_url"));
                post.setTotalReactions(rs.getInt("total_reactions"));
                post.setNbComments(rs.getInt("nb_comments"));
                post.setNbShares(rs.getInt("nb_shares"));

                Account account = new Account();
                account.setID(rs.getInt("account_id"));
                account.setName(rs.getString("account_name"));
                account.setProfileImg(rs.getBytes("account_profile_img"));
                account.setVerified(rs.getBoolean("account_verified"));

                post.setAccount(account);
                posts.add(post);
                System.out.println(post.getCaption()+ " "+ post.getAccount().getName());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return posts;
    }


    public static List<Offer> getJobOffers() {
        List<Offer> jobOffers = new ArrayList<>();
        return jobOffers;
    }
}
