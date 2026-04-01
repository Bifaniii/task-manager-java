package com.bifani.taskmanagerjava.exception;

public class TaskAlreadyFinishedException extends RuntimeException{
    public TaskAlreadyFinishedException(String message) {
        super(message);
    }
}
