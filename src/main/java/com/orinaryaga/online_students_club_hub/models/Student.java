package com.orinaryaga.online_students_club_hub.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Student {
    @Id
    private Long userId;

    private String program;
    private String registrationNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    @JsonBackReference(value = "user-student")
    private User user;

    // Getters and setters

    public Student() {
    }

    public Student(Long userId, String program, String registrationNumber, User user) {
        this.userId = userId;
        this.program = program;
        this.registrationNumber = registrationNumber;
        this.user = user;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProgram() {
        return this.program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Student userId(Long userId) {
        setUserId(userId);
        return this;
    }

    public Student program(String program) {
        setProgram(program);
        return this;
    }

    public Student registrationNumber(String registrationNumber) {
        setRegistrationNumber(registrationNumber);
        return this;
    }

    public Student user(User user) {
        setUser(user);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Student)) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(userId, student.userId) && Objects.equals(program, student.program) && Objects.equals(registrationNumber, student.registrationNumber) && Objects.equals(user, student.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, program, registrationNumber, user);
    }

    @Override
    public String toString() {
        return "{" +
            " userId='" + getUserId() + "'" +
            ", program='" + getProgram() + "'" +
            ", registrationNumber='" + getRegistrationNumber() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }

    

}
