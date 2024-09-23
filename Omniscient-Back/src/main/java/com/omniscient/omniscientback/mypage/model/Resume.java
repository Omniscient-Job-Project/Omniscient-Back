package com.omniscient.omniscientback.mypage.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Resume 엔티티 클래스
 * 이력서 정보를 나타냅니다.
 */
@Entity
@Table(name = "resumes")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resumeId;  // 이력서 고유 식별자

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

    public static class Education {
        private String school;  // 학교명
        private String major;  // 전공
        private String degree;  // 학위
        private String graduationYear;  // 졸업연도
    }

    @Embeddable

    public static class Experience {
        private String company;  // 회사명
        private String position;  // 직책
        private String startDate;  // 시작일
        private String endDate;  // 종료일
        private String description;  // 업무 설명
    }

    @Embeddable

    public static class Certificate {
        private String name;  // 자격증명
        private String date;  // 취득일
    }

    public Resume() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public List<Experience> getExperience() {
        return experience;
    }

    public void setExperience(List<Experience> experience) {
        this.experience = experience;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getResumeId() {
        return resumeId;
    }

    public void setResumeId(Integer resumeId) {
        this.resumeId = resumeId;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "resumeId=" + resumeId +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", education=" + education +
                ", experience=" + experience +
                ", skills='" + skills + '\'' +
                ", certificates=" + certificates +
                ", introduction='" + introduction + '\'' +
                ", status=" + status +
                '}';
    }

}