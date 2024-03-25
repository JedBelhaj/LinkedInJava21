package com.fsb.linkedin.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DataBaseConnection {
    private static Connection connection;

    private final String DB_URL = "jdbc:mysql://localhost:3306/linkedin";
    private final String USER = "root";
    private final String PASS = "";

    private DataBaseConnection() throws SQLException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(DB_URL,USER,PASS);
    }
    public static Connection getInstance(){
        if (connection == null)
            try {
                new DataBaseConnection();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        return connection;
    }
}
