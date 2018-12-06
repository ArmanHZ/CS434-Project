package com.company;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;
import javax.swing.*;

public class ExamPortalView extends JFrame {

    private static final GridBagConstraints GBC = new GridBagConstraints();
    private static final Font HEADER_FONT = new Font("Rockwell", Font.BOLD, 24);
    private static final Font TEXT_FONT_PLAIN = new Font("Banschrift", Font.PLAIN, 16);
    private static final Font TEXT_FONT_BOLD = new Font("Banschrift", Font.BOLD, 18);
    private static final Dimension TEXT_FIELD_DIM = new Dimension(180, 30);

    private static final int INSTRUCTOR_PASS = 53894;

    public ExamPortalView(ExamPortalController controller) {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginPanel(controller);
        this.setLocationRelativeTo(null);
        this.revalidate();
        this.repaint();
    }

    private void instructorPanel(ExamPortalController controller) {
        JPanel instructorPanel = new JPanel();
        instructorPanel.setSize(850, 400);
        setInstructorPanel(instructorPanel, controller);
        this.setSize(instructorPanel.getSize());
        this.add(instructorPanel);
    }

    private void setInstructorPanel(JPanel instructorPanel, ExamPortalController controller) {
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new GridLayout(1, 4));
        instructorPanel.add(upperPanel, BorderLayout.NORTH);
        JButton createExam = new JButton("Create Exam");
        createExam.setFont(TEXT_FONT_BOLD);
        upperPanel.add(createExam);
        createExamButtonActionListener(createExam, controller);
        JButton viewScores = new JButton("View Scores");
        viewScores.setFont(TEXT_FONT_BOLD);
        upperPanel.add(viewScores);
        JButton changePassword = new JButton("Change Password");
        changePassword.setFont(TEXT_FONT_BOLD);
        upperPanel.add(changePassword);
        JButton viewMessage = new JButton("View Message");
        viewMessage.setFont(TEXT_FONT_BOLD);
        upperPanel.add(viewMessage);
    }

    private void createExamButtonActionListener(JButton instructorLogin, ExamPortalController controller) {
        instructorLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.createExamButtonClicked();
            }
        });
    }

    private void loginPanel(ExamPortalController controller) {
        JPanel loginPanel = new JPanel();
        loginPanel.setSize(450, 400);
        loginPanel.setLayout(new BorderLayout());
        setLoginPanelLayout(loginPanel, controller);
        this.setSize(loginPanel.getSize());
        this.add(loginPanel);
    }

    private void setLoginPanelLayout(JPanel panel, ExamPortalController controller) {
        panel.setLayout(new GridBagLayout());
        JLabel loginLabel = new JLabel("Welcome to the Login page");
        loginLabel.setFont(HEADER_FONT);
        GBC.fill = GridBagConstraints.VERTICAL;
        GBC.insets = new Insets(5, 5, 5, 5);
        GBC.gridwidth = 2;
        panel.add(loginLabel, GBC);
        setLoginPanelTextFields(panel);
        setLoginPanelButtons(panel, controller);
    }

    private void setLoginPanelTextFields(JPanel panel) {
        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(TEXT_FONT_PLAIN);
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(TEXT_FONT_PLAIN);
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(TEXT_FIELD_DIM);
        JTextField passwordField = new JPasswordField();
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

    private void setLoginPanelButtons(JPanel panel, ExamPortalController controller) {
        JButton studentLogin = new JButton("Student Login");
        studentLogin.setFont(TEXT_FONT_BOLD);
        JButton instructorLogin = new JButton("Instructor Login");
        instructorLogin.setFont(TEXT_FONT_BOLD);
        instructorLoginButtonActionListener(instructorLogin, controller);
        JButton register = new JButton("Register");
        register.setFont(TEXT_FONT_BOLD);
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

    private void instructorLoginButtonActionListener(JButton instructorLogin, ExamPortalController controller) {
        instructorLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.instructorLoginButtonClicked();
            }
        });
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
        setStudentRegisterPanelFields(northPanel);
        JButton registerButton = new JButton("Register");
        registerButton.setFont(TEXT_FONT_BOLD);
        GBC.gridwidth = 2;
        GBC.gridx = 0;
        northPanel.add(registerButton, GBC);
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

    private void setStudentRegisterPanelFields(JPanel northPanel) {
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
    }


    private JPanel instructorRegisterPanel() {
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
        setInstructorRegisterPanelFields(northPanel);
        JButton registerButton = new JButton("Register");
        registerButton.setFont(TEXT_FONT_BOLD);
        GBC.gridwidth = 2;
        GBC.gridx = 0;
        northPanel.add(registerButton, GBC);
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

    private void setInstructorRegisterPanelFields(JPanel northPanel) {
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
    }

    protected void setRegisterPanel() {
        this.getContentPane().removeAll();
        registerPanel();
        this.revalidate();
        this.repaint();
    }

    public void setInstructorPanel(ExamPortalController controller) {
        this.getContentPane().removeAll();
        instructorPanel(controller);
        this.revalidate();
        this.repaint();
    }

    public void setCreateExamPanel(ExamPortalController controller) {
        JTextField time = new JTextField(5);
        time.setText("");

        String[] examTypes = {"", "Multiple", "T/F", "Test"};
        JComboBox examTypeList = new JComboBox(examTypes);

        JTextField numberOfQuestions = new JTextField(5);
        numberOfQuestions.setText("");

        JPanel panel = new JPanel();

        panel.add(new JLabel("Please enter exam time (in minutes), number of questions for the exam and select exam type: "));
        panel.add(Box.createVerticalStrut(15)); // a spacer
        panel.add(new JLabel("Exam Time: "));
        panel.add(time);
        panel.add(Box.createHorizontalStrut(15)); // a spacer
        panel.add(new JLabel("Exam Type: "));
        panel.add(examTypeList);
        panel.add(Box.createHorizontalStrut(15)); // a spacer
        panel.add(new JLabel("Number of questions: "));
        panel.add(numberOfQuestions);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Create Exam", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            boolean isTimeInteger = Pattern.matches("^\\d*$", time.getText());
            boolean isNumberInteger = Pattern.matches("^\\d*$", numberOfQuestions.getText());
            if (time.getText().equals("") || examTypeList.getSelectedItem().equals("") || numberOfQuestions.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please be careful with your selection");
                setCreateExamPanel(controller);
            } else if (!isTimeInteger) {
                JOptionPane.showMessageDialog(null, "You should enter a positive number for exam time");
                setCreateExamPanel(controller);
            } else if (!isNumberInteger) {
                JOptionPane.showMessageDialog(null, "You should enter a positive number for number of questions");
                setCreateExamPanel(controller);
            } else {
                String timeEntered = time.getText();
                String examTypeSelected = (String)examTypeList.getSelectedItem();
                String numberOfQuestionsEntered = numberOfQuestions.getText();
            }
        }
        this.revalidate();
        this.repaint();
    }
}
