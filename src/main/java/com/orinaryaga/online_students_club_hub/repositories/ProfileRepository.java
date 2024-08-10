package com.orinaryaga.online_students_club_hub.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orinaryaga.online_students_club_hub.models.Profile;

public interface ProfileRepository extends JpaRepository<Profile,Long> {
    // Optional<Profile> findByName(String fileName);
    Optional<Profile> findByimageName(String imageName);
}
