package com.example.javaexpress.model.enums;

public enum Feedback {
    OTIMO(5),
    BOM(4),
    MEDIANO(3),
    RUIM(2),
    PESSIMO(1),
    SEM_FEEDBACK(0);

    private int feedback;

    private Feedback(int feedback) {
        this.feedback = feedback;
    }

    public int getFeedback() {
        return feedback;
    }
}
