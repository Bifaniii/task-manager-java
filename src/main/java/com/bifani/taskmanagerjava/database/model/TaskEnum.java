package com.bifani.taskmanagerjava.database.model;

public enum TaskEnum {
    PENDING("Pendente"),
    DONE("Feito");

    private final String description;

    TaskEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
