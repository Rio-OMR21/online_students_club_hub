package com.orinaryaga.online_students_club_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orinaryaga.online_students_club_hub.models.Mentor;

public interface MentorRepository extends JpaRepository<Mentor, Long> {
    boolean existsByEmployeeNumber(String employeeNumber);
    Mentor findByEmployeeNumber(String employeeNumber);
}