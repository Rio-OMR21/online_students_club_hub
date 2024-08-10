package com.orinaryaga.online_students_club_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orinaryaga.online_students_club_hub.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByRegistrationNumber(String registrationNumber);
}


