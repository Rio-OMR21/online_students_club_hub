package com.orinaryaga.online_students_club_hub.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    private String type;

    @ManyToOne
    @JoinColumn(name = "clubId")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private String title;
    private String description;
    private Timestamp startTime;
    private Timestamp endTime;

    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL)
    private Challenge challenge;

    // Getters and setters

    public Event() {
    }

    public Event(Long eventId, String type, Club club, User user, String title, String description, Timestamp startTime, Timestamp endTime, Challenge challenge) {
        this.eventId = eventId;
        this.type = type;
        this.club = club;
        this.user = user;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.challenge = challenge;
    }

    public Long getEventId() {
        return this.eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Club getClub() {
        return this.club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Challenge getChallenge() {
        return this.challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public Event eventId(Long eventId) {
        setEventId(eventId);
        return this;
    }

    public Event type(String type) {
        setType(type);
        return this;
    }

    public Event club(Club club) {
        setClub(club);
        return this;
    }

    public Event user(User user) {
        setUser(user);
        return this;
    }

    public Event title(String title) {
        setTitle(title);
        return this;
    }

    public Event description(String description) {
        setDescription(description);
        return this;
    }

    public Event startTime(Timestamp startTime) {
        setStartTime(startTime);
        return this;
    }

    public Event endTime(Timestamp endTime) {
        setEndTime(endTime);
        return this;
    }

    public Event challenge(Challenge challenge) {
        setChallenge(challenge);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Event)) {
            return false;
        }
        Event event = (Event) o;
        return Objects.equals(eventId, event.eventId) && Objects.equals(type, event.type) && Objects.equals(club, event.club) && Objects.equals(user, event.user) && Objects.equals(title, event.title) && Objects.equals(description, event.description) && Objects.equals(startTime, event.startTime) && Objects.equals(endTime, event.endTime) && Objects.equals(challenge, event.challenge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, type, club, user, title, description, startTime, endTime, challenge);
    }

    @Override
    public String toString() {
        return "{" +
            " eventId='" + getEventId() + "'" +
            ", type='" + getType() + "'" +
            ", club='" + getClub() + "'" +
            ", creator='" + getUser() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", challenge='" + getChallenge() + "'" +
            "}";
    }
    
}

