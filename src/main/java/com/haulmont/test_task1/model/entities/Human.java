package com.haulmont.test_task1.model.entities;

public abstract class Human extends AbstractEntity {

    private long id;
    private String firstName;
    private String middleName;
    private String lastName;

    Human(long id, String firstName, String lastName, String middleName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    protected void setId(long id) {

        this.id = id;
    }

    public long getId() {
        return id;
    }

    public boolean equals(Human h) {
        return (id == h.id);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getFullName() {
        return firstName + " " + lastName + " " + middleName;
    }

    public boolean contains(String excerpt) {
        String[] split = excerpt.split(" ");
        for (String s : split) {
            if (firstName.toLowerCase().contains(s.toLowerCase())
                    || lastName.toLowerCase().contains(s.toLowerCase())
                    || middleName.toLowerCase().contains(s.toLowerCase()))

                return true;
        }
        return false;
    }
}

