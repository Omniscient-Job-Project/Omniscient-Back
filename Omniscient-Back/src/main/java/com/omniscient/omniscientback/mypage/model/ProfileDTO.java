package com.omniscient.omniscientback.mypage.model;

import java.util.List;

public class ProfileDTO {
    private Integer profileId;
    private String name;
    private String jobTitle;
    private String email;
    private String phone;
    private Integer age;
    private String address;
    private List<String> certificates;
    private Boolean status;
    private List<String> profileImageBase64; // Base64 인코딩된 이미지 문자열 리스트

    public ProfileDTO() {
    }

    // Getters and setters

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

    public List<String> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<String> certificates) {
        this.certificates = certificates;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    // 기존의 setId 메서드에서 id를 profileId로 변경
    public void setProfileId(String id) {
        if (id != null && !id.equals("null")) {
            this.profileId = Integer.parseInt(id);
        } else {
            this.profileId = null;
        }
    }

    public List<String> getProfileImageBase64() {
        return profileImageBase64;
    }

    public void setProfileImageBase64(List<String> profileImageBase64) {
        this.profileImageBase64 = profileImageBase64;
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
                "profileId=" + profileId +
                ", name='" + name + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", certificates=" + certificates +
                ", status=" + status +
                ", profileImageBase64=" + (profileImageBase64 != null ? profileImageBase64.size() + " images" : "null") +
                '}';
    }
}
