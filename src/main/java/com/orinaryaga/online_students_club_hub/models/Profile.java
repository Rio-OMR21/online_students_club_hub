package com.orinaryaga.online_students_club_hub.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import java.util.Objects;

@Entity
@Builder
public class Profile {
    @Id
    private Long userId;

    private String imageName;
    private String imageType;
    private String filePath;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;



    public Profile() {
    }

    public Profile(Long userId, String imageName, String imageType, String filePath, User user) {
        this.userId = userId;
        this.imageName = imageName;
        this.imageType = imageType;
        this.filePath = filePath;
        this.user = user;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getImageName() {
        return this.imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return this.imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Profile userId(Long userId) {
        setUserId(userId);
        return this;
    }

    public Profile imageName(String imageName) {
        setImageName(imageName);
        return this;
    }

    public Profile imageType(String imageType) {
        setImageType(imageType);
        return this;
    }

    public Profile filePath(String filePath) {
        setFilePath(filePath);
        return this;
    }

    public Profile user(User user) {
        setUser(user);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Profile)) {
            return false;
        }
        Profile profile = (Profile) o;
        return Objects.equals(userId, profile.userId) && Objects.equals(imageName, profile.imageName) && Objects.equals(imageType, profile.imageType) && Objects.equals(filePath, profile.filePath) && Objects.equals(user, profile.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, imageName, imageType, filePath, user);
    }

    @Override
    public String toString() {
        return "{" +
            " userId='" + getUserId() + "'" +
            ", imageName='" + getImageName() + "'" +
            ", imageType='" + getImageType() + "'" +
            ", filePath='" + getFilePath() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }

    
}