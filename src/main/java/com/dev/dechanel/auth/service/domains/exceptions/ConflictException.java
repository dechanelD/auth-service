package com.dev.dechanel.auth.service.domains.exceptions;

import com.dev.dechanel.auth.service.domains.error.ProjectError;

public class ConflictException extends ErrorException {

    public ConflictException(ProjectError error) {
        super(error);
    }

}
