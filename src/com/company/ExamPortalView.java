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
        setLoginPanelGridLayout(loginPanel);

        this.setSize(loginPanel.getSize());
        this.add(loginPanel);
    }

    // TODO JPanel stuff should be on another function, add the buttons and register button
    private void setLoginPanelGridLayout(JPanel panel) {
        GridLayout gridLayout = new GridLayout(4, 0);
        panel.setLayout(gridLayout);
        JLabel loginLabel = new JLabel("Welcome to the Login page");
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(150, 20));
        JTextField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(150, 20));
        JButton studentLogin = new JButton("Student Login");
        JButton instructorLogin = new JButton("Instructor Login");

        JPanel firstLine = new JPanel();
        firstLine.add(loginLabel);
        JPanel secondLine = new JPanel();
        secondLine.add(usernameLabel);
        secondLine.add(usernameField);
        JPanel thirdLine = new JPanel();
        thirdLine.add(passwordLabel);
        thirdLine.add(passwordField);

        panel.add(firstLine);
        panel.add(secondLine);
        panel.add(thirdLine);

    }

}
