package com.orinaryaga.online_students_club_hub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.orinaryaga.online_students_club_hub.models.Attendance;
import com.orinaryaga.online_students_club_hub.services.AttendanceService;
import java.util.List;

@RestController
@RequestMapping("/api/events/{eventId}/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // Record attendance for a membership at an event
    @PostMapping("/record/{membershipId}")
    public ResponseEntity<Attendance> recordAttendance(
            @PathVariable Long eventId,
            @PathVariable Long membershipId) {
        try {
            Attendance attendance = attendanceService.recordAttendance(eventId, membershipId);
            return new ResponseEntity<>(attendance, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get all attendance records for an event
    @GetMapping
    public ResponseEntity<List<Attendance>> getAttendanceByEvent(@PathVariable Long eventId) {
        try {
            List<Attendance> attendanceRecords = attendanceService.getAttendanceByEvent(eventId);
            return new ResponseEntity<>(attendanceRecords, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all attendance records for a membership
    @GetMapping("/members/{membershipId}")
    public ResponseEntity<List<Attendance>> getAttendanceByMember(
            @PathVariable Long eventId,
            @PathVariable Long membershipId) {
        try {
            List<Attendance> attendanceRecords = attendanceService.getAttendanceByMember(membershipId);
            return new ResponseEntity<>(attendanceRecords, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an attendance record by ID
    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long attendanceId) {
        try {
            attendanceService.deleteAttendance(attendanceId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
