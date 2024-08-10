package com.orinaryaga.online_students_club_hub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.orinaryaga.online_students_club_hub.models.User;
import com.orinaryaga.online_students_club_hub.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.registerUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Update existing user
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Upload profile photo
    @PostMapping("/{userId}/profile-photo")
    public ResponseEntity<String> uploadProfilePhoto(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        try {
            String response = userService.uploadProfilePhoto(userId, file);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Download profile photo
    @GetMapping("/{userId}/profile-photo")
    public ResponseEntity<byte[]> downloadProfilePhoto(@PathVariable Long userId) {
        try {
            byte[] photo = userService.downloadProfilePhoto(userId);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Adjust if needed
                    .body(photo);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete profile photo
    @DeleteMapping("/{userId}/profile-photo")
    public ResponseEntity<Void> deleteProfilePhoto(@PathVariable Long userId) {
        try {
            userService.deleteProfilePhoto(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Authenticate user
    @PostMapping("/authenticate")
    public ResponseEntity<User> authenticateUser(@RequestParam String email, @RequestParam String password) {
        User user = userService.authenticateUser(email, password);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Change user password
    @PostMapping("/{userId}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long userId, @RequestParam String newPassword) {
        userService.changePassword(userId, newPassword);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Update user email
    @PutMapping("/{userId}/email")
    public ResponseEntity<User> updateUserEmail(@PathVariable Long userId, @RequestParam String newEmail) {
        User updatedUser = userService.updateUserEmail(userId, newEmail);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
