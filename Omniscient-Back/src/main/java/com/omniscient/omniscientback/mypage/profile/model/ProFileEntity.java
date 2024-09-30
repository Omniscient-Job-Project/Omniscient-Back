package com.omniscient.omniscientback.mypage.profile.model;

import jakarta.persistence.*;

@Entity
@Table(name = "profile")
public class ProFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "profileName")
    private String profileName;             // 프로필 이름

    @Column(name = "profilePosition")
    private String profilePosition;         // 프로필 직책

    @Column(name = "profileEmail")
    private String profileEmail;            // 프로필 이메일

    @Column(name = "profilePhone")
    private String profilePhone;            // 프로필 전화번호

    @Column(name = "profileAge")
    private int profileAge;                 // 프로필 나이

    @Column(name = "profileAddress")
    private String profileAddress;          // 프로필 주소

    @Column(name = "profileimageFileName")
    private String profileimageFileName;    // 프로필 이미지 이름

    @Column(name = "profileactive")
    private Boolean profileactive = true;


    @Column(name = "status")
    private String status = "active";

    public ProFileEntity() {
    }

    public ProFileEntity(Integer id, String profileName, String profilePosition, String profileEmail, String profilePhone, int profileAge, String profileAddress, String profileimageFileName, Boolean profileactive, String status) {
        this.id = id;
        this.profileName = profileName;
        this.profilePosition = profilePosition;
        this.profileEmail = profileEmail;
        this.profilePhone = profilePhone;
        this.profileAge = profileAge;
        this.profileAddress = profileAddress;
        this.profileimageFileName = profileimageFileName;
        this.profileactive = profileactive;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfilePosition() {
        return profilePosition;
    }

    public void setProfilePosition(String profilePosition) {
        this.profilePosition = profilePosition;
    }

    public String getProfileEmail() {
        return profileEmail;
    }

    public void setProfileEmail(String profileEmail) {
        this.profileEmail = profileEmail;
    }

    public String getProfilePhone() {
        return profilePhone;
    }

    public void setProfilePhone(String profilePhone) {
        this.profilePhone = profilePhone;
    }

    public int getProfileAge() {
        return profileAge;
    }

    public void setProfileAge(int profileAge) {
        this.profileAge = profileAge;
    }

    public String getProfileAddress() {
        return profileAddress;
    }

    public void setProfileAddress(String profileAddress) {
        this.profileAddress = profileAddress;
    }

    public String getProfileimageFileName() {
        return profileimageFileName;
    }

    public void setProfileimageFileName(String profileimageFileName) {
        this.profileimageFileName = profileimageFileName;
    }

    public Boolean getProfileactive() {
        return profileactive;
    }

    public void setProfileactive(Boolean profileactive) {
        this.profileactive = profileactive;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProFileEntity{" +
                "id=" + id +
                ", profileName='" + profileName + '\'' +
                ", profilePosition='" + profilePosition + '\'' +
                ", profileEmail='" + profileEmail + '\'' +
                ", profilePhone='" + profilePhone + '\'' +
                ", profileAge=" + profileAge +
                ", profileAddress='" + profileAddress + '\'' +
                ", profileimageFileName='" + profileimageFileName + '\'' +
                ", profileactive=" + profileactive +
                ", status='" + status + '\'' +
                '}';
    }
}
