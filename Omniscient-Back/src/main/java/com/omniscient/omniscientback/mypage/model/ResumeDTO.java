package com.omniscient.omniscientback.mypage.model;

import lombok.Data;
import java.util.List;

/**
 * ResumeDTO 클래스
 * 이력서 정보를 전송하기 위한 데이터 전송 객체입니다.
 */
@Data
public class ResumeDTO {
    private Integer id;  // 이력서 고유 식별자
    private String title;  // 이력서 제목
    private String name;  // 이름
    private String email;  // 이메일
    private String phone;  // 전화번호
    private List<Education> education;  // 학력 정보 리스트
    private List<Experience> experience;  // 경력 정보 리스트
    private String skills;  // 보유 기술
    private List<Certificate> certificates;  // 자격증 정보 리스트
    private String introduction;  // 자기소개
    private Boolean status;  // 이력서 상태 (활성화/비활성화)

    @Data
    public static class Education {
        private String school;  // 학교명
        private String major;  // 전공
        private String degree;  // 학위
        private String graduationYear;  // 졸업연도
    }

    @Data
    public static class Experience {
        private String company;  // 회사명
        private String position;  // 직책
        private String startDate;  // 시작일
        private String endDate;  // 종료일
        private String description;  // 업무 설명
    }

    @Data
    public static class Certificate {
        private String name;  // 자격증명
        private String date;  // 취득일
    }
}