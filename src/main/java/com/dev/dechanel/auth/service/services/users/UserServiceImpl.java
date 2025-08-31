package com.dev.dechanel.auth.service.services.users;

import com.dev.dechanel.auth.service.domains.error.ProjectError;
import com.dev.dechanel.auth.service.domains.exceptions.NotFoundExeption;
import com.dev.dechanel.auth.service.entities.UserEntity;
import com.dev.dechanel.auth.service.infracstructures.UserRepository;
import com.dev.dechanel.auth.service.models.PaginatedResponse;
import com.dev.dechanel.auth.service.models.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void searchUserByEmail(String email) {
        long start = System.currentTimeMillis();
        userRepository.findByEmail(email);
        long end = System.currentTimeMillis();
        System.out.println("Temps d'exécution: " + (end - start) + " ms");
    }

    @Override
    public void populateUserData(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            UserEntity user = UserEntity.builder()
                    .firstName("First" + i)
                    .lastName("Last" + i)
                    .email("user" + i + "@example.com")
                    .build();
            userRepository.save(user);
        }
    }

    @Override
    public PaginatedResponse<UserResponse> getAllUsers(int page , int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> users = userRepository.findAll(pageable);

        List<UserResponse> userResponses = users.stream()
                .map(user -> UserResponse.builder()
                        .id(user.getUserId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .build())
                .toList();

        return PaginatedResponse.<UserResponse>builder()
                .items(userResponses)
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .currentPage(users.getNumber())
                .pageSize(users.getSize())
                .first(users.isFirst())
                .last(users.isLast())
                .hasNext(users.hasNext())
                .hasPrevious(users.hasPrevious())
                .numberOfElements(users.getNumberOfElements())
                .build();
    }

    @Override
    public UserResponse getUserById(UUID id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundExeption(ProjectError.ERROR_USER_NOT_FOUND));
        return mapToUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundExeption(ProjectError.ERROR_USER_NOT_FOUND));
        return mapToUserResponse(user);
    }

    private UserResponse mapToUserResponse(UserEntity user) {
        return UserResponse.builder()
                .id(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

}
