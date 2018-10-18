package com.company;

import java.awt.*;
import javax.swing.*;

public class ExamPortalView extends JFrame{

    private JPanel loginPanel;

    public ExamPortalView() {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginPanel();
        this.revalidate();
        this.repaint();
    }

    private void loginPanel() {
        loginPanel = new JPanel();
        loginPanel.setSize(300, 300);
        loginPanel.setLayout(new BorderLayout());
        setLoginPanelLayout(loginPanel);

        this.setSize(loginPanel.getSize());
        this.add(loginPanel);
    }

    private void setLoginPanelLayout(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Font loginFont = new Font("Arial Rounded MT", Font.BOLD, 20);

        JLabel loginLabel = new JLabel("Welcome to the Login page");
        loginLabel.setFont(loginFont);
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(120, 20));
        JTextField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(120, 20));
        JButton studentLogin = new JButton("Student Login");
        JButton instructorLogin = new JButton("Instructor Login");
        JButton register = new JButton("Register");

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 2;
        panel.add(loginLabel, gbc);
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(studentLogin, gbc);
        gbc.gridx = 1;
        panel.add(instructorLogin, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(register, gbc);
    }

}
