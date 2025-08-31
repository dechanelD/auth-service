package com.dev.dechanel.auth.service.domains.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProjectError {
    ERROR_USER_NOT_FOUND("404", "User not found"),
    ERROR_INVALID_CREDENTIALS("401", "Invalid credentials"),
    ERROR_USER_ALREADY_EXISTS("409", "User already exists with the given email");

    private final String code;
    private final String message;
}
