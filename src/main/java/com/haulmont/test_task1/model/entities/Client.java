package com.haulmont.test_task1.model.entities;

import java.io.Serializable;

public class Client extends Human implements Serializable {
    private String phoneNumber;

    public Client(long id, String firstName, String lastName, String middleName, String phoneNumber) {
        super(id, firstName, lastName, middleName);
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String toString() {
        return (getId() + 1) + ")"
                + getLastName() + " " + getFirstName().charAt(0) + ". " + getMiddleName().charAt(0) + ". phone: " + phoneNumber;
    }
}
