package com.fsb.linkedin.DAO;

import com.fsb.linkedin.entities.Account;
import com.fsb.linkedin.entities.Notification;
import com.fsb.linkedin.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentDAO {
    private static final Connection connection = DataBaseConnection.getInstance();
    public static void insertComment(int parentId, int postId, int authorId, String caption, byte[] attachment) {
        String insertSQL = "INSERT INTO Comments (parent_id, post_id, author_id, caption, attachment, comment_type) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            // Set parameters for the PreparedStatement
            preparedStatement.setInt(1, parentId);
            preparedStatement.setInt(2, postId);
            preparedStatement.setInt(3, authorId);
            preparedStatement.setString(4, caption);
            preparedStatement.setBytes(5, attachment);
            Notification notification = new Notification();

            if (CommentSectionDAO.getParent_ID() != -1) {
                preparedStatement.setString(6, "reply");
                notification.setType("CommentReply");
                notification.setDestination_id(getAuthorID(parentId));
                notification.setMessage(getParentCommentCaption(parentId));
            }
            else {
                preparedStatement.setString(6, "normal");
                notification.setType("Comment");
                notification.setDestination_id(PostDAO.getPostAuthorID(postId));
                notification.setMessage(PostDAO.getPostCaption(postId));
            }
            notification.setSource_id(AccountDAO.loadUserID());
            notification.setPost_id(postId);
            System.out.println(notification);
            preparedStatement.executeUpdate();
            NotificationDAO.createNotification(notification);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addLike(int commentID) {
        String sql = "UPDATE comments SET reactions_count = reactions_count + 1 WHERE comment_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, commentID);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error adding like: " + ex.getMessage());
        }
        sql = "INSERT INTO comment_likes (comment_id, account_id) VALUES (?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setInt(1, commentID);
            preparedStatement.setInt(2, AccountDAO.loadUserID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeLike(int commentID) {
        String sql = "UPDATE comments SET reactions_count = reactions_count - 1 WHERE comment_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, commentID);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error adding like: " + ex.getMessage());
        }
        sql = "DELETE FROM comment_likes WHERE comment_id = ? AND account_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, commentID);
            preparedStatement.setInt(2, AccountDAO.loadUserID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean likedByUser(int commentID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM comment_likes WHERE comment_id = ? AND account_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,commentID);
            preparedStatement.setInt(2,AccountDAO.loadUserID());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        }
        return false;
    }

    static public int getCommentCount(int post_id) {
        String sql = "SELECT COUNT(*) FROM comments WHERE post_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, post_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return  rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error checking if post is liked by user: " + ex.getMessage());
        }
        return -1;
    }
    public static int getAuthorID(int post_id){
        String sql = "SELECT author_id FROM comments WHERE comment_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, post_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return  rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error getting post author id: " + ex.getMessage());
        }
        return -1;
    }
    public static String getParentCommentCaption(int comment_id){
        String sql = "SELECT caption FROM comments WHERE comment_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, comment_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return  rs.getString(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error getting post author id: " + ex.getMessage());
        }
        return "";
    }

    public static void insertComment(int parentId, int postId, int authorId, String caption) {
        String insertSQL = "INSERT INTO Comments (parent_id, post_id, author_id, caption, comment_type) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            // Set parameters for the PreparedStatement
            preparedStatement.setInt(1, parentId);
            preparedStatement.setInt(2, postId);
            preparedStatement.setInt(3, authorId);
            preparedStatement.setString(4, caption);
            Notification notification = new Notification();

            if (CommentSectionDAO.getParent_ID() != -1) {
                preparedStatement.setString(5, "reply");
                notification.setType("CommentReply");
                notification.setDestination_id(getAuthorID(parentId));
                notification.setMessage(getParentCommentCaption(parentId));
            }
            else {
                preparedStatement.setString(5, "normal");
                notification.setType("Comment");
                notification.setDestination_id(PostDAO.getPostAuthorID(postId));
                notification.setMessage(PostDAO.getPostCaption(postId));
            }
            notification.setSource_id(AccountDAO.loadUserID());
            notification.setPost_id(postId);
            System.out.println(notification);
            preparedStatement.executeUpdate();
            NotificationDAO.createNotification(notification);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
