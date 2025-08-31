package com.dev.dechanel.auth.service.expositions;

import com.dev.dechanel.auth.service.models.PaginatedResponse;
import com.dev.dechanel.auth.service.models.UserResponse;
import com.dev.dechanel.auth.service.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
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

}
