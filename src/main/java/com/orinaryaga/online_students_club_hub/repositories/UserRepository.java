package com.orinaryaga.online_students_club_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.orinaryaga.online_students_club_hub.models.User;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.student.registrationNumber = ?1")
    boolean existsByRegistrationNumber(String registrationNumber);

    // Find a user by username
    Optional<User> findByUserName(String userName);

    // Fetch a user who is either a student or mentor, but not both
    @Query("SELECT u FROM User u WHERE (u.student IS NOT NULL AND u.mentor IS NULL) OR (u.student IS NULL AND u.mentor IS NOT NULL)")
    List<User> findUsersWhoAreEitherStudentOrMentor();

    // Fetch users based on role in Membership entity
    @Query("SELECT u FROM User u JOIN u.memberships m WHERE m.role = ?1")
    List<User> findUsersByMembershipRole(String role);

    @Query("SELECT u FROM User u WHERE u.student.registrationNumber = ?1")
    Optional<User> findByRegistrationNumber(String registrationNumber);

    @Query("SELECT u FROM User u WHERE u.mentor.employeeNumber = ?1")
    Optional<User> findByEmployeeNumber(String employeeNumber);

   
}
