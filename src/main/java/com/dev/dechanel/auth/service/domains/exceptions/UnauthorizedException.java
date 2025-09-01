package com.dev.dechanel.auth.service.domains.exceptions;

import com.dev.dechanel.auth.service.domains.error.ProjectError;

public class UnauthorizedException extends ErrorException {

    public UnauthorizedException(ProjectError error) {
        super(error);
    }

}
