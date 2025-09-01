package com.dev.dechanel.auth.service.domains.exceptions;

import com.dev.dechanel.auth.service.domains.error.ProjectError;

public class NotFoundException extends ErrorException {

    public NotFoundException(ProjectError error) {
        super(error);
    }

}
