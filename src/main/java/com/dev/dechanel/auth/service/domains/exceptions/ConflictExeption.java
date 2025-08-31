package com.dev.dechanel.auth.service.domains.exceptions;

import com.dev.dechanel.auth.service.domains.error.ProjectError;

public class ConflictExeption extends ErrorExeption {

    public ConflictExeption(ProjectError error) {
        super(error);
    }

}
