package com.omniscient.omniscientback.mypage.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

/**
 * Resume 엔티티 클래스
 * 이력서 정보를 나타냅니다.
 */
@Entity
@Table(name = "resumes")
@Data
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 이력서 고유 식별자

    @Column(length = 50)
    private String title;  // 이력서 제목 (50자 제한)

    @Column(length = 10)
    private String name;  // 이름 (10자 제한)

    @Column(length = 50)
    private String email;  // 이메일 (50자 제한)

    @Column(length = 20)
    private String phone;  // 전화번호 (20자 제한)

    @ElementCollection
    @CollectionTable(name = "resume_education", joinColumns = @JoinColumn(name = "resume_id"))
    private List<Education> education;  // 학력 정보 리스트

    @ElementCollection
    @CollectionTable(name = "resume_experience", joinColumns = @JoinColumn(name = "resume_id"))
    private List<Experience> experience;  // 경력 정보 리스트

    @Column(length = 1000)
    private String skills;  // 보유 기술 (1000자 제한)

    @ElementCollection
    @CollectionTable(name = "resume_certificates", joinColumns = @JoinColumn(name = "resume_id"))
    private List<Certificate> certificates;  // 자격증 정보 리스트

    @Column(length = 3000)
    private String introduction;  // 자기소개 (3000자 제한)

    private Boolean status = true;  // 이력서 상태 (활성화/비활성화)

    @Embeddable
    @Data
    public static class Education {
        private String school;  // 학교명
        private String major;  // 전공
        private String degree;  // 학위
        private String graduationYear;  // 졸업연도
    }

    @Embeddable
    @Data
    public static class Experience {
        private String company;  // 회사명
        private String position;  // 직책
        private String startDate;  // 시작일
        private String endDate;  // 종료일
        private String description;  // 업무 설명
    }

    @Embeddable
    @Data
    public static class Certificate {
        private String name;  // 자격증명
        private String date;  // 취득일
    }
}