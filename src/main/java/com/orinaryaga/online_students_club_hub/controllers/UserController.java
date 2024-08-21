package com.orinaryaga.online_students_club_hub.controllers;

import com.orinaryaga.online_students_club_hub.models.Student;
import com.orinaryaga.online_students_club_hub.models.User;
import com.orinaryaga.online_students_club_hub.repositories.MentorRepository;
import com.orinaryaga.online_students_club_hub.repositories.StudentRepository;
import com.orinaryaga.online_students_club_hub.models.Mentor;
import com.orinaryaga.online_students_club_hub.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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


    // Find Student by ID
    @GetMapping("/find/student/{userId}")
    public ResponseEntity<Student> findStudentById(@PathVariable Long userId) {
        Optional<User> userOptional = userService.findUserById(userId);
        if (userOptional.isPresent() && userOptional.get().getStudent() != null) {
            return ResponseEntity.ok(userOptional.get().getStudent());
        }
        return ResponseEntity.notFound().build();
    }

    // Find Student by Registration Number
    @GetMapping("/find/student/registration-number/{registrationNumber}")
    public ResponseEntity<Student> findStudentByRegistrationNumber(@PathVariable String registrationNumber) {
        Optional<User> userOptional = userService.findUserByRegistrationNumber(registrationNumber);
        if (userOptional.isPresent() && userOptional.get().getStudent() != null) {
            return ResponseEntity.ok(userOptional.get().getStudent());
        }
        return ResponseEntity.notFound().build();
    }

    // Check if Student Exists by Registration Number
    @GetMapping("/exists/student/registration-number/{registrationNumber}")
    public ResponseEntity<Boolean> existsByRegistrationNumber(@PathVariable String registrationNumber) {
        boolean exists = userService.existsByRegistrationNumber(registrationNumber);
        return ResponseEntity.ok(exists);
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



    // Find Mentor by ID
    @GetMapping("/find/mentor/{userId}")
    public ResponseEntity<Mentor> findMentorById(@PathVariable Long userId) {
        Optional<User> userOptional = userService.findUserById(userId);
        if (userOptional.isPresent() && userOptional.get().getMentor() != null) {
            return ResponseEntity.ok(userOptional.get().getMentor());
        }
        return ResponseEntity.notFound().build();
    }

    // Find Mentor by Employee Number
    @GetMapping("/find/mentor/employee-number/{employeeNumber}")
    public ResponseEntity<Mentor> findMentorByEmployeeNumber(@PathVariable String employeeNumber) {
        Optional<User> userOptional = userService.findUserByEmployeeNumber(employeeNumber);
        if (userOptional.isPresent() && userOptional.get().getMentor() != null) {
            return ResponseEntity.ok(userOptional.get().getMentor());
        }
        return ResponseEntity.notFound().build();
    }

    // Check if Mentor Exists by Employee Number
    @GetMapping("/exists/mentor/employee-number/{employeeNumber}")
    public ResponseEntity<Boolean> existsByEmployeeNumber(@PathVariable String employeeNumber) {
        boolean exists = userService.existsByEmployeeNumber(employeeNumber);
        return ResponseEntity.ok(exists);
    }

    // Find User by Username
    @GetMapping("/find/user/username/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username) {
        Optional<User> userOptional = userService.findUserByUsername(username);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

   
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String term) {
        List<User> users = userService.searchUsersAcrossEntities(term);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }
    

    /////////////////////////////////////////////////////

// Read/Retrieve Student and associated User data by userId
@GetMapping("/read/student/{userId}")
public ResponseEntity<Student> getStudentWithUserData(@PathVariable Long userId) {
    Optional<User> userOptional = userService.findUserById(userId);
    if (userOptional.isPresent() && userOptional.get().getStudent() != null) {
        return ResponseEntity.ok(userOptional.get().getStudent());
    }
    return ResponseEntity.notFound().build();
}


// Read/Retrieve Mentor and associated User data by userId
@GetMapping("/read/mentor/{userId}")
public ResponseEntity<Mentor> getMentorWithUserData(@PathVariable Long userId) {
    Optional<User> userOptional = userService.findUserById(userId);
    if (userOptional.isPresent() && userOptional.get().getMentor() != null) {
        return ResponseEntity.ok(userOptional.get().getMentor());
    }
    return ResponseEntity.notFound().build();
}


// Update Student and associated User data by userId
@PutMapping("/update/student/{userId}")
public ResponseEntity<Void> updateStudentWithUserData(
    @PathVariable Long userId,
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
    Optional<User> userOptional = userService.findUserById(userId);
    if (userOptional.isPresent() && userOptional.get().getStudent() != null) {
        User user = userOptional.get();
        // Update User fields
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setLastName(lastName);
        user.setGender(gender);
        user.setEmail(email);
        user.setPassword(password);
        
        // Update Student fields
        Student student = user.getStudent();
        student.setProgram(program);
        student.setRegistrationNumber(registrationNumber);
        
        userService.updateStudent(student, profilePhoto);
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
}


// Update Mentor and associated User data by userId
@PutMapping("/update/mentor/{userId}")
public ResponseEntity<Void> updateMentorWithUserData(
    @PathVariable Long userId,
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
    Optional<User> userOptional = userService.findUserById(userId);
    if (userOptional.isPresent() && userOptional.get().getMentor() != null) {
        User user = userOptional.get();
        // Update User fields
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setLastName(lastName);
        user.setGender(gender);
        user.setEmail(email);
        user.setPassword(password);
        
        // Update Mentor fields
        Mentor mentor = user.getMentor();
        mentor.setDepartment(department);
        mentor.setEmployeeNumber(employeeNumber);
        
        userService.updateMentor(mentor, profilePhoto);
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
}


// Delete Student and associated User data by userId
@DeleteMapping("/delete/student/{userId}")
public ResponseEntity<Void> deleteStudentWithUserData(@PathVariable Long userId) {
    Optional<User> userOptional = userService.findUserById(userId);
    if (userOptional.isPresent() && userOptional.get().getStudent() != null) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
}


// Delete Mentor and associated User data by userId
@DeleteMapping("/delete/mentor/{userId}")
public ResponseEntity<Void> deleteMentorWithUserData(@PathVariable Long userId) {
    Optional<User> userOptional = userService.findUserById(userId);
    if (userOptional.isPresent() && userOptional.get().getMentor() != null) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
}


// Read/Retrieve all Students with corresponding User data
@GetMapping("/read/all/students")
public ResponseEntity<List<Student>> getAllStudentsWithUserData() {
    List<Student> students = userService.getAllStudentsWithUserData();
    if (students.isEmpty()) {
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(students);
}


// Read/Retrieve all Mentors with corresponding User data
@GetMapping("/read/all/mentors")
public ResponseEntity<List<Mentor>> getAllMentorsWithUserData() {
    List<Mentor> mentors = userService.getAllMentorsWithUserData();
    if (mentors.isEmpty()) {
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(mentors);
}


// Read/Retrieve all Users with corresponding data from either Student or Mentor
@GetMapping("/read/all")
public ResponseEntity<List<User>> getAllUsersWithRoleData() {
    List<User> users = userService.getAllUsersWithRoleData();
    if (users.isEmpty()) {
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(users);
}


    
}

