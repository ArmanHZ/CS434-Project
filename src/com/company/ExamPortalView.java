package com.company;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class ExamPortalView extends JFrame {

    private static final GridBagConstraints GBC = new GridBagConstraints();
    private static final Font HEADER_FONT = new Font("Rockwell", Font.BOLD, 24);
    private static final Font TEXT_FONT_PLAIN = new Font("Banschrift", Font.PLAIN, 16);
    private static final Font TEXT_FONT_BOLD = new Font("Banschrift", Font.BOLD, 18);
    private static final Dimension TEXT_FIELD_DIM = new Dimension(180, 30);

    private JTextField usernameField;
    private JTextField passwordField;
    private static JPanel instructorQuestionPanel = new JPanel();

    private static final int INSTRUCTOR_PASS = 53894;
    private JPanel instructorPanel;
    private static int questionCounter = 0;
    private static ArrayList<Question> questions = new ArrayList<Question>();
    private static JComboBox answerBox = null;


    public ExamPortalView(ExamPortalController controller) {
        this.setTitle("Exam Portal");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginPanel(controller);
        this.setLocationRelativeTo(null);
        this.revalidate();
        this.repaint();
    }

    private void loginPanel(ExamPortalController controller) {
        JPanel loginPanel = new JPanel();
        loginPanel.setSize(450, 400);
        loginPanel.setLayout(new BorderLayout());
        setLoginPanelLayout(loginPanel, controller);
        this.setSize(loginPanel.getSize());
        this.setLocationRelativeTo(null);
        this.add(loginPanel);
    }

    private void setLoginPanelLayout(JPanel loginPanel, ExamPortalController controller) {
        GBC.gridx = GBC.gridy = 0;
        loginPanel.setLayout(new GridBagLayout());
        JLabel loginLabel = new JLabel("Welcome to the Login page");
        loginLabel.setFont(HEADER_FONT);
        GBC.fill = GridBagConstraints.VERTICAL;
        GBC.insets = new Insets(5, 5, 5, 5);
        GBC.gridwidth = 2;
        loginPanel.add(loginLabel, GBC);
        setLoginPanelTextFields(loginPanel);
        setLoginPanelButtons(loginPanel, controller);
    }

    private void setLoginPanelTextFields(JPanel panel) {
        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(TEXT_FONT_PLAIN);
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(TEXT_FONT_PLAIN);
        usernameField = new JTextField();
        usernameField.setPreferredSize(TEXT_FIELD_DIM);
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(TEXT_FIELD_DIM);
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

    private void setLoginPanelButtons(JPanel loginPanel, ExamPortalController controller) {
        JButton studentLogin = new JButton("Student Login");
        studentLogin.addActionListener(e -> controller.studentLogin(usernameField.getText(), passwordField.getText(), loginPanel));
        studentLogin.setFont(TEXT_FONT_BOLD);
        JButton instructorLogin = new JButton("Instructor Login");
        instructorLogin.setFont(TEXT_FONT_BOLD);
        instructorLoginButtonActionListener(instructorLogin, controller);
        JButton register = new JButton("Register");
        register.setFont(TEXT_FONT_BOLD);
        registerButtonActionListener(register, controller);
        GBC.gridx = 0;
        GBC.gridy = 3;
        loginPanel.add(studentLogin, GBC);
        GBC.gridx = 1;
        loginPanel.add(instructorLogin, GBC);
        GBC.gridx = 0;
        GBC.gridy = 4;
        GBC.gridwidth = 2;
        loginPanel.add(register, GBC);
    }

    // Student Panel after Login
    public void setStudentPanel(JPanel studentPanel) {
        this.getContentPane().removeAll();
        studentPanel.removeAll();
        this.add(studentPanel);
        studentPanel.add(new JButton("XDDDDDDDD"));
        this.revalidate();
        this.repaint();
    }

    private void instructorLoginButtonActionListener(JButton instructorLogin, ExamPortalController controller) {
        instructorLogin.addActionListener(e -> setInstructorPanel(controller));
    }

    private void registerButtonActionListener(JButton register, ExamPortalController controller) {
        register.addActionListener(e -> setRegisterPanel(controller));
    }

    private void instructorPanel(ExamPortalController controller) {
        instructorPanel = new JPanel(new BorderLayout());
        instructorPanel.setSize(1000, 500);
        setInstructorPanel(instructorPanel, controller);
        this.setSize(instructorPanel.getSize());
        this.add(instructorPanel);
        this.setLocationRelativeTo(null);
        this.revalidate();
        this.repaint();
    }

    private void setInstructorPanel(JPanel instructorPanel, ExamPortalController controller) {
        JPanel upperPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        upperPanel.setLayout(new GridLayout(1, 4));
        instructorPanel.add(upperPanel, BorderLayout.NORTH);
        instructorPanel.add(middlePanel, BorderLayout.CENTER);
        instructorPanel.add(bottomPanel, BorderLayout.SOUTH);
        setInstructorPanelButtons(controller, upperPanel, middlePanel, bottomPanel);
    }

    private void setInstructorPanelButtons(ExamPortalController controller, JPanel upperPanel, JPanel middlePanel, JPanel bottomPanel) {
        JButton createExam = new JButton("Create Exam");
        createExam.addActionListener(e -> controller.createExamPanel(middlePanel, bottomPanel));
        createExam.setFont(TEXT_FONT_BOLD);
        upperPanel.add(createExam);
        JButton viewScores = new JButton("View Scores");
        viewScores.addActionListener(e -> controller.viewStudentScores(middlePanel, bottomPanel));
        viewScores.setFont(TEXT_FONT_BOLD);
        upperPanel.add(viewScores);
        JButton changePassword = new JButton("Change Password");
        changePassword.setFont(TEXT_FONT_BOLD);
        upperPanel.add(changePassword);
        JButton viewMessage = new JButton("View Message");
        viewMessage.setFont(TEXT_FONT_BOLD);
        upperPanel.add(viewMessage);
        JButton logOut = new JButton("Log Out");
        logOut.addActionListener(e -> resetLoginPanel(controller));
        logOut.setFont(TEXT_FONT_BOLD);
        upperPanel.add(logOut);
    }

    private void registerPanel(ExamPortalController controller) {
        JTabbedPane userSelection = new JTabbedPane();
        JPanel studentRegisterPanel = studentRegisterPanel(controller);
        JPanel instructorRegisterPanel = instructorRegisterPanel(controller);
        userSelection.add("Student", studentRegisterPanel);
        userSelection.add("Instructor", instructorRegisterPanel);
        this.add(userSelection);
    }

    private JPanel studentRegisterPanel(ExamPortalController controller) {
        JPanel studentRegisterPanel = new JPanel();
        this.setSize(new Dimension(500, 500));
        studentRegisterPanel.setLayout(new BorderLayout());
        JPanel northPanel = new JPanel(new GridBagLayout());
        studentRegisterPanel.add(northPanel, BorderLayout.NORTH);
        JLabel studentRegisterLabel = new JLabel("Student Register");
        studentRegisterLabel.setFont(HEADER_FONT);
        GBC.gridwidth = 2;
        GBC.gridx = 0;
        GBC.gridy = 0;
        northPanel.add(studentRegisterLabel, GBC);
        setStudentRegisterPanelLabels(northPanel);
        setStudentRegisterPanelFields(northPanel, controller);

        return studentRegisterPanel;
    }

    private void setStudentRegisterPanelLabels(JPanel northPanel) {
        GBC.gridwidth = 1;
        GBC.gridy = 1;
        JLabel firstName = new JLabel("First Name: ", JLabel.LEFT);
        firstName.setFont(TEXT_FONT_BOLD);
        northPanel.add(firstName, GBC);
        GBC.gridy++;
        JLabel userName = new JLabel("Username: ", JLabel.LEFT);
        userName.setFont(TEXT_FONT_BOLD);
        northPanel.add(userName, GBC);
        GBC.gridy++;
        JLabel password = new JLabel("Password: ", JLabel.LEFT);
        password.setFont(TEXT_FONT_BOLD);
        northPanel.add(password, GBC);
        GBC.gridy++;
        JLabel rePassword = new JLabel("Re-Enter Password: ", JLabel.LEFT);
        rePassword.setFont(TEXT_FONT_BOLD);
        northPanel.add(rePassword, GBC);
        GBC.gridy++;
        JLabel email = new JLabel("E-mail Address: ", JLabel.LEFT);
        email.setFont(TEXT_FONT_BOLD);
        northPanel.add(email, GBC);
        GBC.gridy++;
        JLabel department = new JLabel("Department: ", JLabel.LEFT);
        department.setFont(TEXT_FONT_BOLD);
        northPanel.add(department, GBC);
    }

    private void setStudentRegisterPanelFields(JPanel northPanel, ExamPortalController controller) {
        GBC.gridy = 1;
        GBC.gridx = 1;
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(TEXT_FIELD_DIM);
        nameField.setFont(TEXT_FONT_PLAIN);
        northPanel.add(nameField, GBC);
        GBC.gridy++;
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(TEXT_FIELD_DIM);
        usernameField.setFont(TEXT_FONT_PLAIN);
        northPanel.add(usernameField, GBC);
        GBC.gridy++;
        JTextField passwordField = new JPasswordField();
        passwordField.setPreferredSize(TEXT_FIELD_DIM);
        northPanel.add(passwordField, GBC);
        GBC.gridy++;
        JTextField rePasswordField = new JPasswordField();
        rePasswordField.setPreferredSize(TEXT_FIELD_DIM);
        northPanel.add(rePasswordField, GBC);
        GBC.gridy++;
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(TEXT_FIELD_DIM);
        emailField.setFont(TEXT_FONT_PLAIN);
        northPanel.add(emailField, GBC);
        GBC.gridy++;
        String[] departments = {"Computer Science", "Industrial Engineering", "Business Administration", "Music"};
        JComboBox departmentBox = new JComboBox(departments);
        departmentBox.setPreferredSize(TEXT_FIELD_DIM);
        departmentBox.setFont(TEXT_FONT_PLAIN);
        northPanel.add(departmentBox, GBC);
        GBC.gridy++;
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String rePassword = rePasswordField.getText();
            String email = emailField.getText();
            String department = departmentBox.getSelectedItem().toString();
            if (!name.equals("") && !username.equals("") && !password.equals("") && !rePassword.equals("") && !email.equals(""))
                controller.studentRegisterButtonClicked(name, username, password, rePassword, email, department);
            else
                JOptionPane.showMessageDialog(northPanel, "One or more fields are empty!", "Error", JOptionPane.ERROR_MESSAGE);
        });
        registerButton.setFont(TEXT_FONT_BOLD);
        GBC.gridx = 0;
        northPanel.add(registerButton, GBC);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> resetLoginPanel(controller));
        backButton.setFont(TEXT_FONT_BOLD);
        GBC.gridx = 1;
        northPanel.add(backButton, GBC);
    }


    private JPanel instructorRegisterPanel(ExamPortalController controller) {
        JPanel instructorRegisterPanel = new JPanel();
        this.setSize(new Dimension(500, 500));
        instructorRegisterPanel.setLayout(new BorderLayout());
        JPanel northPanel = new JPanel(new GridBagLayout());
        instructorRegisterPanel.add(northPanel, BorderLayout.NORTH);
        JLabel studentRegisterLabel = new JLabel("Instructor Register");
        studentRegisterLabel.setFont(HEADER_FONT);
        GBC.gridwidth = 2;
        GBC.gridx = 0;
        GBC.gridy = 0;
        northPanel.add(studentRegisterLabel, GBC);
        setInstructorRegisterPanelLabels(northPanel);
        setInstructorRegisterPanelFields(northPanel, controller);

        return instructorRegisterPanel;
    }

    private void setInstructorRegisterPanelLabels(JPanel northPanel) {
        GBC.gridwidth = 1;
        GBC.gridy = 1;
        JLabel firstName = new JLabel("First Name: ", JLabel.LEFT);
        firstName.setFont(TEXT_FONT_BOLD);
        northPanel.add(firstName, GBC);
        GBC.gridy++;
        JLabel userName = new JLabel("Username: ", JLabel.LEFT);
        userName.setFont(TEXT_FONT_BOLD);
        northPanel.add(userName, GBC);
        GBC.gridy++;
        JLabel password = new JLabel("Password: ", JLabel.LEFT);
        password.setFont(TEXT_FONT_BOLD);
        northPanel.add(password, GBC);
        GBC.gridy++;
        JLabel rePassword = new JLabel("Re-Enter Password: ", JLabel.LEFT);
        rePassword.setFont(TEXT_FONT_BOLD);
        northPanel.add(rePassword, GBC);
        GBC.gridy++;
        JLabel email = new JLabel("E-mail Address: ", JLabel.LEFT);
        email.setFont(TEXT_FONT_BOLD);
        northPanel.add(email, GBC);
        GBC.gridy++;
        JLabel instructorCode = new JLabel("Instructor Access Code: ");
        instructorCode.setFont(TEXT_FONT_BOLD);
        northPanel.add(instructorCode, GBC);
        GBC.gridy++;
        JLabel department = new JLabel("Department: ", JLabel.LEFT);
        department.setFont(TEXT_FONT_BOLD);
        northPanel.add(department, GBC);
    }

    private void setInstructorRegisterPanelFields(JPanel northPanel, ExamPortalController controller) {
        GBC.gridy = 1;
        GBC.gridx = 1;
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(TEXT_FIELD_DIM);
        nameField.setFont(TEXT_FONT_PLAIN);
        northPanel.add(nameField, GBC);
        GBC.gridy++;
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(TEXT_FIELD_DIM);
        usernameField.setFont(TEXT_FONT_PLAIN);
        northPanel.add(usernameField, GBC);
        GBC.gridy++;
        JTextField passwordField = new JPasswordField();
        passwordField.setPreferredSize(TEXT_FIELD_DIM);
        northPanel.add(passwordField, GBC);
        GBC.gridy++;
        JTextField rePasswordField = new JPasswordField();
        rePasswordField.setPreferredSize(TEXT_FIELD_DIM);
        northPanel.add(rePasswordField, GBC);
        GBC.gridy++;
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(TEXT_FIELD_DIM);
        emailField.setFont(TEXT_FONT_PLAIN);
        northPanel.add(emailField, GBC);
        GBC.gridy++;
        JTextField instructorCodeField = new JTextField();
        instructorCodeField.setPreferredSize(TEXT_FIELD_DIM);
        northPanel.add(instructorCodeField, GBC);
        GBC.gridy++;
        String[] departments = {"Computer Science", "Industrial Engineering", "Business Administration", "Music"};
        JComboBox departmentBox = new JComboBox(departments);
        departmentBox.setPreferredSize(TEXT_FIELD_DIM);
        departmentBox.setFont(TEXT_FONT_PLAIN);
        northPanel.add(departmentBox, GBC);
        GBC.gridy++;
        JButton registerButton = new JButton("Register");
        registerButton.setFont(TEXT_FONT_BOLD);
        GBC.gridx = 0;
        northPanel.add(registerButton, GBC);
        JButton backButton = new JButton("Back");
        backButton.setFont(TEXT_FONT_BOLD);
        backButton.addActionListener(e -> resetLoginPanel(controller));
        GBC.gridx = 1;
        northPanel.add(backButton, GBC);
    }

    private void setRegisterPanel(ExamPortalController controller) {
        this.getContentPane().removeAll();
        registerPanel(controller);
        this.setLocationRelativeTo(null);
        this.revalidate();
        this.repaint();
    }

    protected void resetLoginPanel(ExamPortalController controller) {
        this.getContentPane().removeAll();
        loginPanel(controller);
    }

    private void setInstructorPanel(ExamPortalController controller) {
        this.getContentPane().removeAll();
        instructorPanel(controller);
        this.revalidate();
        this.repaint();
    }
}
