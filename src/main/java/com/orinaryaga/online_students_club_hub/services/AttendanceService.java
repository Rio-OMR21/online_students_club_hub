package com.orinaryaga.online_students_club_hub.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orinaryaga.online_students_club_hub.models.Attendance;
import com.orinaryaga.online_students_club_hub.models.Event;
import com.orinaryaga.online_students_club_hub.models.Membership;
import com.orinaryaga.online_students_club_hub.models.User;
import com.orinaryaga.online_students_club_hub.repositories.AttendanceRepository;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private MembershipService membershipService;

    // Record attendance for a membership at an event
     public Attendance recordAttendance(Long eventId, Long memberId) {
        Event event = eventService.getEventById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Membership membership = membershipService.getMembershipById(memberId)
                .orElseThrow(() -> new RuntimeException("Membership not found"));

        // Check if attendance already recorded
        attendanceRepository.findByEventAndMember(event, membership)
                .ifPresent(attendance -> {
                    throw new RuntimeException("Attendance already recorded");
                });

        Attendance attendance = new Attendance();
        attendance.setEvent(event);
        attendance.setMember(membership);
        attendance.setRecordedTime(new Timestamp(System.currentTimeMillis()));

        return attendanceRepository.save(attendance);
    }

    // Get all attendance records for an event
    public List<Attendance> getAttendanceByEvent(Long eventId) {
        Event event = eventService.getEventById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return attendanceRepository.findByEvent(event);
    }

    // Automatically record attendance for the event user
    public void autoRecordCreatorAttendance(Event event) {
        User user = event.getUser();
        Membership membership = membershipService.findByUserAndClub(user.getUserId(), event.getClub().getClubId())
                .orElseThrow(() -> new RuntimeException("Creator is not a member of the club"));

        // Check if attendance already recorded
        attendanceRepository.findByEventAndMember(event, membership)
                .ifPresent(attendance -> {
                    throw new RuntimeException("Attendance already recorded for user");
                });

        Attendance attendance = new Attendance();
        attendance.setEvent(event);
        attendance.setMember(membership);
        attendance.setRecordedTime(new Timestamp(System.currentTimeMillis()));

        attendanceRepository.save(attendance);
    }


    // Get all attendance records for a membership
    public List<Attendance> getAttendanceByMember(Long membershipId) {
        Membership membership = membershipService.getMembershipById(membershipId)
                .orElseThrow(() -> new RuntimeException("Membership not found"));

        return attendanceRepository.findByMember(membership);
    }

    // Delete an attendance record by ID
    public void deleteAttendance(Long attendanceId) {
        attendanceRepository.deleteById(attendanceId);
    }
}




