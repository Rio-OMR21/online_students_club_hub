package com.orinaryaga.online_students_club_hub.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.orinaryaga.online_students_club_hub.models.Profile;
import com.orinaryaga.online_students_club_hub.models.Student;
import com.orinaryaga.online_students_club_hub.models.User;
import com.orinaryaga.online_students_club_hub.repositories.StudentRepository;
import com.orinaryaga.online_students_club_hub.repositories.UserRepository;
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileService profileService;

    // Register a new student
    public Student registerStudent(Student student) {
        validateRegistrationNumber(student.getRegistrationNumber());
        // Save the user first
        User savedUser = userRepository.save(student.getUser());
        // Set the user to the student and save the student
        student.setUser(savedUser);
        return studentRepository.save(student);
    }

    // Update existing student
    public Student updateStudent(Student student) {
        validateRegistrationNumber(student.getRegistrationNumber());
        // Ensure the student exists
        Student existingStudent = studentRepository.findById(student.getUserId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Update the student details
        existingStudent.setProgram(student.getProgram());
        existingStudent.setRegistrationNumber(student.getRegistrationNumber());
        return studentRepository.save(existingStudent);
    }

    // Get student by ID
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Delete student by ID
    public void deleteStudent(Long id) {
        // Ensure to also delete associated user
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        userRepository.delete(student.getUser());
        studentRepository.delete(student);
    }

    // Validate registration number
    private void validateRegistrationNumber(String registrationNumber) {
        boolean exists = studentRepository.existsByRegistrationNumber(registrationNumber);
        if (exists) {
            throw new RuntimeException("Registration number already in use");
        }
    }

    // Upload profile photo for the student
    public String uploadProfilePhoto(Long studentId, MultipartFile file) throws IOException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Profile profile = student.getUser().getProfile();
        String response = profileService.uploadImageToFileSystem(file);
        profile.setImageName(file.getOriginalFilename());
        profile.setImageType(file.getContentType());
        profile.setFilePath(response);
        profileService.saveProfile(profile);
        return response;
    }

    // Download profile photo for the student
    public byte[] downloadProfilePhoto(Long studentId) throws IOException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Profile profile = student.getUser().getProfile();
        return profileService.downloadImageFromFileSystem(profile.getImageName());
    }

    // Delete profile photo for the student
    public void deleteProfilePhoto(Long studentId) throws IOException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Profile profile = student.getUser().getProfile();
        profileService.deleteImageFromFileSystem(profile.getImageName());
        profile.setImageName(null);
        profile.setImageType(null);
        profile.setFilePath(null);
        profileService.saveProfile(profile);
    }
}

