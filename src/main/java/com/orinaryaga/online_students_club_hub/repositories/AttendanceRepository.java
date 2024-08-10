package com.orinaryaga.online_students_club_hub.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orinaryaga.online_students_club_hub.models.Attendance;
import com.orinaryaga.online_students_club_hub.models.Event;
import com.orinaryaga.online_students_club_hub.models.Membership;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    // Add custom query methods if needed
    List<Attendance> findByEvent(Event event);
    List<Attendance> findByMember(Membership member);
    Optional<Attendance> findByEventAndMember(Event event, Membership member);
}