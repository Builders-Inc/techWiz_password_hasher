package com.naz.techwiz.controllers;


import com.naz.techwiz.entities.User;
import com.naz.techwiz.repositories.UserRepository;
import com.naz.techwiz.services.HashService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class PasswordController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashService hashService;

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        try {
            String encryptedPassword = hashService.passwordEncryption(user.getEncryptedPassword());
            user.setEncryptedPassword(encryptedPassword);
            userRepository.save(user);
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user");
        }
    }
}

