package com.orinaryaga.online_students_club_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.orinaryaga.online_students_club_hub.models.User;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Add custom query methods if needed
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);  // Ensure this method exists
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.student.registrationNumber = ?1")
    boolean existsByRegistrationNumber(String registrationNumber);
}
