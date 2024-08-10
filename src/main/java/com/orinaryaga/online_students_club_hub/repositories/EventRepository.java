package com.orinaryaga.online_students_club_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orinaryaga.online_students_club_hub.models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // Add custom query methods if needed
}
