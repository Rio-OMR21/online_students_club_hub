package com.orinaryaga.online_students_club_hub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orinaryaga.online_students_club_hub.models.Chat;
import com.orinaryaga.online_students_club_hub.models.Club;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    // Add custom query methods if needed
    List<Chat> findByClubId(Long clubId);
}