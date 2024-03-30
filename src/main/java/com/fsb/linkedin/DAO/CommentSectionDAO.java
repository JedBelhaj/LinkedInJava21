package com.fsb.linkedin.DAO;

import com.fsb.linkedin.entities.Account;
import com.fsb.linkedin.entities.Comment;
import com.fsb.linkedin.entities.OtherAccount;
import com.fsb.linkedin.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommentSectionDAO {
    private static int post_id;

    public static int getPost_id() {
        return post_id;
    }

    public static void setPost_id(int post_id) {
        CommentSectionDAO.post_id = post_id;
    }

    private static int parent_id;
    private static boolean isReply = false;


    public static boolean isIsReply() {
        return isReply;
    }

    public static void setIsReply(boolean isReply) {
        CommentSectionDAO.isReply = isReply;
    }

    static public void setParent_id(int parent_id) {
        CommentSectionDAO.parent_id = parent_id;
    }

    public static int getParent_ID() {
        return parent_id;
    }

    public static List<Comment> getComments() throws SQLException {
        Connection connection = DataBaseConnection.getInstance();
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE post_id = ? AND parent_id = ? ORDER BY created_at DESC LIMIT 30 ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, post_id);
            if (parent_id == -1)
                preparedStatement.setInt(2, post_id);
            else
                preparedStatement.setInt(2, parent_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                OtherAccountDAO.loadUser(rs.getInt("author_id"));
                account.setID(rs.getInt("author_id"));
                account.setProfileImg(OtherAccount.getInstance().getProfilePicture());
                account.setName(OtherAccount.getInstance().getFirstName());
                Comment comment = new Comment();
                comment.setAccount(account);
                comment.setCaption(rs.getString("caption"));
                comment.setDate(String.valueOf(rs.getDate("created_at")));
                comment.setCommentID(rs.getInt("comment_id"));
                comment.setLikeCount(rs.getInt("reactions_count"));
                comment.setLiked(CommentDAO.likedByUser(comment.getCommentID()));
                comment.setImage(rs.getBytes("attachment"));
                comments.add(comment);
            }
        }
        connection = DataBaseConnection.getInstance();
        return comments;
    }
}
