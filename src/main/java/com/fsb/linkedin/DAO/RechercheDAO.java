package com.fsb.linkedin.DAO;

import com.fsb.linkedin.entities.Account;
import com.fsb.linkedin.entities.Post;
import com.fsb.linkedin.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RechercheDAO {
    private static final Connection connection = DataBaseConnection.getInstance();

    public static List<Post> findPosts(String keywords, String type) {
        List<Post> posts = new ArrayList<>();
        String othertype = "Job Offer";
        if (Objects.equals(type, "Any") || type == null)
            type = "Internship Offer";
        else
            othertype = type;
        // Prepare the SQL query
        String sql = "SELECT p.*, a.first_name, a.profilePicture, a.is_verified " +
                "FROM posts p " +
                "INNER JOIN accounts a ON p.account_id = a.account_id " +
                "WHERE p.caption LIKE ? AND (p.post_type = ? OR p.post_type = ?)";
        System.out.println(type + ' ' +  othertype+ ' ' +keywords);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set parameters
            statement.setString(1, "%" + keywords + "%");
            statement.setString(2, othertype);
            statement.setString(3, type);

            // Execute query
            ResultSet resultSet = statement.executeQuery();

            // Process results
            while (resultSet.next()) {
                Post post = new Post();

                post.setPostID(resultSet.getInt("post_id"));
                post.setDate(resultSet.getString("date"));
                post.setCaption(resultSet.getString("caption"));
                post.setImage(resultSet.getBytes("image_url"));
                post.setTotalReactions(resultSet.getInt("total_reactions"));
                post.setNbComments(CommentDAO.getCommentCount(post.getPostID()));
                post.setNbShares(resultSet.getInt("nb_shares"));
                post.setPostType(resultSet.getString("post_type"));

                Account account = new Account();
                account.setID(resultSet.getInt("account_id"));
                account.setName(resultSet.getString("first_name"));
                account.setProfileImg(resultSet.getBytes("profilePicture"));
                account.setVerified(resultSet.getBoolean("is_verified"));

                post.setAccount(account);
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }
}
