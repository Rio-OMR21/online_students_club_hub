package com.orinaryaga.online_students_club_hub.services;

import com.orinaryaga.online_students_club_hub.models.Student;
import com.orinaryaga.online_students_club_hub.models.Mentor;
import com.orinaryaga.online_students_club_hub.models.User;
import com.orinaryaga.online_students_club_hub.repositories.MentorRepository;
import com.orinaryaga.online_students_club_hub.repositories.StudentRepository;
import com.orinaryaga.online_students_club_hub.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private StudentRepository studentRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final String imagePath = "C:/Users/omr/Documents/GitHub/online_students_club_hub/src/main/resources/image_db/";

    
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public boolean existsByRegistrationNumber(String registrationNumber) {
        return studentRepository.existsByRegistrationNumber(registrationNumber);
    }

    public boolean existsByEmployeeNumber(String employeeNumber) {
        return mentorRepository.existsByEmployeeNumber(employeeNumber);
    }


    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

     // Save a new User with profile photo
    public User saveUser(User user, MultipartFile profilePhoto) throws IOException {
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            String filePath = saveProfilePhoto(profilePhoto);
            user.setProfilePhoto(filePath);
        }
        return userRepository.save(user);
    }

    // Save profile photo to local file system
    private String saveProfilePhoto(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = Paths.get(imagePath, fileName).toString();
        File destinationFile = new File(filePath);
        file.transferTo(destinationFile);
        return fileName;
    }

    // Register a new Student along with a new User entity
    public Student registerStudent(String userName, String firstName, String secondName, String lastName, String gender, String email, String password, String program, String registrationNumber, MultipartFile profilePhoto) throws IOException {
        User user = new User();

        // Set User properties from the provided data
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setLastName(lastName);
        user.setGender(gender);
        user.setEmail(email);
        user.setPassword(password);

        // Create and set the Student object
        Student student = new Student();
        student.setProgram(program);
        student.setRegistrationNumber(registrationNumber);
        student.setUser(user);

        // Establish bi-directional relationship
        user.setStudent(student);

        // Save the User and Student entities
        user = saveUser(user, profilePhoto);
        return studentRepository.save(student);
    }

    // Register a new Mentor along with a new User entity
    public Mentor registerMentor(String userName, String firstName, String secondName, String lastName, String gender, String email, String password, String department, String employeeNumber, MultipartFile profilePhoto) throws IOException {
        User user = new User();

        // Set User properties from the provided data
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setLastName(lastName);
        user.setGender(gender);
        user.setEmail(email);
        user.setPassword(password);

        // Create and set the Mentor object
        Mentor mentor = new Mentor();
        mentor.setDepartment(department);
        mentor.setEmployeeNumber(employeeNumber);
        mentor.setUser(user);

        // Establish bi-directional relationship
        user.setMentor(mentor);

        // Save the User and Mentor entities
        user = saveUser(user, profilePhoto);
        return mentorRepository.save(mentor);
    }


    public Optional<User> findUserByEmployeeNumber(String employeeNumber) {
        Mentor mentor = mentorRepository.findByEmployeeNumber(employeeNumber);
        if (mentor != null) {
            return Optional.of(mentor.getUser());
        }
        return Optional.empty();
    }

    public Optional<User> findUserByRegistrationNumber(String registrationNumber) {
        Student student = studentRepository.findByRegistrationNumber(registrationNumber);
        if (student != null) {
            return Optional.of(student.getUser());
        }
        return Optional.empty();
    }

    ///////////////////////
    ////////////////////////////////
     // Fetch all students
     public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Fetch all mentors
    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
    }

    // Fetch all users (both students and mentors)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Delete a student by ID
    public void deleteStudentById(Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            userRepository.delete(student.getUser());
            studentRepository.delete(student);
        } else {
            logger.error("Student with ID: {} not found", studentId);
            throw new IllegalArgumentException("Student not found");
        }
    }

    // Delete a mentor by ID
    public void deleteMentorById(Long mentorId) {
        Optional<Mentor> mentorOptional = mentorRepository.findById(mentorId);
        if (mentorOptional.isPresent()) {
            Mentor mentor = mentorOptional.get();
            userRepository.delete(mentor.getUser());
            mentorRepository.delete(mentor);
        } else {
            logger.error("Mentor with ID: {} not found", mentorId);
            throw new IllegalArgumentException("Mentor not found");
        }
    }

    // Update a student
    public void updateStudent(Student student, MultipartFile profilePhoto) throws IOException {
        User user = student.getUser();
        if (user.getMentor() != null) {
            logger.error("User with ID: {} is already registered as a mentor", user.getUserId());
            throw new IllegalArgumentException("User cannot be both a student and a mentor");
        }
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            String filePath = saveProfilePhoto(profilePhoto);
            user.setProfilePhoto(filePath);
        }
        userRepository.save(user);
        studentRepository.save(student);
    }

    // Update a mentor
    public void updateMentor(Mentor mentor, MultipartFile profilePhoto) throws IOException {
        User user = mentor.getUser();
        if (user.getStudent() != null) {
            logger.error("User with ID: {} is already registered as a student", user.getUserId());
            throw new IllegalArgumentException("User cannot be both a student and a mentor");
        }
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            String filePath = saveProfilePhoto(profilePhoto);
            user.setProfilePhoto(filePath);
        }
        userRepository.save(user);
        mentorRepository.save(mentor);
    }

    // Search users by field
    public List<User> searchUsers(String field, String term) {
        switch (field) {
            case "userName":
                return userRepository.findByUserNameContaining(term);
            case "email":
                return userRepository.findByEmailContaining(term);
            case "firstName":
                return userRepository.findByFirstNameContaining(term);
            case "lastName":
                return userRepository.findByLastNameContaining(term);
            case "registrationNumber":
                Student student = studentRepository.findByRegistrationNumber(term);
                return student != null ? Collections.singletonList(student.getUser()) : Collections.emptyList();
            case "employeeNumber":
                Mentor mentor = mentorRepository.findByEmployeeNumber(term);
                return mentor != null ? Collections.singletonList(mentor.getUser()) : Collections.emptyList();
            default:
                return Collections.emptyList();
        }
    }

    //////////////////////////////////

    public List<Student> getAllStudentsWithUserData() {
    List<User> users = userRepository.findAll();
    return users.stream()
                .filter(user -> user.getStudent() != null)
                .map(User::getStudent)
                .collect(Collectors.toList());
}

public List<Mentor> getAllMentorsWithUserData() {
    List<User> users = userRepository.findAll();
    return users.stream()
                .filter(user -> user.getMentor() != null)
                .map(User::getMentor)
                .collect(Collectors.toList());
}


public List<User> getAllUsersWithRoleData() {
    return userRepository.findAll();
}


public List<User> searchUsersAcrossEntities(String term) {
    if (!StringUtils.hasText(term)) {
        return new ArrayList<>();
    }

    return userRepository.findAll().stream()
        .filter(user -> 
            // User attributes
            (StringUtils.hasText(user.getUserName()) && user.getUserName().toLowerCase().contains(term.toLowerCase())) ||
            (StringUtils.hasText(user.getFirstName()) && user.getFirstName().toLowerCase().contains(term.toLowerCase())) ||
            (StringUtils.hasText(user.getSecondName()) && user.getSecondName().toLowerCase().contains(term.toLowerCase())) ||
            (StringUtils.hasText(user.getLastName()) && user.getLastName().toLowerCase().contains(term.toLowerCase())) ||
            (StringUtils.hasText(user.getGender()) && user.getGender().toLowerCase().contains(term.toLowerCase())) ||
            (StringUtils.hasText(user.getEmail()) && user.getEmail().toLowerCase().contains(term.toLowerCase())) ||

            // Student attributes
            (user.getStudent() != null && 
                ((StringUtils.hasText(user.getStudent().getProgram()) && user.getStudent().getProgram().toLowerCase().contains(term.toLowerCase())) ||
                 (StringUtils.hasText(user.getStudent().getRegistrationNumber()) && user.getStudent().getRegistrationNumber().toLowerCase().contains(term.toLowerCase())))) ||

            // Mentor attributes
            (user.getMentor() != null && 
                ((StringUtils.hasText(user.getMentor().getDepartment()) && user.getMentor().getDepartment().toLowerCase().contains(term.toLowerCase())) ||
                 (StringUtils.hasText(user.getMentor().getEmployeeNumber()) && user.getMentor().getEmployeeNumber().toLowerCase().contains(term.toLowerCase()))))
        )
        .collect(Collectors.toList());
}


    
}
