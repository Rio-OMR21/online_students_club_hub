package com.orinaryaga.online_students_club_hub.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import java.util.Objects;

@Entity
public class Mentor {
    @Id
    private Long userId;

    private String department;
    private String employeeNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userId",referencedColumnName = "userId", nullable = false)
    private User user;

    // Getters and setters


    public Mentor() {
    }

    public Mentor(Long userId, String department, String employeeNumber, User user) {
        this.userId = userId;
        this.department = department;
        this.employeeNumber = employeeNumber;
        this.user = user;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmployeeNumber() {
        return this.employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Mentor userId(Long userId) {
        setUserId(userId);
        return this;
    }

    public Mentor department(String department) {
        setDepartment(department);
        return this;
    }

    public Mentor employeeNumber(String employeeNumber) {
        setEmployeeNumber(employeeNumber);
        return this;
    }

    public Mentor user(User user) {
        setUser(user);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Mentor)) {
            return false;
        }
        Mentor mentor = (Mentor) o;
        return Objects.equals(userId, mentor.userId) && Objects.equals(department, mentor.department) && Objects.equals(employeeNumber, mentor.employeeNumber) && Objects.equals(user, mentor.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, department, employeeNumber, user);
    }

    @Override
    public String toString() {
        return "{" +
            " userId='" + getUserId() + "'" +
            ", department='" + getDepartment() + "'" +
            ", employeeNumber='" + getEmployeeNumber() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }

   
}

