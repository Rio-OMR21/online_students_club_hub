package com.orinaryaga.online_students_club_hub.services;

import com.orinaryaga.online_students_club_hub.models.Mentor;
import com.orinaryaga.online_students_club_hub.models.User;
import com.orinaryaga.online_students_club_hub.repositories.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class MentorService {

    @Autowired
    private UserService userService;

    @Autowired
    private MentorRepository mentorRepository;

    public Mentor registerMentor(String userName, String firstName, String secondName, String lastName, String gender, String email, String password, String department, String employeeNumber, MultipartFile profilePhoto) throws IOException {
        // Call UserService to register the mentor
        return userService.registerMentor(userName, firstName, secondName, lastName, gender, email, password, department, employeeNumber, profilePhoto);
    }

    public void updateMentor(Long userId, String department, String employeeNumber, MultipartFile profilePhoto) throws IOException {
        Optional<User> userOptional = userService.findUserById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Mentor mentor = user.getMentor();
            if (mentor == null) {
                throw new IllegalArgumentException("No mentor found for user ID: " + userId);
            }

            mentor.setDepartment(department);
            mentor.setEmployeeNumber(employeeNumber);
            user.setMentor(mentor);

            userService.updateMentor(mentor, profilePhoto);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

    public void deleteMentor(Long userId) {
        // Find and delete mentor by user ID
        Optional<User> userOptional = userService.findUserById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getMentor() != null) {
                mentorRepository.delete(user.getMentor());
            }
            userService.deleteUserById(userId);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

    public Optional<Mentor> findMentorById(Long userId) {
        return userService.findUserById(userId)
                .map(User::getMentor);
    }

    public Optional<Mentor> findMentorByEmployeeNumber(String employeeNumber) {
        return userService.findUserByEmployeeNumber(employeeNumber)
                .map(User::getMentor);
    }
}
