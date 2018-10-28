package com.company;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class ExamPortalView extends JFrame {

    private JPanel loginPanel;
    private static final GridBagConstraints GBC = new GridBagConstraints();

    public ExamPortalView(ExamPortalController controller) {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginPanel(controller);
        this.revalidate();
        this.repaint();
    }

    private void loginPanel(ExamPortalController controller) {
        loginPanel = new JPanel();
        loginPanel.setSize(300, 300);
        loginPanel.setLayout(new BorderLayout());
        setLoginPanelLayout(loginPanel, controller);

        this.setSize(loginPanel.getSize());
        this.add(loginPanel);
    }

    private void setLoginPanelLayout(JPanel panel, ExamPortalController controller) {
        panel.setLayout(new GridBagLayout());
        Font loginFont = new Font("Arial Rounded MT", Font.BOLD, 20);

        JLabel loginLabel = new JLabel("Welcome to the Login page");
        loginLabel.setFont(loginFont);
        GBC.fill = GridBagConstraints.VERTICAL;
        GBC.insets = new Insets(5, 5, 5, 5);
        GBC.gridwidth = 2;
        panel.add(loginLabel, GBC);
        setLoginPanelTextFields(panel);
        setLoginPanelButtons(panel, controller);
    }

    private void setLoginPanelTextFields(JPanel panel) {
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(120, 20));
        JTextField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(120, 20));
        GBC.gridwidth = 1;
        GBC.gridy = 1;
        panel.add(usernameLabel, GBC);
        GBC.gridx = 1;
        panel.add(usernameField, GBC);
        GBC.gridx = 0;
        GBC.gridy = 2;
        panel.add(passwordLabel, GBC);
        GBC.gridx = 1;
        panel.add(passwordField, GBC);
    }

    private void setLoginPanelButtons(JPanel panel, ExamPortalController controller) {
        JButton studentLogin = new JButton("Student Login");
        JButton instructorLogin = new JButton("Instructor Login");
        JButton register = new JButton("Register");
        registerButtonActionListener(register, controller);
        GBC.gridx = 0;
        GBC.gridy = 3;
        panel.add(studentLogin, GBC);
        GBC.gridx = 1;
        panel.add(instructorLogin, GBC);
        GBC.gridx = 0;
        GBC.gridy = 4;
        GBC.gridwidth = 2;
        panel.add(register, GBC);
    }

    private void registerButtonActionListener(JButton register, ExamPortalController controller) {
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.registerButtonClicked();
            }
        });
    }

    private void registerPanel() {
        JTabbedPane userSelection = new JTabbedPane();
        JPanel studentRegisterPanel = studentRegisterPanel();
        JPanel instructorRegisterPanel = instructorRegisterPanel();
        userSelection.add("Student", studentRegisterPanel);
        userSelection.add("Instructor", instructorRegisterPanel);
        this.add(userSelection);
    }

    private JPanel studentRegisterPanel() {
        JPanel studentRegisterPanel = new JPanel();
        studentRegisterPanel.setSize(500, 500);
        JPanel upperPanel = new JPanel();
        JLabel register = new JLabel("STUDENT REGISTER");
        register.setFont(new Font("Serif", Font.BOLD, 22));
        upperPanel.add(register);
        studentRegisterPanel.add(upperPanel, BorderLayout.NORTH);
        JPanel mainPanel = new JPanel();
        studentRegisterPanel.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new GridLayout(5, 2));
        JLabel user = new JLabel("User Name:");
        user.setFont(new Font("Serif", Font.PLAIN, 17));
        JTextField userName = new JTextField();
        userName.setFont(new Font("Serif", Font.PLAIN, 17));
        mainPanel.add(user);
        mainPanel.add(userName);
        JLabel password = new JLabel("Password:");
        password.setFont(new Font("Serif", Font.PLAIN, 17));
        JPasswordField passwordValue = new JPasswordField();
        passwordValue.setFont(new Font("Serif", Font.PLAIN, 17));
        mainPanel.add(password);
        mainPanel.add(passwordValue);
        JLabel repassword = new JLabel("Retype Password:");
        repassword.setFont(new Font("Serif", Font.PLAIN, 17));
        JPasswordField repasswordValue = new JPasswordField();
        repasswordValue.setFont(new Font("Serif", Font.PLAIN, 17));
        mainPanel.add(repassword);
        mainPanel.add(repasswordValue);
        JLabel email = new JLabel("E-mail Address:");
        email.setFont(new Font("Serif", Font.PLAIN, 17));
        mainPanel.add(email);
        JTextField emailValue = new JTextField();
        emailValue.setFont(new Font("Serif", Font.PLAIN, 17));
        mainPanel.add(emailValue);
        JLabel department = new JLabel("Department:");
        department.setFont(new Font("Serif", Font.PLAIN, 17));
        mainPanel.add(department);
        String[] departments = {"Computer Science", "Industrial Engineering", "Business Administration", "Music"};
        JComboBox departmentBox = new JComboBox(departments);
        departmentBox.setFont(new Font("Serif", Font.PLAIN, 17));
        mainPanel.add(departmentBox);

        JButton submitButton = new JButton("SUBMIT");
        submitButton.setFont(new Font("Serif", Font.BOLD, 15));
        studentRegisterPanel.add(submitButton, BorderLayout.SOUTH);
        this.setSize(studentRegisterPanel.getSize());
        this.add(studentRegisterPanel);
        return studentRegisterPanel;
    }

    private JPanel instructorRegisterPanel() {
        JPanel studentRegisterPanel = new JPanel();
        studentRegisterPanel.setSize(500, 500);
        JPanel upperPanel = new JPanel();
        JLabel register = new JLabel("INSTRUCTOR REGISTER");
        register.setFont(new Font("Serif", Font.BOLD, 22));
        upperPanel.add(register);
        studentRegisterPanel.add(upperPanel, BorderLayout.NORTH);
        JPanel mainPanel = new JPanel();
        studentRegisterPanel.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new GridLayout(5, 2));
        JLabel user = new JLabel("User Name:");
        user.setFont(new Font("Serif", Font.PLAIN, 17));
        JTextField userName = new JTextField();
        userName.setFont(new Font("Serif", Font.PLAIN, 17));
        mainPanel.add(user);
        mainPanel.add(userName);
        JLabel password = new JLabel("Password:");
        password.setFont(new Font("Serif", Font.PLAIN, 17));
        JPasswordField passwordValue = new JPasswordField();
        passwordValue.setFont(new Font("Serif", Font.PLAIN, 17));
        mainPanel.add(password);
        mainPanel.add(passwordValue);
        JLabel repassword = new JLabel("Retype Password:");
        repassword.setFont(new Font("Serif", Font.PLAIN, 17));
        JPasswordField repasswordValue = new JPasswordField();
        repasswordValue.setFont(new Font("Serif", Font.PLAIN, 17));
        mainPanel.add(repassword);
        mainPanel.add(repasswordValue);
        JLabel email = new JLabel("E-mail Address:");
        email.setFont(new Font("Serif", Font.PLAIN, 17));
        mainPanel.add(email);
        JTextField emailValue = new JTextField();
        emailValue.setFont(new Font("Serif", Font.PLAIN, 17));
        mainPanel.add(emailValue);
        JLabel department = new JLabel("Department:");
        department.setFont(new Font("Serif", Font.PLAIN, 17));
        mainPanel.add(department);
        String[] departments = {"Computer Science", "Industrial Engineering", "Business Administration", "Music"};
        JComboBox departmentBox = new JComboBox(departments);
        departmentBox.setFont(new Font("Serif", Font.PLAIN, 17));
        mainPanel.add(departmentBox);

        JButton submitButton = new JButton("SUBMIT");
        submitButton.setFont(new Font("Serif", Font.BOLD, 15));
        studentRegisterPanel.add(submitButton, BorderLayout.SOUTH);
        this.setSize(studentRegisterPanel.getSize());
        this.add(studentRegisterPanel);
        return studentRegisterPanel;
    }

    protected void setRegisterPanel() {
        this.getContentPane().removeAll();
        registerPanel();
        this.revalidate();
        this.repaint();
    }
}
