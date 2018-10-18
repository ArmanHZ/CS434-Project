package com.company;

public class ExamPortalController {

    private ExamPortalView view;

    public ExamPortalController() {
        view = new ExamPortalView(this);
    }

    public void registerButtonClicked() {
        view.setRegisterPanel();
    }
}
