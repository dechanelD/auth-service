package com.dev.dechanel.auth.service.domains.exceptions;

import com.dev.dechanel.auth.service.domains.error.ProjectError;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ErrorException extends RuntimeException{

    private ProjectError error;
    private  String message;
    private final Timestamp timestamp;

    public ErrorException(ProjectError error, String message) {
        super(message);
        this.error = error;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public ErrorException(String message) {
        super(message);
        this.message = message;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public ErrorException(ProjectError error) {
        super(error.getCode()+" : "+error.getMessage());
        this.message = error.getCode()+" : "+error.getMessage();
        this.error = error;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }
}
