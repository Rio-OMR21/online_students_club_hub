package com.orinaryaga.online_students_club_hub.controllers;

import com.orinaryaga.online_students_club_hub.models.Student;
import com.orinaryaga.online_students_club_hub.models.User;
import com.orinaryaga.online_students_club_hub.models.Mentor;
import com.orinaryaga.online_students_club_hub.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register Student
    @PostMapping("/register/student")
    public ResponseEntity<Student> registerStudent(
        @RequestParam("userName") String userName,
        @RequestParam("firstName") String firstName,
        @RequestParam("secondName") String secondName,
        @RequestParam("lastName") String lastName,
        @RequestParam("gender") String gender,
        @RequestParam("email") String email,
        @RequestParam("password") String password,
        @RequestParam("program") String program,
        @RequestParam("registrationNumber") String registrationNumber,
        @RequestParam("profilePhoto") MultipartFile profilePhoto
    ) throws IOException {
        Student registeredStudent = userService.registerStudent(userName, firstName, secondName, lastName, gender, email, password, program, registrationNumber, profilePhoto);
        return ResponseEntity.ok(registeredStudent);
    }

    // Update Student
    @PutMapping("/student/{userId}")
    public ResponseEntity<Void> updateStudent(
        @PathVariable Long userId,
        @RequestParam("program") String program,
        @RequestParam("registrationNumber") String registrationNumber,
        @RequestParam("profilePhoto") MultipartFile profilePhoto
    ) throws IOException {
        Optional<User> userOptional = userService.findUserById(userId);
        if (userOptional.isPresent() && userOptional.get().getStudent() != null) {
            Student student = userOptional.get().getStudent();
            student.setProgram(program);
            student.setRegistrationNumber(registrationNumber);
            userService.updateStudent(student, profilePhoto);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Delete Student
    @DeleteMapping("/student/{userId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long userId) {
        Optional<User> userOptional = userService.findUserById(userId);
        if (userOptional.isPresent() && userOptional.get().getStudent() != null) {
            userService.deleteUserById(userId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Find Student by ID
    @GetMapping("/student/{userId}")
    public ResponseEntity<Student> findStudentById(@PathVariable Long userId) {
        Optional<User> userOptional = userService.findUserById(userId);
        if (userOptional.isPresent() && userOptional.get().getStudent() != null) {
            return ResponseEntity.ok(userOptional.get().getStudent());
        }
        return ResponseEntity.notFound().build();
    }

    // Find Student by Registration Number
    @GetMapping("/student/registration-number/{registrationNumber}")
    public ResponseEntity<Student> findStudentByRegistrationNumber(@PathVariable String registrationNumber) {
        Optional<User> userOptional = userService.findUserByRegistrationNumber(registrationNumber);
        if (userOptional.isPresent() && userOptional.get().getStudent() != null) {
            return ResponseEntity.ok(userOptional.get().getStudent());
        }
        return ResponseEntity.notFound().build();
    }

    // Register Mentor
    @PostMapping("/register/mentor")
    public ResponseEntity<Mentor> registerMentor(
        @RequestParam("userName") String userName,
        @RequestParam("firstName") String firstName,
        @RequestParam("secondName") String secondName,
        @RequestParam("lastName") String lastName,
        @RequestParam("gender") String gender,
        @RequestParam("email") String email,
        @RequestParam("password") String password,
        @RequestParam("department") String department,
        @RequestParam("employeeNumber") String employeeNumber,
        @RequestParam("profilePhoto") MultipartFile profilePhoto
    ) throws IOException {
        Mentor registeredMentor = userService.registerMentor(userName, firstName, secondName, lastName, gender, email, password, department, employeeNumber, profilePhoto);
        return ResponseEntity.ok(registeredMentor);
    }

    // Update Mentor
    @PutMapping("/mentor/{userId}")
    public ResponseEntity<Void> updateMentor(
        @PathVariable Long userId,
        @RequestParam("department") String department,
        @RequestParam("employeeNumber") String employeeNumber,
        @RequestParam("profilePhoto") MultipartFile profilePhoto
    ) throws IOException {
        Optional<User> userOptional = userService.findUserById(userId);
        if (userOptional.isPresent() && userOptional.get().getMentor() != null) {
            Mentor mentor = userOptional.get().getMentor();
            mentor.setDepartment(department);
            mentor.setEmployeeNumber(employeeNumber);
            userService.updateMentor(mentor, profilePhoto);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Delete Mentor
    @DeleteMapping("/mentor/{userId}")
    public ResponseEntity<Void> deleteMentor(@PathVariable Long userId) {
        Optional<User> userOptional = userService.findUserById(userId);
        if (userOptional.isPresent() && userOptional.get().getMentor() != null) {
            userService.deleteUserById(userId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Find Mentor by ID
    @GetMapping("/mentor/{userId}")
    public ResponseEntity<Mentor> findMentorById(@PathVariable Long userId) {
        Optional<User> userOptional = userService.findUserById(userId);
        if (userOptional.isPresent() && userOptional.get().getMentor() != null) {
            return ResponseEntity.ok(userOptional.get().getMentor());
        }
        return ResponseEntity.notFound().build();
    }

    // Find Mentor by Employee Number
    @GetMapping("/mentor/employee-number/{employeeNumber}")
    public ResponseEntity<Mentor> findMentorByEmployeeNumber(@PathVariable String employeeNumber) {
        Optional<User> userOptional = userService.findUserByEmployeeNumber(employeeNumber);
        if (userOptional.isPresent() && userOptional.get().getMentor() != null) {
            return ResponseEntity.ok(userOptional.get().getMentor());
        }
        return ResponseEntity.notFound().build();
    }
}
