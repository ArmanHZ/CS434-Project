package com.company;

import javax.swing.*;

public class ExamPortalController {

    private ExamPortalView view;
    private SQLConnection connection;

    public ExamPortalController() {
        view = new ExamPortalView(this);
        connection = new SQLConnection();
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

}
