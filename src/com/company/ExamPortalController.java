package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ExamPortalController {

    private ExamPortalView view;
    private SQLConnection connection;
    private static final String INSTRUCTOR_PASS = "53894";
    private List<Question> questions = new ArrayList<Question>();
    private ButtonGroup buttonGroup;
    private JSpinner timeLimit;
//    private JSpinner numQuestion;
    private JTextArea questionArea;
    private JComboBox trueFalseOption;
    private JComboBox multipleChoiceOption;
    private ArrayList<JCheckBox> testOptions;
    public static List<List<Question>> exams = new ArrayList<>();
    private int pageCounter = 0;

    private static final Mediator MEDIATOR = new MediatorImpl();

    public ExamPortalController() {
        view = new ExamPortalView(this);
        connection = SQLConnection.getInstance();
    }

    public void studentRegisterButtonClicked(String name, String username, String password,
                                             String rePassword, String email, String department) {

        if (password.equals(rePassword) && isValidEmail(email)) {
            connection.registerStudent(name, username, password, email, department);
            JOptionPane.showMessageDialog(null, "Register complete!", "Success", JOptionPane.INFORMATION_MESSAGE);
            view.resetLoginPanel(this);
        }
        else
            JOptionPane.showMessageDialog(view, "Password mismatch or Incorrect E-mail address", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void instructorRegisterButtonClicked(String name, String username, String password,
                                                String rePassword, String email, String code, String department) {
        if (password.equals(rePassword) && code.equals(INSTRUCTOR_PASS) && isValidEmail(email)) {
            connection.registerInstructor(name, username, password, email, department);
            JOptionPane.showMessageDialog(null, "Register complete!", "Success", JOptionPane.INFORMATION_MESSAGE);
            view.resetLoginPanel(this);
        } else {
            JOptionPane.showMessageDialog(null, "Check your Password or Instructor Code or Email!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private boolean isValidEmail(String email) {
        for (int i = 0; i < email.length(); ++i) {
            if (email.charAt(i) == '@')
                return true;
        }
        return false;
    }

    public void studentLogin(String username, String password, JPanel studentPanel) {
        if (connection.studentLoginCheck(username, password)) {
            ExamPortalView.CURRENT_STUDENT = username;
            view.setStudentPanel(studentPanel, this);
        } else {
            JOptionPane.showMessageDialog(null, "Username or Password is Incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void viewLatestScore(JPanel studentMiddlePanel) {
        studentMiddlePanel.removeAll();
        String grade = connection.getLatestScore();
        studentMiddlePanel.add(new JLabel("Your latest grade is: " + grade));
        System.out.println(grade);
        studentMiddlePanel.revalidate();
        studentMiddlePanel.repaint();
    }

    public void instructorLogin(String username, String password) {
        if (connection.instructorLoginCheck(username, password)) {
            ExamPortalView.CURRENT_INSTRUCTOR = username;
            System.out.println("Valid User");   // Delete later
            view.initInstructorPanel(this);
        } else {
            JOptionPane.showMessageDialog(null, "Username or Password is Incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // For instructor
    public void viewStudentScores(JPanel middlePanel, JPanel bottomPanel) {
        middlePanel.removeAll();
        bottomPanel.removeAll();
        JTable studentScores = connection.getStudentScoresTable();
        JScrollPane scrollPane = new JScrollPane(studentScores);
        middlePanel.add(scrollPane);
        middlePanel.revalidate();
        middlePanel.repaint();
    }

    public void sendMessage() {
        String message = JOptionPane.showInputDialog(null, "Enter your message", "Message", JOptionPane.INFORMATION_MESSAGE);
        User temp = new Student(MEDIATOR, ExamPortalView.CURRENT_STUDENT);
        temp.sendMessage(message);
    }

    public void viewMessages(JPanel middlePanel) {
        JPanel temporaryPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        middlePanel.removeAll();
        middlePanel.add(temporaryPanel);
        MessageIterator iterator = new MessageIterator(MEDIATOR);
        while (iterator.hasNext()) {
            String message = (String)iterator.next();
            temporaryPanel.add(new JLabel(message), gbc);
            gbc.gridy++;
        }
        middlePanel.revalidate();
        middlePanel.repaint();
    }

    public void changeStudentPassword() {
        String newPassword = JOptionPane.showInputDialog(null, "Enter your new password!", "Password Change", JOptionPane.INFORMATION_MESSAGE);
        if (!newPassword.equals("")) {
            connection.changeStudentPassword(newPassword);
            JOptionPane.showMessageDialog(null, "Password changed", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Password can not be Null!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void changeInstructorPassword() {
        String newPassword = JOptionPane.showInputDialog(null, "Enter your new password!", "Password Change", JOptionPane.INFORMATION_MESSAGE);
        if (!newPassword.equals("")) {
            connection.changeInstructorPassword(newPassword);
            JOptionPane.showMessageDialog(null, "Password changed", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Password can not be Null!", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
                createMultiQuestionPage(middlePanel);
                break;
            case "test":
                createTestQuestionPage(middlePanel);
                break;
            default:
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
//        gbc.gridy++;
//        gbc.gridx = 0;
//        panel.add(new JLabel("Number of Questions: "), gbc);
//        gbc.gridx = 1;
//        numQuestion = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
//        panel.add(numQuestion, gbc);
        return panel;
    }

    private void createTrueFalseQuestionPage(JPanel middlePanel) {
        JLabel question = new JLabel("Question" + (pageCounter + 1) + ": ");
        questionArea = new JTextArea();
        questionArea.setPreferredSize(new Dimension(300,100));
        JLabel answer = new JLabel("Answer: ");
        String [] options = { "T", "F" };
        trueFalseOption = new JComboBox(options);
        middlePanel.add(question);
        middlePanel.add(questionArea);
        middlePanel.add(answer);
        middlePanel.add(trueFalseOption);
    }

    private void createMultiQuestionPage(JPanel middlePanel) {
        JLabel question = new JLabel("Question" + (pageCounter + 1) + ": ");
        questionArea = new JTextArea();
        questionArea.setPreferredSize(new Dimension(300,100));
        JLabel answer = new JLabel("Answer: ");
        String[] options = {"A", "B", "C", "D", "E"};
        multipleChoiceOption = new JComboBox(options);
        middlePanel.add(question);
        middlePanel.add(questionArea);
        middlePanel.add(answer);
        middlePanel.add(multipleChoiceOption);
    }

    private void createTestQuestionPage(JPanel middlePanel) {
        JLabel question = new JLabel("Question" + (pageCounter + 1) + ": ");
        questionArea = new JTextArea();
        questionArea.setPreferredSize(new Dimension(300,100));
        JLabel answer = new JLabel("Answer: ");
        testOptions = new ArrayList<>();
        JCheckBox firstOption = new JCheckBox("A");
        JCheckBox secondOption = new JCheckBox("B");
        JCheckBox thirdOption = new JCheckBox("C");
        JCheckBox fourthOption = new JCheckBox("D");
        JCheckBox fifthOption = new JCheckBox("E");
        testOptions.add(firstOption);
        testOptions.add(secondOption);
        testOptions.add(thirdOption);
        testOptions.add(fourthOption);
        testOptions.add(fifthOption);
        middlePanel.add(question);
        middlePanel.add(questionArea);
        middlePanel.add(answer);
        middlePanel.add(firstOption);
        middlePanel.add(secondOption);
        middlePanel.add(thirdOption);
        middlePanel.add(fourthOption);
        middlePanel.add(fifthOption);
    }

    private void createExamBottomButtons(JPanel middlePanel, JPanel bottomPanel) {
        bottomPanel.removeAll();
        JButton next = new JButton("Next");
        next.addActionListener(e -> nextQuestion(middlePanel));
        JButton saveQuestion = new JButton("Save Question");
        saveQuestion.addActionListener(e -> saveQuestion());
        JButton previous = new JButton("Previous");
        previous.addActionListener(e -> previousQuestion(middlePanel));
        bottomPanel.add(previous);
        bottomPanel.add(next);
        bottomPanel.add(saveQuestion);
        bottomPanel.revalidate();
        bottomPanel.repaint();
    }

    private void saveQuestion() {
        if(!questionArea.getText().equals("")) {
            String description = questionArea.getText();
            String type = getExamType();
            String answer = "";
            if (getExamType().equals("TF")) {
                answer = (trueFalseOption.getSelectedItem()).toString();
            } else if (getExamType().equals("multi")) {
                answer = (multipleChoiceOption.getSelectedItem().toString());
            } else {
                for (int i = 0; i < testOptions.size(); i++) {
                    if (testOptions.get(i).isSelected()) answer += testOptions.get(i).getText();
                }
            }
            Question question = new Question(description, answer, type);
            questions.add(question);
            System.out.println((pageCounter + 1) + " " + question);
        } else {
            JOptionPane.showMessageDialog(null, "Please write your question first!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void nextQuestion(JPanel middlePanel) {
        if(!questionArea.getText().equals("")) {
            pageCounter++;
            middlePanel.removeAll();
            createExam(middlePanel);
            middlePanel.revalidate();
            middlePanel.repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Please write your question first!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void previousQuestion(JPanel middlePanel) {
        if (pageCounter>0) {
            pageCounter--;
            middlePanel.removeAll();
            createExam(middlePanel);
            Question previousQuestion = questions.get(pageCounter);
            System.out.println(previousQuestion);
            questionArea.setText(previousQuestion.getDescription());
            middlePanel.revalidate();
            middlePanel.repaint();
        }
    }

    private void saveQuestionsToExams(List<Question> questions) {
        List<Question> temp = new ArrayList<Question>();
        temp = questions;
        exams.add(temp);
    }

}
