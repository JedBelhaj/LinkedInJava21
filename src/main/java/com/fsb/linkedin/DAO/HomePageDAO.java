package com.fsb.linkedin.DAO;

import com.fsb.linkedin.entities.*;
import com.fsb.linkedin.utils.DataBaseConnection;
import com.fsb.linkedin.utils.SceneSwitcher;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HomePageDAO {

    private static final Connection connection = DataBaseConnection.getInstance();

    public static void createPost(Post post) {
        String sql = "INSERT INTO posts (account_id, date, audience, caption, image_url, post_type) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, AccountDAO.loadUserID(PersonalAccount.getInstance().getEmail()));
            pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(3, post.getAudience().getAudienceAsString());
            pstmt.setString(4, post.getCaption());
            pstmt.setBytes(5, post.getImage());
            pstmt.setString(6, post.getPostType());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT DISTINCT " +
                "p.*, " +
                "a.first_name AS account_name, " +
                "a.profilePicture AS account_profile_img, " +
                "a.is_verified AS account_verified " +
                "FROM " +
                "posts p " +
                "INNER JOIN " +
                "accounts a ON p.account_id = a.account_id " +
                "LEFT JOIN " +
                "friends f ON (a.account_id = f.account_id1 OR a.account_id = f.account_id2) " +
                "WHERE " +
                "(f.account_id1 = ? OR f.account_id2 = ? OR a.account_id = ?) " +
                "ORDER BY " +
                "p.date DESC " +
                "LIMIT 10;";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int userId = AccountDAO.loadUserID();
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
                post.setNbComments(CommentDAO.getCommentCount(post.getPostID()));
                post.setNbShares(rs.getInt("nb_shares"));
                post.setPostType(rs.getString("post_type"));

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


    public static List<Post> getJobOffers() {
        List<Post> jobOffers = new ArrayList<>();
        String sql = "SELECT DISTINCT " +
                "p.*, " +
                "a.first_name AS account_name, " +
                "a.profilePicture AS account_profile_img, " +
                "a.is_verified AS account_verified " +
                "FROM " +
                "posts p " +
                "INNER JOIN " +
                "accounts a ON p.account_id = a.account_id " +
                "LEFT JOIN " +
                "friends f ON (a.account_id = f.account_id1 OR a.account_id = f.account_id2) " +
                "WHERE " +
                "(f.account_id1 = ? OR f.account_id2 = ? OR a.account_id = ?) " +
                "AND p.post_type = 'Job Offer' " +
                "ORDER BY " +
                "p.date DESC " +
                "LIMIT 10;";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int userId = AccountDAO.loadUserID();
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
                post.setNbComments(CommentDAO.getCommentCount(post.getPostID()));
                post.setNbShares(rs.getInt("nb_shares"));
                post.setPostType(rs.getString("post_type"));

                Account account = new Account();
                account.setID(rs.getInt("account_id"));
                account.setName(rs.getString("account_name"));
                account.setProfileImg(rs.getBytes("account_profile_img"));
                account.setVerified(rs.getBoolean("account_verified"));

                post.setAccount(account);
                jobOffers.add(post);
                System.out.println(post.getCaption()+ " "+ post.getAccount().getName());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return jobOffers;
    }

    public static List<Integer> getFriends() throws SQLException {
        List<Integer> friends = new ArrayList<>();
        String sql = "SELECT account_id, type from accounts";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("account_id");
                if (FriendRequestDAO.areAlreadyFriends(id) && !rs.getString("type").equals("BANNED")){
                    friends.add(id);
                }
            }
        }
        return friends;
    }

    public static List<Integer> getAccounts(String country, String type, String keyword) throws SQLException {
        List<Integer> friends = new ArrayList<>();
        String sql = "SELECT account_id, type " +
                "FROM accounts " +
                "WHERE (country = ? OR ? = '') " +
                "  AND (type = ? OR ? = '') " +
                "  AND (CONCAT(first_name, ' ', last_name) LIKE ? OR ? = '');";


        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, country);
            preparedStatement.setString(2, country);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, type);
            preparedStatement.setString(5, "%" + keyword + "%");
            preparedStatement.setString(6, "%" + keyword + "%");

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("account_id");
                // Assuming AccountDAO.loadUserID() returns the current user's ID
                if (id != AccountDAO.loadUserID() && !rs.getString("type").equals("BANNED")) {
                    friends.add(id);
                }
            }
        } catch (SQLException e) {
            // Error handling: print or log any exceptions that occur
            e.printStackTrace();
        }
        return friends;
    }


}
