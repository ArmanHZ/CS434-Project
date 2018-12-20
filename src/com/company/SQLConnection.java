package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class SQLConnection {

    private static SQLConnection SQL_CONNECTION = null;
    private static Connection CONNECTION ;
    private static final String STUDENT_USERNAME_COLUMN_LABEL = "sUsername";
    private static final String INSTRUCTOR_USERNAME_COLUMN_LABEL = "iUsername";

    private SQLConnection() {
        connect();
    }

    private void connect() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = ("jdbc:mysql://localhost:3306/cs434?useSSL=false&user=newuser&password=armanADMIN123");
        try {
            CONNECTION = DriverManager.getConnection(url);
            System.out.println("Connected");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not connect to DB");
        }
    }

    public static SQLConnection getInstance() {
        if (SQL_CONNECTION == null) {
            SQL_CONNECTION = new SQLConnection();
            return SQL_CONNECTION;
        } else
            return SQL_CONNECTION;
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

    public void registerInstructor(String name, String username, String password, String email, String department) {
        String query = "INSERT INTO instructors (iName, iUsername, iPassword, iEmail, iDepartment)" +
                " VALUES ('" + name + "', '" + username + "', '" + password + "', '" + email + "', '" + department + "');";
        try {
            if (!doesUserExist(username, INSTRUCTOR_USERNAME_COLUMN_LABEL)) {
                Statement statement = CONNECTION.createStatement();
                statement.executeUpdate(query);
                System.out.println("Instructor added successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public boolean studentLoginCheck(String username, String password) {
        if (doesUserExist(username, "sUsername")) {
            try {
                String query = "SELECT S.sPassword FROM Students S WHERE S.sUsername = \"" + username + "\";";
                String userPass = "";
                Statement statement = CONNECTION.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next())
                    userPass = resultSet.getString("sPassword");
                return userPass.equals(password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean instructorLoginCheck(String username, String password) {
        if (doesUserExist(username, "iUsername")) {
            try {
                String query = "SELECT I.iPassword FROM instructors I WHERE I.iUsername = \"" + username + "\";";
                String userPass = "";
                Statement statement = CONNECTION.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next())
                    userPass = resultSet.getString("iPassword");
                return userPass.equals(password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void changeStudentPassword(String newPassword) {
        try {
            String query = "UPDATE students SET sPassword='" + newPassword + "' WHERE sUsername='" + ExamPortalView.CURRENT_STUDENT + "';";
            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeInstructorPassword(String newPassword) {
        try {
            String query = "UPDATE instructors SET iPassword='" + newPassword + "' WHERE iUsername='" + ExamPortalView.CURRENT_INSTRUCTOR + "';";
            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // For Student
    public String getLatestScore() {
        String query = "SELECT sLatestGrade FROM students WHERE sUsername='" + ExamPortalView.CURRENT_STUDENT + "';";
        String grade = "";
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next())
                grade = resultSet.getString("sLatestGrade");
            return grade;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JTable getStudentScoresTable() {
        int rowLimit = getNumStudents();
        int columnLimit = 2;
        String[][] data = new String[rowLimit][columnLimit];
        String query = "SELECT S.sLatestGrade, S.sName FROM students S";
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int rowCount = 0;
            while (resultSet.next()) {
                data[rowCount][0] = resultSet.getString("sName");
                data[rowCount][1] = resultSet.getString("sLatestGrade");
                rowCount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return createScoresTable(data);
    }

    private int getNumStudents() {
        int counter = 0;
        String query = "SELECT S.sUsername FROM students S";
        try {
            Statement statement = CONNECTION.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
                counter++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }

    private JTable createScoresTable(String[][] data) {
        String[] columnNames = { "Student Names", "Latest Grade" };
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnNames);
        defaultTableModel.setColumnIdentifiers(columnNames);
        JTable table = new JTable(defaultTableModel);
        table.setPreferredScrollableViewportSize(new Dimension(450,63));
        table.setFillsViewportHeight(true);
        return table;
    }

    public void updateScore(String studentName, int score) {
        String query = "UPDATE students SET sLatestGrade='" + score + "' WHERE sUsername='" + studentName + "'";
        try {
            Statement statement = CONNECTION.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
