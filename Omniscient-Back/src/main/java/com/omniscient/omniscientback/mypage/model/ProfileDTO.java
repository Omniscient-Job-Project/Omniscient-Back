package com.omniscient.omniscientback.mypage.model;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ProfileDTO 클래스
 * 프로필 정보를 전송하기 위한 데이터 전송 객체입니다.
 */

public class ProfileDTO {
    private Integer id;  // 프로필 고유 식별자
    private String name;  // 사용자 이름
    private String jobTitle;  // 직책
    private String email;  // 이메일 주소
    private String phone;  // 전화번호
    private Integer age;  // 나이
    private String address;  // 주소
    private List<String> certificates;  // 자격증 목록
    private Boolean status;  // 프로필 상태 (활성화/비활성화)

    @JsonIgnore
    private byte[] profileImage;  // 프로필 이미지 (JSON 직렬화에서 제외)

    public ProfileDTO() {
    }

    /**
     * Integer 타입의 id를 설정합니다.
     * @param id 설정할 id 값
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * String 타입의 id를 Integer로 변환하여 설정합니다.
     * @param id 설정할 id 값 (문자열)
     */
    public void setId(String id) {
        if (id != null && !id.equals("null")) {
            this.id = Integer.parseInt(id);
        } else {
            this.id = null;
        }
    }

    public Integer getId() {
        return id;
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

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", certificates=" + certificates +
                ", status=" + status +
                ", profileImage=" + Arrays.toString(profileImage) +
                '}';
    }
}