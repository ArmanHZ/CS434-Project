package com.company;

import java.sql.*;

public class SQLConnection {

    private static Connection CONNECTION = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    void connect() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = ("jdbc:mysql://localhost:3306/cs434?user=root&password=armanADMIN123");
        try {
            CONNECTION = DriverManager.getConnection(url);
            System.out.println("Connected");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not connect to DB");
        }
    }

    private void closeConnection() {
        try {
            CONNECTION.close();
            System.out.println("Disconnected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TODO To be tested
    private void registerStudent(String name, String userName, String password, String email, String department) {
        String query = "INSERT INTO students VALUES (sName, sUsername, sPassword, sEmail, sDepartment)" +
                " VALUES (" + name + ", " + userName + ", " + password + ", " + email + ", " + department + ")";
        try {
            this.statement = CONNECTION.createStatement();
            this.statement.executeUpdate(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
