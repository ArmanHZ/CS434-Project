package com.company;

import javax.swing.*;
import java.sql.*;

public class SQLConnection {

    private static Connection CONNECTION = null;
    private static final String STUDENT_USERNAME_COLUMN_LABEL = "sUsername";
    private static final String INSTRUCTOR_USERNAME_COLUMN_LABEL = "iUsername";

    SQLConnection() {
        connect();
//        disconnect();
    }

    private void connect() {

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

    private void disconnect() {
        try {
            CONNECTION.close();
            System.out.println("Disconnected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerStudent(String name, String username, String password, String email, String department) {
        String query = "INSERT INTO students (sName, sUsername, sPassword, sEmail, sDepartment)" +
                " VALUES ('" + name + "', '" + username + "', '" + password + "', '" + email + "', '" + department + "');";
        try {
            if (!doesUserExist(username, STUDENT_USERNAME_COLUMN_LABEL)) {
                Statement statement = CONNECTION.createStatement();
                statement.executeUpdate(query);
                System.out.println("Student added successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private boolean doesUserExist(String username, String columnLabel) {
        String query = "";
        if (columnLabel.equals("sUsername"))
            query = "SELECT S.sUsername FROM students S;";
        else if (columnLabel.equals("iUsername"))
            query = "SELECT I.iUsername FROM instructors I;";
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String currentName = resultSet.getString(columnLabel);
                if (currentName.equals(username))
                    return true;
            }
        } catch (SQLException exception) {
            exception.getMessage();
        }

        return false;
    }

}
