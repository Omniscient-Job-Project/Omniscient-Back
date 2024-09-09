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

    @Column(length = 50) // 제목은 50자 제한
    private String title;

    @Column(length = 10) // 이름은 10자 제한
    private String name;

    @Column(length = 50) // 이메일은 50자 제한
    private String email;

    @Column(length = 20) // 전화번호는 20자 제한
    private String phone;

    // @ElementCollection: 여러 값을 하나로 묶어 저장하지만, 이 값들은 독립적인 엔티티(객체)가 아니라, 값 타입으로 취급 즉, 별도의 테이블에 저장되지만,
    //                     그 자체로 중요한 엔티티는 아니고 특정 객체의 부속 정보로만 사용
    // @CollectionTable: 컬렉션을 저장할 테이블을 지정하고, 외래 키를 설정
    // Resume이라는 메인 정보에 여러 가지 학력(Education), 경력(Experience), 자격증(Certificate) 같은 여러 정보를 따로 테이블로 분리해서 저장
    @ElementCollection
    @CollectionTable(name = "resume_education", joinColumns = @JoinColumn(name = "resume_id"))
    private List<Education> education;

    @ElementCollection
    @CollectionTable(name = "resume_experience", joinColumns = @JoinColumn(name = "resume_id"))
    private List<Experience> experience;

    @Column(length = 5000) // 스킬은 1000자 제한
    private String skills;

    @ElementCollection
    @CollectionTable(name = "resume_certificates", joinColumns = @JoinColumn(name = "resume_id"))
    private List<Certificate> certificates;

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
