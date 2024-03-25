package com.fsb.linkedin.DAO;

import com.fsb.linkedin.entities.Experience;
import com.fsb.linkedin.entities.PersonalAccount;
import com.fsb.linkedin.entities.Project;
import com.fsb.linkedin.entities.Qualification;
import com.fsb.linkedin.utils.DataBaseConnection;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private static final Connection connection = DataBaseConnection.getInstance();
    public static int save(PersonalAccount account) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "INSERT INTO accounts (first_name, last_name, email, password, phone_number, date_of_birth, gender, country, profilePicture) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, account.getFirstName());
            pstmt.setString(2, account.getLastName());
            pstmt.setString(3, account.getEmail());
            pstmt.setString(4, account.getPassword());
            pstmt.setString(5, account.getPhoneNumber());
            pstmt.setDate(6, java.sql.Date.valueOf(account.getDateOfBirth()));
            pstmt.setString(7, account.getGender());
            pstmt.setString(8, account.getCountry());
            pstmt.setBytes(9, account.getProfilePicture());

            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }
    public static void saveQualifications(PersonalAccount account, int accountId) {
        if (accountId == -1) return;

        String sql = "INSERT INTO qualifications (account_id, diploma, title, institution, technology, start_date, finish_date, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Qualification qualification : account.getQualifications()) {
                pstmt.setInt(1, accountId);
                pstmt.setString(2, qualification.getDiploma());
                pstmt.setString(3, qualification.getTitle());
                pstmt.setString(4, qualification.getInstitution());
                pstmt.setString(5, qualification.getTechnology());
                pstmt.setDate(6, java.sql.Date.valueOf(qualification.getStartDate()));
                pstmt.setDate(7, java.sql.Date.valueOf(qualification.getFinishDate()));
                pstmt.setString(8, qualification.getDescription());

                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void saveProjects(PersonalAccount account, int accountId) {
        if (accountId == -1) return;

        String sql = "INSERT INTO projects (account_id, title, start_date, finish_date, description) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Project project : account.getProjects()) {
                pstmt.setInt(1, accountId);
                pstmt.setString(2, project.getTitle());
                pstmt.setDate(3, java.sql.Date.valueOf(project.getStartDate()));
                pstmt.setDate(4, java.sql.Date.valueOf(project.getFinishDate()));
                pstmt.setString(5, project.getDescription());

                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void saveExperiences(PersonalAccount account, int accountId) {
        if (accountId == -1) return;
        String sql = "INSERT INTO experiences (account_id, title, type, mission, technology, start_date, finish_date, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Experience experience : account.getExperiences()) {
                pstmt.setInt(1, accountId);
                pstmt.setString(2, experience.getTitle());
                pstmt.setString(3, experience.getType());
                pstmt.setString(4, experience.getMission());
                pstmt.setString(5, experience.getTechnology());
                pstmt.setDate(6, java.sql.Date.valueOf(experience.getStartDate()));
                pstmt.setDate(7, java.sql.Date.valueOf(experience.getFinishDate()));
                pstmt.setString(8, experience.getDescription());

                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void saveAccount(PersonalAccount p){
        int accountId = AccountDAO.save(p);
        if (accountId == -1) return;
        saveQualifications(p,accountId);
        saveExperiences(p,accountId);
        saveProjects(p,accountId);
    }
    public static boolean loginIsValid(String email, String pass) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE email = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, pass);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    public static void loadUser(String email){
        int id = loadUserID(email);
        retrievePersonalInfo(id);
        retrieveProjects(id);
        retrieveExperiences(id);
        retrieveQualifications(id);
    }
    private static int loadUserID(String email){
        String sql = "SELECT account_id FROM accounts WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }
    private static void retrieveProjects(int accountId) {
        if (accountId == -1) return;

        PersonalAccount account = PersonalAccount.getInstance();
        System.out.println(accountId);
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM projects WHERE account_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Project project = new Project();
                project.setTitle(rs.getString("title"));
                project.setStartDate(rs.getDate("start_date").toLocalDate());
                project.setFinishDate(rs.getDate("finish_date").toLocalDate());
                project.setDescription(rs.getString("description"));
                projects.add(project);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        account.setProjects(projects);
    }
    private static void retrievePersonalInfo(int accountId) {
        if (accountId == -1) return;

        PersonalAccount account = PersonalAccount.getInstance();
        String query = "SELECT * FROM accounts WHERE account_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                account.setFirstName(rs.getString("first_name"));
                account.setLastName(rs.getString("last_name"));
                account.setEmail(rs.getString("email"));
                account.setPhoneNumber(rs.getString("phone_number"));
                account.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                account.setGender(rs.getString("gender"));
                account.setCountry(rs.getString("country"));
                account.setProfilePicture(rs.getBytes("profilePicture"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static File writeByteArrayToFile(byte[] data) throws IOException {
        File file = File.createTempFile("profile", ".jpg"); // Adjust file extension as needed
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        }
        return file;
    }

    private static void retrieveQualifications(int accountId) {
        if (accountId == -1) return;

        PersonalAccount account = PersonalAccount.getInstance();

        List<Qualification> qualifications = new ArrayList<>();
        String query = "SELECT * FROM qualifications WHERE account_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Qualification qualification = new Qualification();
                qualification.setDiploma(rs.getString("diploma"));
                qualification.setTitle(rs.getString("title"));
                qualification.setInstitution(rs.getString("institution"));
                qualification.setTechnology(rs.getString("technology"));
                qualification.setStartDate(rs.getDate("start_date").toLocalDate());
                qualification.setFinishDate(rs.getDate("finish_date").toLocalDate());
                qualification.setDescription(rs.getString("description"));
                qualifications.add(qualification);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        account.setQualifications(qualifications);
    }

    private static void retrieveExperiences(int accountId) {
        if (accountId == -1) return;

        PersonalAccount account = PersonalAccount.getInstance();
        List<Experience> experiences = new ArrayList<>();
        String query = "SELECT * FROM experiences WHERE account_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Experience experience = new Experience();
                experience.setTitle(rs.getString("title"));
                experience.setType(rs.getString("type"));
                experience.setMission(rs.getString("mission"));
                experience.setTechnology(rs.getString("technology"));
                experience.setStartDate(rs.getDate("start_date").toLocalDate());
                experience.setFinishDate(rs.getDate("finish_date").toLocalDate());
                experience.setDescription(rs.getString("description"));
                experiences.add(experience);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        account.setExperiences(experiences);
    }
    public static boolean AttributeIsUnique(String attribute,String value){
        String sql = "SELECT COUNT(*) FROM accounts WHERE "+ attribute +"  = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1,value);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getInt(1) == 0;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
