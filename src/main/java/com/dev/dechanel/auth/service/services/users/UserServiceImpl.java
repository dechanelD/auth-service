package com.dev.dechanel.auth.service.services.users;

import com.dev.dechanel.auth.service.domains.error.ProjectError;
import com.dev.dechanel.auth.service.domains.exceptions.ConflictException;
import com.dev.dechanel.auth.service.domains.exceptions.NotFoundException;
import com.dev.dechanel.auth.service.entities.UserEntity;
import com.dev.dechanel.auth.service.infracstructures.UserRepository;
import com.dev.dechanel.auth.service.models.CreateUserRequest;
import com.dev.dechanel.auth.service.models.PaginatedResponse;
import com.dev.dechanel.auth.service.models.UserResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
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
                .orElseThrow(() -> new NotFoundException(ProjectError.ERROR_USER_NOT_FOUND));
        return mapToUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ProjectError.ERROR_USER_NOT_FOUND));
        return mapToUserResponse(user);
    }

    @Override
    public void deleteUserById(UUID id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ProjectError.ERROR_USER_NOT_FOUND));
        userRepository.delete(user);
        LOGGER.info("User with id {} has been deleted.", id);
    }

    @Override
    public UserResponse createUser(CreateUserRequest userRequest) {
        var user = userRepository.findByEmail(userRequest.getEmail());
        if(user.isPresent()){
            throw new ConflictException(ProjectError.ERROR_USER_ALREADY_EXISTS);
        }
        UserEntity newUser = UserEntity.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .build();
        UserEntity savedUser = userRepository.save(newUser);
        LOGGER.info("User with id {} has been created.", savedUser.getUserId());
        return mapToUserResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(UUID id, CreateUserRequest userRequest) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ProjectError.ERROR_USER_NOT_FOUND));

        if (!existingUser.getEmail().equals(userRequest.getEmail())) {
            var userWithEmail = userRepository.findByEmail(userRequest.getEmail());
            if(userWithEmail.isPresent()){
                throw new ConflictException(ProjectError.ERROR_USER_ALREADY_EXISTS);
            }
        }

        if (userRequest.getFirstName() != null) {
            existingUser.setFirstName(userRequest.getFirstName());
        }
        if (userRequest.getLastName() != null) {
            existingUser.setLastName(userRequest.getLastName());
        }
        if (userRequest.getEmail() != null) {
            existingUser.setEmail(userRequest.getEmail());
        }

        UserEntity updatedUser = userRepository.save(existingUser);
        LOGGER.info("User with id {} has been updated.", updatedUser.getUserId());
        return mapToUserResponse(updatedUser);
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
