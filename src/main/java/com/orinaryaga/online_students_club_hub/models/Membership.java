package com.orinaryaga.online_students_club_hub.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long membershipId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "clubId")
    private Club club;

    private String role;

    // Getters and setters

    public Membership() {
    }

    public Membership(Long membershipId, User user, Club club, String role) {
        this.membershipId = membershipId;
        this.user = user;
        this.club = club;
        this.role = role;
    }

    public Long getMembershipId() {
        return this.membershipId;
    }

    public void setMembershipId(Long membershipId) {
        this.membershipId = membershipId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Club getClub() {
        return this.club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Membership membershipId(Long membershipId) {
        setMembershipId(membershipId);
        return this;
    }

    public Membership user(User user) {
        setUser(user);
        return this;
    }

    public Membership club(Club club) {
        setClub(club);
        return this;
    }

    public Membership role(String role) {
        setRole(role);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Membership)) {
            return false;
        }
        Membership membership = (Membership) o;
        return Objects.equals(membershipId, membership.membershipId) && Objects.equals(user, membership.user) && Objects.equals(club, membership.club) && Objects.equals(role, membership.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(membershipId, user, club, role);
    }

    @Override
    public String toString() {
        return "{" +
            " membershipId='" + getMembershipId() + "'" +
            ", user='" + getUser() + "'" +
            ", club='" + getClub() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
    
}
