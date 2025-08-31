package com.dev.dechanel.auth.service;

import com.dev.dechanel.auth.service.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IndexTestRunner implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Insertion des données...");
        //userService.populateUserData(500000); // 500K utilisateurs

        System.out.println("Recherche avec index :");
        userService.searchUserByEmail("user400000@example.com");

    }
}
