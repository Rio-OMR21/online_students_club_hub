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
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @ManyToOne
    @JoinColumn(name = "clubId", referencedColumnName = "clubId", nullable = false)
    private Club club;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;

    private String message;
    private Timestamp sentTime;

    // Getters and setters

    public Chat() {
    }

    public Chat(Long chatId, Club club, User user, String message, Timestamp sentTime) {
        this.chatId = chatId;
        this.club = club;
        this.user = user;
        this.message = message;
        this.sentTime = sentTime;
    }

    public Long getChatId() {
        return this.chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
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

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getSentTime() {
        return this.sentTime;
    }

    public void setSentTime(Timestamp sentTime) {
        this.sentTime = sentTime;
    }

    public Chat chatId(Long chatId) {
        setChatId(chatId);
        return this;
    }

    public Chat club(Club club) {
        setClub(club);
        return this;
    }

    public Chat user(User user) {
        setUser(user);
        return this;
    }

    public Chat message(String message) {
        setMessage(message);
        return this;
    }

    public Chat sentTime(Timestamp sentTime) {
        setSentTime(sentTime);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Chat)) {
            return false;
        }
        Chat chat = (Chat) o;
        return Objects.equals(chatId, chat.chatId) && Objects.equals(club, chat.club) && Objects.equals(user, chat.user) && Objects.equals(message, chat.message) && Objects.equals(sentTime, chat.sentTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, club, user, message, sentTime);
    }

    @Override
    public String toString() {
        return "{" +
            " chatId='" + getChatId() + "'" +
            ", club='" + getClub() + "'" +
            ", user='" + getUser() + "'" +
            ", message='" + getMessage() + "'" +
            ", sentTime='" + getSentTime() + "'" +
            "}";
    }
    
}
