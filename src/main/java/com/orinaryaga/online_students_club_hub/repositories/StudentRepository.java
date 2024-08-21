package com.orinaryaga.online_students_club_hub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orinaryaga.online_students_club_hub.models.Student;
import com.orinaryaga.online_students_club_hub.models.User;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAll();
    boolean existsByRegistrationNumber(String registrationNumber);
    Student findByRegistrationNumber(String registrationNumber);
}


