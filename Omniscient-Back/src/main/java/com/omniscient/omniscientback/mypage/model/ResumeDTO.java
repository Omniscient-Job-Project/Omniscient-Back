package com.omniscient.omniscientback.mypage.model;

import lombok.Data;
import java.util.List;

@Data
public class ResumeDTO {
    private Integer id;
    private String title;
    private String name;
    private String email;
    private String phone;
    private List<Education> education;
    private List<Experience> experience;
    private String skills;
    private List<Certificate> certificates;
    private String introduction;

    @Data
    public static class Education {
        private String school;
        private String major;
        private String degree;
        private String graduationYear;
    }

    @Data
    public static class Experience {
        private String company;
        private String position;
        private String startDate;
        private String endDate;
        private String description;
    }

    @Data
    public static class Certificate {
        private String name;
        private String date;
    }
}