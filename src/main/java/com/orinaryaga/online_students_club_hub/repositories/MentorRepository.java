package com.orinaryaga.online_students_club_hub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orinaryaga.online_students_club_hub.models.Mentor;
import com.orinaryaga.online_students_club_hub.models.User;

public interface MentorRepository extends JpaRepository<Mentor, Long> {
    List<Mentor> findAll();
    boolean existsByEmployeeNumber(String employeeNumber);
    Mentor findByEmployeeNumber(String employeeNumber);
}