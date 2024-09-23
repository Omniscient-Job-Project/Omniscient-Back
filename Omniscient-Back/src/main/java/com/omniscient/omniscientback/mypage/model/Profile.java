package com.omniscient.omniscientback.mypage.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profileId;

    private String name;
    private String jobTitle;
    private String email;
    private String phone;
    private Integer age;
    private String address;
    private Boolean status = true;

    @ElementCollection
    @CollectionTable(name = "profile_certificates", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "certificate")
    private List<String> certificates = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "profile_images", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "image", length = 5 * 1024 * 1024) // 5MB 제한
    private List<byte[]> profileImages = new ArrayList<>();

    public Profile() {
    }

    // Getters and setters
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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<String> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<String> certificates) {
        this.certificates = certificates;
    }

    public List<byte[]> getProfileImages() {
        return profileImages;
    }

    public void setProfileImages(List<byte[]> profileImages) {
        this.profileImages = profileImages;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profileId=" + profileId +
                ", name='" + name + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", certificates=" + certificates +
                ", profileImages=" + (profileImages != null ? profileImages.size() + " images" : "null") +
                '}';
    }

}