package com.dev.dechanel.auth.service.expositions;

import com.dev.dechanel.auth.service.models.CreateUserRequest;
import com.dev.dechanel.auth.service.models.PaginatedResponse;
import com.dev.dechanel.auth.service.models.UserResponse;
import com.dev.dechanel.auth.service.services.users.UserService;
import com.dev.dechanel.auth.service.utils.ApiRoutes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiRoutes.USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    PaginatedResponse<UserResponse> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return userService.getAllUsers(page, size);
    }

    @PostMapping(ApiRoutes.REGISTER)
    UserResponse createUser(@RequestBody @Valid CreateUserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @DeleteMapping(ApiRoutes.USER_BY_ID)
    void deleteUserById(@PathVariable UUID userId) {
        userService.deleteUserById(userId);
    }

}
