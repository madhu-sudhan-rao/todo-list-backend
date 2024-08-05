package com.backend.todo.enums;

public enum Status {

    PENDING("pending"), IN_PROGRESS("in_progress"), COMPLETED("completed");

    private String value;

    public String getValue() {
        return value;
    }

    Status(String value) {
        this.value = value;
    }
}
