package com.dev.dechanel.auth.service.domains.exceptions;

import com.dev.dechanel.auth.service.domains.error.ProjectError;

public class InvalidUUIDException extends ErrorException {

    public InvalidUUIDException(ProjectError error) {
        super(error);
    }

    public  InvalidUUIDException(String message) {
        super(message);
    }

}
