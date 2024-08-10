package com.orinaryaga.online_students_club_hub.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import java.util.Objects;

@Entity
public class Challenge {
    @Id
    private Long eventId;

    private String question;
    private String answer;

    @OneToOne
    @MapsId
    @JoinColumn(name = "eventId", referencedColumnName = "eventId", nullable = false)
    private Event event;

    // Getters and setters


    public Challenge() {
    }

    public Challenge(Long eventId, String question, String answer, Event event) {
        this.eventId = eventId;
        this.question = question;
        this.answer = answer;
        this.event = event;
    }

    public Long getEventId() {
        return this.eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Challenge eventId(Long eventId) {
        setEventId(eventId);
        return this;
    }

    public Challenge question(String question) {
        setQuestion(question);
        return this;
    }

    public Challenge answer(String answer) {
        setAnswer(answer);
        return this;
    }

    public Challenge event(Event event) {
        setEvent(event);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Challenge)) {
            return false;
        }
        Challenge challenge = (Challenge) o;
        return Objects.equals(eventId, challenge.eventId) && Objects.equals(question, challenge.question) && Objects.equals(answer, challenge.answer) && Objects.equals(event, challenge.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, question, answer, event);
    }

    @Override
    public String toString() {
        return "{" +
            " eventId='" + getEventId() + "'" +
            ", question='" + getQuestion() + "'" +
            ", answer='" + getAnswer() + "'" +
            ", event='" + getEvent() + "'" +
            "}";
    }
    
}

