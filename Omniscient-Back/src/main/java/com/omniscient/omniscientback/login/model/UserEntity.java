package com.omniscient.omniscientback.login.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user_entity")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId; // 사용자 ID

    @Column(name = "user_name", nullable = false)
    private String username; // 사용자 이름

    @Column(name = "user_password", nullable = false)
    private String password; // 비밀번호

    @Column(name = "user_status")
    private boolean userStatus; // 사용자 상태

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole role; // 사용자 역할

    @Column(name = "birth_date")
    private String birthDate; // 생년월일

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber; // 전화번호

    @Column(name = "email", nullable = false, unique = true)
    private String email; // 이메일

    @Column(name = "refresh_token")
    private String refreshToken; // 리프레시 토큰

    @Column(name = "is_active")
    private boolean active = true; // 활성화 상태

    // Builder 패턴을 위한 생성자
    private UserEntity(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.username = builder.username;
        this.password = builder.password;
        this.userStatus = builder.userStatus;
        this.role = builder.role;
        this.birthDate = builder.birthDate;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.refreshToken = builder.refreshToken;
        this.active = builder.active;
    }

    // 기본 생성자
    public UserEntity() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (this.role != null) {
            authorities.add(new SimpleGrantedAuthority(this.role.name())); // ROLE_ 접두사 없이 추가
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password; // 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return username; // 사용자 이름 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠김 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return userStatus; // 사용자 활성화 여부
    }

    public void setRole(UserRole role) {
        this.role = role; // 사용자 역할 설정
    }

    public UserRole getRole() {
        return role; // 사용자 역할 반환
    }

    public static class Builder {
        private Integer id; // 사용자 ID
        private String userId; // 사용자 ID
        private String username; // 사용자 이름
        private String password; // 비밀번호
        private boolean userStatus; // 사용자 상태
        private UserRole role; // 사용자 역할
        private String birthDate; // 생년월일
        private String phoneNumber; // 전화번호
        private String email; // 이메일
        private String refreshToken; // 리프레시 토큰
        private boolean active = true;

        public Builder id(Integer id) {
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

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(this); // UserEntity 객체 반환
        }
    }

    // Getter 및 Setter 메서드
    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isUserStatus() {
        return userStatus;
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
