package com.dev.dechanel.auth.service.services.users;

import com.dev.dechanel.auth.service.models.CreateUserRequest;
import com.dev.dechanel.auth.service.models.PaginatedResponse;
import com.dev.dechanel.auth.service.models.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    public void searchUserByEmail(String email);
    public void populateUserData(int count);
    PaginatedResponse<UserResponse> getAllUsers(int page, int size);
    UserResponse getUserById(UUID id);
    UserResponse getUserByEmail(String email);
    void deleteUserById(UUID id);
    UserResponse createUser(CreateUserRequest userRequest);
    UserResponse updateUser(UUID id, CreateUserRequest userRequest);
}
