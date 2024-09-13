package com.omniscient.omniscientback.login.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; // 변경된 부분: 'Id'를 'id'로 수정

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_status")
    private boolean userStatus;

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(name = "user_role")
    private UserRole role;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "refresh_token", nullable = false) // 변경된 부분: 'refresh_Token'을 'refresh_token'으로 수정
    private String refreshToken;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한을 반환하는 로직
        return List.of(new SimpleGrantedAuthority("ROLE_USER")); // 예시로 ROLE_USER 권한 반환
    }

    private UserEntity(Builder builder) {
        this.id = builder.id; // 변경된 부분: 'Id'를 'id'로 수정
        this.userId = builder.userId;
        this.username = builder.username;
        this.password = builder.password;
        this.userStatus = builder.userStatus;
        this.role = builder.role;
        this.birthDate = builder.birthDate;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.refreshToken = builder.refreshToken;
    }

    public UserEntity() {
    }

    // Builder 패턴 적용
    public static class Builder {
        private Integer id; // 변경된 부분: 'Id'를 'id'로 수정
        private String userId;
        private String username;
        private String password;
        private boolean userStatus;
        private UserRole role;
        private String birthDate;
        private String phoneNumber;
        private String email;
        private String refreshToken;

        public Builder id(Integer id) { // 변경된 부분: 'Id'를 'id'로 수정
            this.id = id;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder userStatus(boolean userStatus) {
            this.userStatus = userStatus;
            return this;
        }

        public Builder role(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder birthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        // build() 메서드: UserEntity 객체를 반환
        public UserEntity build() {
            return new UserEntity(this);
        }
    }

    public Integer getId() { // 변경된 부분: 'Id'를 'id'로 수정
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public UserRole getRole() {
        return role;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setId(Integer id) { // 변경된 부분: 'Id'를 'id'로 수정
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
