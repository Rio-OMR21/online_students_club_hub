package com.orinaryaga.online_students_club_hub.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Entity
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;

    private String clubName;
    private String description;
    private String category;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private Set<Chat> chat = new HashSet<>();


    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "club-membership")
    private List<Membership> memberships = new ArrayList<>();
    // Getters and setters



    public Club() {
    }

    public Club(Long clubId, String clubName, String description, String category, Set<Chat> chat, List<Membership> memberships) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.description = description;
        this.category = category;
        this.chat = chat;
        this.memberships = memberships;
    }

    public Long getClubId() {
        return this.clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return this.clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Chat> getChat() {
        return this.chat;
    }

    public void setChat(Set<Chat> chat) {
        this.chat = chat;
    }

    public List<Membership> getMemberships() {
        return this.memberships;
    }

    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }

    public Club clubId(Long clubId) {
        setClubId(clubId);
        return this;
    }

    public Club clubName(String clubName) {
        setClubName(clubName);
        return this;
    }

    public Club description(String description) {
        setDescription(description);
        return this;
    }

    public Club category(String category) {
        setCategory(category);
        return this;
    }

    public Club chat(Set<Chat> chat) {
        setChat(chat);
        return this;
    }

    public Club memberships(List<Membership> memberships) {
        setMemberships(memberships);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Club)) {
            return false;
        }
        Club club = (Club) o;
        return Objects.equals(clubId, club.clubId) && Objects.equals(clubName, club.clubName) && Objects.equals(description, club.description) && Objects.equals(category, club.category) && Objects.equals(chat, club.chat) && Objects.equals(memberships, club.memberships);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clubId, clubName, description, category, chat, memberships);
    }

    @Override
    public String toString() {
        return "{" +
            " clubId='" + getClubId() + "'" +
            ", clubName='" + getClubName() + "'" +
            ", description='" + getDescription() + "'" +
            ", category='" + getCategory() + "'" +
            ", chat='" + getChat() + "'" +
            ", memberships='" + getMemberships() + "'" +
            "}";
    }
   

   
    
}

