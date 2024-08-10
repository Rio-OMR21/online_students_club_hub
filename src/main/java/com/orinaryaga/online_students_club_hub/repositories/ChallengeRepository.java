package com.orinaryaga.online_students_club_hub.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orinaryaga.online_students_club_hub.models.Challenge;
import com.orinaryaga.online_students_club_hub.models.Event;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByEvent(Event event);
}
