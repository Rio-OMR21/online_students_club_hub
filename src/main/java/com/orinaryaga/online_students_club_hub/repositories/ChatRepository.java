package com.orinaryaga.online_students_club_hub.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.orinaryaga.online_students_club_hub.models.Chat;
import com.orinaryaga.online_students_club_hub.models.Club;
import com.orinaryaga.online_students_club_hub.models.User;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByClubOrderBySentTimeAsc(Club club);
    List<Chat> findByClubAndUserOrderBySentTimeAsc(Club club, User user);
    List<Chat> findByClubAndSentTimeBetweenOrderBySentTimeAsc(Club club, Timestamp startTime, Timestamp endTime);
    // Add custom query methods if needed
    List<Chat> findByClubClubId(Long clubId); 

    // @Query("SELECT c FROM Chat c WHERE c.club.clubId = :clubId")
    // List<Chat> findByClubId(@Param("clubId") Long clubId);
    
}