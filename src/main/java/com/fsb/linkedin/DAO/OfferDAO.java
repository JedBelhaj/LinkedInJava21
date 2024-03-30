package com.fsb.linkedin.DAO;

import com.fsb.linkedin.entities.Notification;
import com.fsb.linkedin.entities.OfferApplication;
import com.fsb.linkedin.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
