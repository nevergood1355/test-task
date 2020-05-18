package com.haulmont.test_task1.model.entities;

import java.io.Serializable;

public class Mechanic extends Human implements Serializable {

    private Long hourlyWage;

    public Mechanic(long id, String firstName, String lastName, String middleName, Long hourlyWage) {
        super(id, firstName, lastName, middleName);
        this.hourlyWage = hourlyWage;
    }

    public Long getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(Long hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public String toString() {
        return (getId() + 1) + ")"
                + getLastName() + " " + getFirstName().charAt(0) + ". " + getMiddleName().charAt(0) + ".  wage: " + hourlyWage;
    }
}
