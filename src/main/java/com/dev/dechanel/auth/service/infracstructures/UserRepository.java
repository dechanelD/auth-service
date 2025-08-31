package com.dev.dechanel.auth.service.infracstructures;

import com.dev.dechanel.auth.service.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    Page<UserEntity> findAll(Pageable pageable);
}