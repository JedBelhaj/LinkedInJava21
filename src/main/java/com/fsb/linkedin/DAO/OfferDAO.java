package com.fsb.linkedin.DAO;

import com.fsb.linkedin.entities.*;
import com.fsb.linkedin.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfferDAO {
    private static final Connection connection = DataBaseConnection.getInstance();

    public static void insertApplication(int offer_id){
        String sql = "INSERT INTO offer_applications (applicant_id, offer_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, AccountDAO.loadUserID());
            preparedStatement.setInt(2, offer_id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeApplication(int offer_id){
    String sql = "DELETE FROM offer_applications WHERE applicant_id = ? AND offer_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, AccountDAO.loadUserID());
            preparedStatement.setInt(2, offer_id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Application removed successfully.");
            } else {
                System.out.println("No Application found to remove.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean applied(int offer_id){
        String sql = "SELECT COUNT(*) FROM offer_applications WHERE applicant_id = ? AND offer_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, AccountDAO.loadUserID());
            preparedStatement.setInt(2, offer_id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return  rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static List<Post> getOffers(int account_id, String type) {
        List<Post> offers = new ArrayList<>();
        String sql = "SELECT p.*, a.first_name, a.profilePicture, a.is_verified " +
                "FROM posts p " +
                "JOIN accounts a ON p.account_id = a.account_id " +
                "WHERE p.account_id = ? AND p.post_type = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, account_id);
            preparedStatement.setString(2, type);

            ResultSet rs = preparedStatement.executeQuery();
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
                account.setName(rs.getString("first_name"));
                account.setProfileImg(rs.getBytes("profilePicture"));
                account.setVerified(rs.getBoolean("is_verified"));

                post.setAccount(account);
                offers.add(post);
                System.out.println(post.getCaption()+ " "+ post.getAccount().getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return offers;
    }

}
