package com.omniscient.omniscientback.login.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UserDTO {
    private Integer id; // 사용자 ID

    private String userId; // 사용자 ID

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
        return id; // 사용자 ID 반환
    }

    public void setId(Integer id) {
        this.id = id; // 사용자 ID 설정
    }

    public String getUserId() {
        return userId; // 사용자 ID 반환
    }

    public void setUserId(String userId) {
        this.userId = userId; // 사용자 ID 설정
    }

    public String getUsername() {
        return username; // 사용자 이름 반환
    }

    public void setUsername(String username) {
        this.username = username; // 사용자 이름 설정
    }

    public String getPassword() {
        return password; // 비밀번호 반환
    }

    public void setPassword(String password) {
        this.password = password; // 비밀번호 설정
    }

    public Boolean isUserStatus() {
        return userStatus; // 사용자 상태 반환
    }

    public void setUserStatus(Boolean userStatus) {
        this.userStatus = userStatus; // 사용자 상태 설정
    }

    public String getRole() {
        return role; // 사용자 역할 반환
    }

    public void setRole(String role) {
        this.role = role; // 사용자 역할 설정
    }

    public String getBirthDate() {
        return birthDate; // 생년월일 반환
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate; // 생년월일 설정
    }

    public String getPhoneNumber() {
        return phoneNumber; // 전화번호 반환
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber; // 전화번호 설정
    }

    public String getEmail() {
        return email; // 이메일 반환
    }

    public void setEmail(String email) {
        this.email = email; // 이메일 설정
    }
}
