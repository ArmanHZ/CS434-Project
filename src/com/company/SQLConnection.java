package com.company;

import java.sql.*;

public class SQLConnection {

    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    protected void connectionTest() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = ("jdbc:mysql://localhost:3306/cs434?user=root&password=armanADMIN123");
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connected");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not connect to DB");
        }
        closeConnection();
    }

    private void closeConnection() {
        try {
            connection.close();
            System.out.println("Disconnected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
