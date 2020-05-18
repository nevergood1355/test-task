package com.haulmont.test_task1.model.enums;

public enum Status {
    SCHEDULED, COMPLETED, ACCEPTED_BY_CLIENT, NONE;

    public static Status get(String val) {
        switch (val) {
            case "Запланирован":
                return SCHEDULED;
            case "Выполнен":
                return COMPLETED;
            case "Принят клиентом":
                return ACCEPTED_BY_CLIENT;
        }
        return NONE;
    }

    public String toString() {
        switch (this) {
            case SCHEDULED:
                return "Запланирован";
            case COMPLETED:
                return "Выполнен";
            case ACCEPTED_BY_CLIENT:
                return "Принят клиентом";
        }
        return "";
    }
}
