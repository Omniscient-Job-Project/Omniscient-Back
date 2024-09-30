package com.omniscient.omniscientback.login.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserDTO {
    private Integer id; // 사용자 ID

    @NotBlank
    private String userId; // 사용자 ID

    @NotBlank
    private String username; // 사용자 이름

    @NotBlank // 빈 값 허용 안 함
    private String password; // 비밀번호

    private Boolean userStatus; // 사용자 상태 (null 체크 가능)

    @NotBlank // 빈 값 허용 안 함
    @Pattern(regexp = "USER|ADMIN", message = "Role must be either USER or ADMIN") // 유효한 역할 값 검증
    private String role; // 사용자 역할

    private String birthDate; // 생년월일

    @NotBlank // 빈 값 허용 안 함
    private String phoneNumber; // 전화번호

    @NotBlank // 빈 값 허용 안 함
    @Email // 이메일 형식 검증
    private String email; // 이메일

    // Getter 및 Setter 메서드
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Boolean userStatus) {
        this.userStatus = userStatus;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
