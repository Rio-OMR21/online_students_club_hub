package com.orinaryaga.online_students_club_hub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.orinaryaga.online_students_club_hub.models.Profile;
import com.orinaryaga.online_students_club_hub.models.User;
import com.orinaryaga.online_students_club_hub.repositories.ProfileRepository;
import com.orinaryaga.online_students_club_hub.repositories.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;


    public User registerUser(User user) {
        validateEmail(user.getEmail());
        // Store password as plain text
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword()); // No encryption
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        validateEmail(user.getEmail());
        if (user.getPassword() != null) {
            // Store password as plain text
            // user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setPassword(user.getPassword()); // No encryption
        }
        return userRepository.save(user);
    }

    public String uploadProfilePhoto(Long userId, MultipartFile file) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        String response = profileService.uploadImageToFileSystem(file);
        // Update the profile with the new image details
        Profile profile = user.getProfile();
        profile.setImageName(file.getOriginalFilename());
        profile.setImageType(file.getContentType());
        profile.setFilePath(response);
        profileRepository.save(profile);
        return response;
    }

    public byte[] downloadProfilePhoto(Long userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return profileService.downloadImageFromFileSystem(user.getProfile().getImageName());
    }

    public void deleteProfilePhoto(Long userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Profile profile = user.getProfile();
        profileService.deleteImageFromFileSystem(profile.getImageName());
        profile.setImageName(null);
        profile.setImageType(null);
        profile.setFilePath(null);
        profileRepository.save(profile);
    }

    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        // No password encryption comparison
        if (password.equals(user.getPassword())) {
            return user;
        } else {
            throw new RuntimeException("Invalid password");
        }
    }

    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        // Store password as plain text
        // user.setPassword(passwordEncoder.encode(newPassword));
        user.setPassword(newPassword); // No encryption
        userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Additional business logic

    public User updateUserEmail(Long userId, String newEmail) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        validateEmail(newEmail);
        user.setEmail(newEmail);
        return userRepository.save(user);
    }


    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already in use");
        }
    }
}


