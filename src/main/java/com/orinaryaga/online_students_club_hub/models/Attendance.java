package com.orinaryaga.online_students_club_hub.models;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @ManyToOne
    @JoinColumn(name = "membershipId")
    private Membership member;

    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;

    private Timestamp recordedTime;

    // Getters and setters

    public Attendance() {
    }

    public Attendance(Long attendanceId, Membership member, Event event, Timestamp recordedTime) {
        this.attendanceId = attendanceId;
        this.member = member;
        this.event = event;
        this.recordedTime = recordedTime;
    }

    public Long getAttendanceId() {
        return this.attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Membership getMember() {
        return this.member;
    }

    public void setMember(Membership member) {
        this.member = member;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Timestamp getRecordedTime() {
        return this.recordedTime;
    }

    public void setRecordedTime(Timestamp recordedTime) {
        this.recordedTime = recordedTime;
    }

    public Attendance attendanceId(Long attendanceId) {
        setAttendanceId(attendanceId);
        return this;
    }

    public Attendance member(Membership member) {
        setMember(member);
        return this;
    }

    public Attendance event(Event event) {
        setEvent(event);
        return this;
    }

    public Attendance recordedTime(Timestamp recordedTime) {
        setRecordedTime(recordedTime);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Attendance)) {
            return false;
        }
        Attendance attendance = (Attendance) o;
        return Objects.equals(attendanceId, attendance.attendanceId) && Objects.equals(member, attendance.member) && Objects.equals(event, attendance.event) && Objects.equals(recordedTime, attendance.recordedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceId, member, event, recordedTime);
    }

    @Override
    public String toString() {
        return "{" +
            " attendanceId='" + getAttendanceId() + "'" +
            ", member='" + getMember() + "'" +
            ", event='" + getEvent() + "'" +
            ", recordedTime='" + getRecordedTime() + "'" +
            "}";
    }
    
}

