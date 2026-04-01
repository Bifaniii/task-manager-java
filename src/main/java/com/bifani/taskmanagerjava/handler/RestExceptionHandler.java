package com.bifani.taskmanagerjava.handler;

import com.bifani.taskmanagerjava.dto.ErrorResponse;
import com.bifani.taskmanagerjava.exception.TaskAlreadyFinishedException;
import com.bifani.taskmanagerjava.exception.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    private ResponseEntity<ErrorResponse> taskNotFoundHandler(TaskNotFoundException exception) {
        ErrorResponse threatResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(TaskAlreadyFinishedException.class)
    private ResponseEntity<ErrorResponse> taskAlreadyFinishedHandler(TaskAlreadyFinishedException exception) {
        ErrorResponse threatResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }
}
