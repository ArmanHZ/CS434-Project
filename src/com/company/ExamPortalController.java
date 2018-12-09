package com.company;

import javax.swing.*;
import java.awt.*;

public class ExamPortalController {

    private ExamPortalView view;
    private SQLConnection connection;

    public ExamPortalController() {
        view = new ExamPortalView(this);
        connection = SQLConnection.getInstance();
    }

    public void studentRegisterButtonClicked(String name, String username, String password,
                                             String rePassword, String email, String department) {

        if (password.equals(rePassword) && isValidEmail(email)) {
            connection.registerStudent(name, username, password, email, department);
            view.resetLoginPanel(this);
        }
        else
            JOptionPane.showMessageDialog(view, "Password mismatch or Incorrect E-mail address", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private boolean isValidEmail(String email) {
        for (int i = 0; i < email.length(); ++i) {
            if (email.charAt(i) == '@')
                return true;
        }
        return false;
    }

    public void studentLogin(String username, String password) {
        if (connection.studentLoginCheck(username, password)) {
            System.out.println("Valid User");
            // TODO studentPanel where the Student sees the exams and etc.
        } else {
            JOptionPane.showMessageDialog(null, "Username or Password is Incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void viewStudentScores(JPanel middlePanel) {
        middlePanel.removeAll();
        JTable studentScores = connection.getStudentScoresTable();
        JScrollPane scrollPane = new JScrollPane(studentScores);
        middlePanel.add(scrollPane, BorderLayout.CENTER);
        middlePanel.revalidate();
        middlePanel.repaint();
    }

}
