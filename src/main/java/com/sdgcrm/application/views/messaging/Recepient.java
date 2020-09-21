package com.sdgcrm.application.views.messaging;

import java.util.List;
import java.util.Set;

public class Recepient {

    String recepientsList;
    String subject;
    String message;



    public String getRecepientsList() {
        return recepientsList;
    }

    public void setRecepientsList(String recepientsList) {
        this.recepientsList = recepientsList;
    }

    public Recepient() {

    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
