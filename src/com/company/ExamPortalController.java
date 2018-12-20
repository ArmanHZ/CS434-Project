package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ExamPortalController {

    private ExamPortalView view;
    private SQLConnection connection;
    private static final String INSTRUCTOR_PASS = "53894";
    private ArrayList<Question> questions = new ArrayList<>();
    private ButtonGroup buttonGroup;
    private JTextField examTitle;
    private JTextArea questionArea;
    private JComboBox trueFalseOption;
    private JComboBox multipleChoiceOption;
    private ArrayList<JCheckBox> testOptions;
    public static ArrayList<Exam> exams = new ArrayList<>();
    private int pageCounter = 0;
    private int numberOfCorrectAnswers = 0;

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
        } else
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
            System.out.println("Valid User");
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

    public void viewMessages(JPanel middlePanel, JPanel bottomPanel) {
        JPanel temporaryPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        middlePanel.removeAll();
        bottomPanel.removeAll();
        middlePanel.add(temporaryPanel);
        MessageIterator iterator = new MessageIterator(MEDIATOR);
        while (iterator.hasNext()) {
            String message = (String) iterator.next();
            temporaryPanel.add(new JLabel(message), gbc);
            gbc.gridy++;
        }
        middlePanel.revalidate();
        middlePanel.repaint();
        bottomPanel.revalidate();
        bottomPanel.repaint();
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
        questions = new ArrayList<>();  // Whenever the Create Exam button is clicked the previous Questions are cleared.
        pageCounter = 0;
        int result = JOptionPane.showConfirmDialog(null, questionTypeSelect(), "Question Select", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION && !examTitle.getText().equals("")) {
            createExam(middlePanel);
            createExamBottomButtons(middlePanel, bottomPanel);
        } else {
            JOptionPane.showMessageDialog(null, "Please fill all the information!", "Error", JOptionPane.ERROR_MESSAGE);
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
        panel.add(new JLabel("Exam Title: "), gbc);
        gbc.gridx = 1;
        examTitle = new JTextField(10);
        panel.add(examTitle, gbc);
        return panel;
    }

    private void createTrueFalseQuestionPage(JPanel middlePanel) {
        JLabel question = new JLabel("Question" + (pageCounter + 1) + ": ");
        questionArea = new JTextArea();
        questionArea.setPreferredSize(new Dimension(300, 100));
        JLabel answer = new JLabel("Answer: ");
        String[] options = {"T", "F"};
        trueFalseOption = new JComboBox(options);
        middlePanel.add(question);
        middlePanel.add(questionArea);
        middlePanel.add(answer);
        middlePanel.add(trueFalseOption);
    }

    private void createMultiQuestionPage(JPanel middlePanel) {
        JLabel question = new JLabel("Question" + (pageCounter + 1) + ": ");
        questionArea = new JTextArea();
        questionArea.setPreferredSize(new Dimension(300, 100));
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
        questionArea.setPreferredSize(new Dimension(300, 100));
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
        JButton uploadExam = new JButton("Upload Exam");
        uploadExam.addActionListener(e -> uploadExam(middlePanel, bottomPanel));
        bottomPanel.add(previous);
        bottomPanel.add(next);
        bottomPanel.add(saveQuestion);
        bottomPanel.add(uploadExam);
        bottomPanel.revalidate();
        bottomPanel.repaint();
    }

    private void saveQuestion() {
        if (!questionArea.getText().equals("")) {
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
        } else {
            JOptionPane.showMessageDialog(null, "Please write your question first!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void nextQuestion(JPanel middlePanel) {
        if (!questionArea.getText().equals("")) {
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
        if (pageCounter > 0) {
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

    private void uploadExam(JPanel middlePanel, JPanel bottomPanel) {
        if (!questions.isEmpty()) {
            middlePanel.removeAll();
            bottomPanel.removeAll();
            Exam exam = new Exam(examTitle.getText());
            ArrayList<Question> temp = questions;
            exam.setQuestions(temp);
            exams.add(exam);
            middlePanel.revalidate();
            middlePanel.repaint();
            bottomPanel.revalidate();
            bottomPanel.repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Please write your question first!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void viewExams(JPanel middlePanel) {
        middlePanel.removeAll();
        middlePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        for (Exam exam : exams) {
            JLabel label = new JLabel("Exam title: " + exam.toString() + " Created by: " + ExamPortalView.CURRENT_INSTRUCTOR);
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    createStudentExam(middlePanel, exam);
                }
            });
            middlePanel.add(label, gbc);
            gbc.gridy++;
        }
        middlePanel.revalidate();
        middlePanel.repaint();
    }

    public void createStudentExam(JPanel middlePanel, Exam exam) {
        middlePanel.removeAll();
        middlePanel.setLayout(new BorderLayout());
        JPanel questionPanel = new JPanel();
        pageCounter = 0;
        JLabel questionLabel = new JLabel();
        questionLabel.setText(exam.getQuestions().get(pageCounter).getDescription());
        JPanel bottomPanel = new JPanel();
        JButton previous = new JButton("Previous");
        JButton next = new JButton("Next");
        JButton submit = new JButton("Submit Question");
        JButton finish = new JButton("Finish exam");
        JLabel answerLabel = new JLabel("Your Answer: ");
        middlePanel.add(answerLabel);
        JTextField answerField = new JTextField(15);
        questionPanel.add(questionLabel);
        questionPanel.add(answerField);
        next.addActionListener(e -> {
            if ((pageCounter + 1) < exam.getQuestions().size()) {
                pageCounter++;
                questionLabel.setText(exam.getQuestions().get(pageCounter).getDescription());
                answerField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "No more pages left", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        previous.addActionListener(e -> {
            if ((pageCounter - 1) >= 0) {
                pageCounter--;
                questionLabel.setText(exam.getQuestions().get(pageCounter).getDescription());
                answerField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "No more pages left", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        submit.addActionListener(e -> {
            System.out.println(exam.getQuestions().get(pageCounter).getAnswer());
            if (exam.getQuestions().get(pageCounter).getAnswer().equals(answerField.getText())) {
                System.out.println(answerField.getText());
                numberOfCorrectAnswers++;
            }
        });
        finish.addActionListener(e -> {
            System.out.println("Correct answers: " + numberOfCorrectAnswers);
            System.out.println("Exam questions: " + exam.getQuestions().size());
            double score = 100 * ((double) numberOfCorrectAnswers / (double) exam.getQuestions().size());
            int finalScore = (int) score;
            JOptionPane.showMessageDialog(null, "Your Score is: " + finalScore, "Info", JOptionPane.INFORMATION_MESSAGE);
            connection.updateScore(ExamPortalView.CURRENT_STUDENT, finalScore);
            middlePanel.removeAll();
            middlePanel.revalidate();
            middlePanel.repaint();
        });
        bottomPanel.add(previous);
        bottomPanel.add(next);
        bottomPanel.add(submit);
        bottomPanel.add(finish);
        middlePanel.add(questionPanel, BorderLayout.CENTER);
        middlePanel.add(bottomPanel, BorderLayout.SOUTH);
        middlePanel.revalidate();
        middlePanel.repaint();

    }

}
