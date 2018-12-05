package com.company;

public class ExamPortalController {

    private ExamPortalView view;
    private SQLConnection connection;

    public ExamPortalController() {
        view = new ExamPortalView(this);
//        connection = new SQLConnection();
//        connection.connect();
    }

    public void registerButtonClicked() {
        view.setRegisterPanel();
    }

}
