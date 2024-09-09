package com.omniscient.omniscientback.mypage.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "resumes")
@Data
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String name;
    private String email;
    private String phone;

    @ElementCollection
    @CollectionTable(name = "resume_education", joinColumns = @JoinColumn(name = "resume_id"))
    private List<Education> education;

    @ElementCollection
    @CollectionTable(name = "resume_experience", joinColumns = @JoinColumn(name = "resume_id"))
    private List<Experience> experience;

    private String skills;

    @ElementCollection
    @CollectionTable(name = "resume_certificates", joinColumns = @JoinColumn(name = "resume_id"))
    private List<Certificate> certificates;

    @Lob
    private String introduction;

    @Embeddable
    @Data
    public static class Education {
        private String school;
        private String major;
        private String degree;
        private String graduationYear;
    }

    @Embeddable
    @Data
    public static class Experience {
        private String company;
        private String position;
        private String startDate;
        private String endDate;
        private String description;
    }

    @Embeddable
    @Data
    public static class Certificate {
        private String name;
        private String date;
    }
}