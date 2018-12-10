package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExamPortalController {

    private ExamPortalView view;
    private SQLConnection connection;
    private List<Question> questions = new ArrayList<Question>();
    private ButtonGroup buttonGroup;
    private JSpinner timeLimit;
    private JSpinner numQuestion;
    private JTextField questionField;
    private JComboBox trueFalseOption;
    public static List<List<Question>> exams = new ArrayList<>();
    private int pageCounter = 0;

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

    public void viewStudentScores(JPanel middlePanel, JPanel bottomPanel) {
        middlePanel.removeAll();
        bottomPanel.removeAll();
        JTable studentScores = connection.getStudentScoresTable();
        JScrollPane scrollPane = new JScrollPane(studentScores);
        middlePanel.add(scrollPane);
        middlePanel.revalidate();
        middlePanel.repaint();
    }

    public void createExamPanel(JPanel middlePanel, JPanel bottomPanel) {
        middlePanel.removeAll();
        if (!questions.isEmpty())
            saveQuestionsToExams(questions);
        questions.clear();  // Whenever the Create Exam button is clicked the previous Questions are cleared.
        pageCounter = 0;
        int result = JOptionPane.showConfirmDialog(null, questionTypeSelect(), "Question Select", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            createExam(middlePanel);
            createExamBottomButtons(middlePanel, bottomPanel);
        }
        middlePanel.revalidate();
        middlePanel.repaint();
    }

    private void createExam(JPanel middlePanel) {
        switch (getExamType()) {
            case "TF":
                createTrueFalseQuestionPage(middlePanel);
                break;
            case "multi":
                createMultiQuestionPage();
                break;
            default:
                createTestQuestionPage();
                break;
        }
    }

    private String getExamType() {
        return buttonGroup.getSelection().getActionCommand();
    }

    private JPanel questionTypeSelect() {
        JPanel panel = new JPanel(new GridBagLayout());
        buttonGroup = new ButtonGroup();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel("Please fill in the information"), gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;
        JRadioButton trueFalse = new JRadioButton("T/F");
        trueFalse.setActionCommand("TF");
        JRadioButton multipleChoice = new JRadioButton("Multiple");
        multipleChoice.setActionCommand("multi");
        JRadioButton test = new JRadioButton("Test");
        test.setActionCommand("test");
        buttonGroup.add(trueFalse);
        buttonGroup.add(multipleChoice);
        buttonGroup.add(test);
        panel.add(trueFalse, gbc);
        gbc.gridx = 1;
        panel.add(multipleChoice, gbc);
        gbc.gridx = 2;
        panel.add(test, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Time limit in Minutes(0 for unlimited): "), gbc);
        gbc.gridx = 1;
        timeLimit = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
        panel.add(timeLimit, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Number of Questions: "), gbc);
        gbc.gridx = 1;
        numQuestion = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
        panel.add(numQuestion, gbc);
        return panel;
    }

    private void createTrueFalseQuestionPage(JPanel middlePanel) {
        JLabel question = new JLabel("Question" + (pageCounter + 1) + ": ");
        questionField = new JTextField(15);
        JLabel answer = new JLabel("Answer: ");
        String [] options = { "T", "F" };
        trueFalseOption = new JComboBox(options);
        middlePanel.add(question);
        middlePanel.add(questionField);
        middlePanel.add(answer);
        middlePanel.add(trueFalseOption);
    }

    private void createMultiQuestionPage() {

    }

    private void createTestQuestionPage() {

    }

    private void createExamBottomButtons(JPanel middlePanel, JPanel bottomPanel) {
        bottomPanel.removeAll();
        JButton previous = new JButton("Previous");
        JButton next = new JButton("Next");
        next.addActionListener(e -> nextQuestion(middlePanel));
        JButton saveQuestion = new JButton("Save Question");
        saveQuestion.addActionListener(e -> saveQuestion());
        bottomPanel.add(previous);
        bottomPanel.add(next);
        bottomPanel.add(saveQuestion);
        bottomPanel.revalidate();
        bottomPanel.repaint();
    }

    private void saveQuestion() {
        String description = questionField.getText();
        String answer = Objects.requireNonNull(trueFalseOption.getSelectedItem()).toString();
        String type = buttonGroup.getSelection().getActionCommand();
        Question question = new Question(description, answer, type);
        questions.add(question);
        System.out.println(pageCounter + " " + question);
    }

    private void nextQuestion(JPanel middlePanel) {
        pageCounter++;
        middlePanel.removeAll();
        createExam(middlePanel);
        middlePanel.revalidate();
        middlePanel.repaint();
    }

    private void saveQuestionsToExams(List<Question> questions) {
        List<Question> temp = new ArrayList<Question>();
        temp = questions;
        exams.add(temp);
    }

}
