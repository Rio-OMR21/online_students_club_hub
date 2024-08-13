package com.orinaryaga.online_students_club_hub.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.Set;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;
    private String firstName;
    private String secondName;
    private String lastName;
    private String gender;
    private String email;
    private String password;
    private String profilePhoto;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Student student;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Mentor mentor;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Membership> memberships;
    

    public User() {
    }

    public User(Long userId, String userName, String firstName, String secondName, String lastName, String gender, String email, String password, String profilePhoto, Student student, Mentor mentor, Set<Membership> memberships) {
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.student = student;
        this.mentor = mentor;
        this.memberships = memberships;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePhoto() {
        return this.profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Mentor getMentor() {
        return this.mentor;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }

    public Set<Membership> getMemberships() {
        return this.memberships;
    }

    public void setMemberships(Set<Membership> memberships) {
        this.memberships = memberships;
    }

    public User userId(Long userId) {
        setUserId(userId);
        return this;
    }

    public User userName(String userName) {
        setUserName(userName);
        return this;
    }

    public User firstName(String firstName) {
        setFirstName(firstName);
        return this;
    }

    public User secondName(String secondName) {
        setSecondName(secondName);
        return this;
    }

    public User lastName(String lastName) {
        setLastName(lastName);
        return this;
    }

    public User gender(String gender) {
        setGender(gender);
        return this;
    }

    public User email(String email) {
        setEmail(email);
        return this;
    }

    public User password(String password) {
        setPassword(password);
        return this;
    }

    public User profilePhoto(String profilePhoto) {
        setProfilePhoto(profilePhoto);
        return this;
    }

    public User student(Student student) {
        setStudent(student);
        return this;
    }

    public User mentor(Mentor mentor) {
        setMentor(mentor);
        return this;
    }

    public User memberships(Set<Membership> memberships) {
        setMemberships(memberships);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(userName, user.userName) && Objects.equals(firstName, user.firstName) && Objects.equals(secondName, user.secondName) && Objects.equals(lastName, user.lastName) && Objects.equals(gender, user.gender) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(profilePhoto, user.profilePhoto) && Objects.equals(student, user.student) && Objects.equals(mentor, user.mentor) && Objects.equals(memberships, user.memberships);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, firstName, secondName, lastName, gender, email, password, profilePhoto, student, mentor, memberships);
    }

    @Override
    public String toString() {
        return "{" +
            " userId='" + getUserId() + "'" +
            ", userName='" + getUserName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", secondName='" + getSecondName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", gender='" + getGender() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", profilePhoto='" + getProfilePhoto() + "'" +
            ", student='" + getStudent() + "'" +
            ", mentor='" + getMentor() + "'" +
            ", memberships='" + getMemberships() + "'" +
            "}";
    }




    
}
