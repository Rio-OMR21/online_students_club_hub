package com.orinaryaga.online_students_club_hub.services;

import com.orinaryaga.online_students_club_hub.models.Student;
import com.orinaryaga.online_students_club_hub.models.User;
import com.orinaryaga.online_students_club_hub.repositories.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentRepository studentRepository;

    public Student registerStudent(String userName, String firstName, String secondName, String lastName, String gender, String email, String password, String program, String registrationNumber, MultipartFile profilePhoto) throws IOException {
        // Call UserService to register the student
        return userService.registerStudent(userName, firstName, secondName, lastName, gender, email, password, program, registrationNumber, profilePhoto);
    }

    public void updateStudent(Long userId, String program, String registrationNumber, MultipartFile profilePhoto) throws IOException {
        Optional<User> userOptional = userService.findUserById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Student student = user.getStudent();
            if (student == null) {
                throw new IllegalArgumentException("No student found for user ID: " + userId);
            }

            student.setProgram(program);
            student.setRegistrationNumber(registrationNumber);
            user.setStudent(student);

            userService.updateStudent(student, profilePhoto);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

    public void deleteStudent(Long userId) {
        // Find and delete student by user ID
        Optional<User> userOptional = userService.findUserById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getStudent() != null) {
                studentRepository.delete(user.getStudent());
            }
            userService.deleteUserById(userId);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

    public Optional<Student> findStudentById(Long userId) {
        return userService.findUserById(userId)
                .map(User::getStudent);
    }

    public Optional<Student> findStudentByRegistrationNumber(String registrationNumber) {
        return userService.findUserByRegistrationNumber(registrationNumber)
                .map(User::getStudent);
    }
}
