package com.orinaryaga.online_students_club_hub.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.orinaryaga.online_students_club_hub.models.Mentor;
import com.orinaryaga.online_students_club_hub.models.Profile;
import com.orinaryaga.online_students_club_hub.models.User;
import com.orinaryaga.online_students_club_hub.repositories.UserRepository;
import com.orinaryaga.online_students_club_hub.repositories.MentorRepository;


@Service
public class MentorService {

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileService profileService;

    // Register a new mentor
    public Mentor registerMentor(Mentor mentor) {
        validateEmployeeNumber(mentor.getEmployeeNumber());
        // Save the user first
        User savedUser = userRepository.save(mentor.getUser());
        // Set the user to the mentor and save the mentor
        mentor.setUser(savedUser);
        return mentorRepository.save(mentor);
    }

    // Update existing mentor
    public Mentor updateMentor(Mentor mentor) {
        validateEmployeeNumber(mentor.getEmployeeNumber());
        // Ensure the mentor exists
        Mentor existingMentor = mentorRepository.findById(mentor.getUserId())
                .orElseThrow(() -> new RuntimeException("Mentor not found"));

        // Update the mentor details
        existingMentor.setEmployeeNumber(mentor.getEmployeeNumber());
        existingMentor.getUser().setUserName(mentor.getUser().getUserName());
        existingMentor.getUser().setFirstName(mentor.getUser().getFirstName());
        existingMentor.getUser().setSecondName(mentor.getUser().getSecondName());
        existingMentor.getUser().setLastName(mentor.getUser().getLastName());
        existingMentor.getUser().setGender(mentor.getUser().getGender());
        existingMentor.getUser().setProfile(mentor.getUser().getProfile());
        userRepository.save(existingMentor.getUser());
        return mentorRepository.save(existingMentor);
    }

    // Get mentor by ID
    public Optional<Mentor> getMentorById(Long id) {
        return mentorRepository.findById(id);
    }

    // Get all mentors
    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
    }

    // Delete mentor by ID
    public void deleteMentor(Long id) {
        // Ensure to also delete associated user
        Mentor mentor = mentorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mentor not found"));
        userRepository.delete(mentor.getUser());
        mentorRepository.delete(mentor);
    }

    // Validate employee number
    private void validateEmployeeNumber(String employeeNumber) {
        if (mentorRepository.existsByEmployeeNumber(employeeNumber)) {
            throw new RuntimeException("Employee number already in use");
        }
    }

    // Upload profile photo for the mentor
    public String uploadProfilePhoto(Long mentorId, MultipartFile file) throws IOException {
        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new RuntimeException("Mentor not found"));
        Profile profile = mentor.getUser().getProfile();
        String response = profileService.uploadImageToFileSystem(file);
        profile.setImageName(file.getOriginalFilename());
        profile.setImageType(file.getContentType());
        profile.setFilePath(response);
        profileService.saveProfile(profile);
        return response;
    }

    // Download profile photo for the mentor
    public byte[] downloadProfilePhoto(Long mentorId) throws IOException {
        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new RuntimeException("Mentor not found"));
        Profile profile = mentor.getUser().getProfile();
        return profileService.downloadImageFromFileSystem(profile.getImageName());
    }

    // Delete profile photo for the mentor
    public void deleteProfilePhoto(Long mentorId) throws IOException {
        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new RuntimeException("Mentor not found"));
        Profile profile = mentor.getUser().getProfile();
        profileService.deleteImageFromFileSystem(profile.getImageName());
        profile.setImageName(null);
        profile.setImageType(null);
        profile.setFilePath(null);
        profileService.saveProfile(profile);
    }
}


