package com.omniscient.omniscientback.mypage.model;

import java.time.LocalDateTime;

public class UserProfileDTO {

    private Integer profileId;
    private String name;
    private String email;
    private String imageFileName;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserProfileDTO() {
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserProfileDTO(Integer profileId, String name, String email, String imageFileName, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.profileId = profileId;
        this.name = name;
        this.email = email;
        this.imageFileName = imageFileName;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "UserProfileDTO{" +
                "profileId=" + profileId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", imageFileName='" + imageFileName + '\'' +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}