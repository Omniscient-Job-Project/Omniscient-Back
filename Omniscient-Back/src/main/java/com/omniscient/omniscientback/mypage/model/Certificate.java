package com.omniscient.omniscientback.mypage.model;

import jakarta.persistence.*;

/**
 * Certificate 엔티티 클래스
 * 사용자의 자격증 정보를 나타냅니다.
 */
@Entity
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 자격증 고유 식별자

    private String name;  // 자격증 이름

    private String date;  // 자격증 취득일 (문자열 형식)

    private String issuer;  // 발급 기관

    private String number;  // 자격증 번호

    private Boolean isActive = true;  // 자격증 상태 (활성화/비활성화)

    // 기본 생성자
    public Certificate() {
    }

    // 모든 필드를 포함한 생성자
    public Certificate(Integer id, String name, String date, String issuer, String number, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.issuer = issuer;
        this.number = number;
        this.isActive = isActive;
    }

    // Getter와 Setter 메서드
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }
    // tosting
    @Override
    public String toString() {
        return "Certificate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", issuer='" + issuer + '\'' +
                ", number='" + number + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}