package com.dev.dechanel.auth.service.models;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;

}
