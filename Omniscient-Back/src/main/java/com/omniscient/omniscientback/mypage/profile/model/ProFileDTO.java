package com.omniscient.omniscientback.mypage.profile.model;

public class ProFileDTO {
    private String profileName;             // 프로필 이름
    private String profilePosition;         // 프로필 직책
    private String profileEmail;            // 프로필 이메일
    private String profilePhone;            // 프로필 전화번호
    private int profileAge;                 // 프로필 나이
    private String profileAddress;          // 프로필 주소
    private String profileimageFileName;    // 프로필 이미지 이름
    private Boolean profileactive = true;


    public ProFileDTO() {
    }

    public ProFileDTO(String profileName, String profilePosition, String profileEmail, String profilePhone, int profileAge, String profileAddress, String profileimageFileName, Boolean profileactive) {
        this.profileName = profileName;
        this.profilePosition = profilePosition;
        this.profileEmail = profileEmail;
        this.profilePhone = profilePhone;
        this.profileAge = profileAge;
        this.profileAddress = profileAddress;
        this.profileimageFileName = profileimageFileName;
        this.profileactive = profileactive;
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

    @Override
    public String toString() {
        return "ProFileDTO{" +
                "profileName='" + profileName + '\'' +
                ", profilePosition='" + profilePosition + '\'' +
                ", profileEmail='" + profileEmail + '\'' +
                ", profilePhone='" + profilePhone + '\'' +
                ", profileAge=" + profileAge +
                ", profileAddress='" + profileAddress + '\'' +
                ", profileimageFileName='" + profileimageFileName + '\'' +
                ", profileactive=" + profileactive +
                '}';
    }
}
