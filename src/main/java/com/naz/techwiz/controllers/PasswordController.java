package com.naz.techwiz.controllers;


import com.naz.techwiz.entities.User;
import com.naz.techwiz.repositories.UserRepository;
import com.naz.techwiz.services.PasswordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class PasswordController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/encrypt")
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        try {
            String passwordEncrypted = passwordService.passwordEncryption(user.getEncryptedPassword());
            user.setEncryptedPassword(passwordEncrypted);
            userRepository.save(user);
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user");
        }
    }

    @GetMapping("/{username}")
    public String getUserPassword(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return passwordService.passwordDecryption(user.getEncryptedPassword());
        } else {
            return "User not found";
        }
    }
}

