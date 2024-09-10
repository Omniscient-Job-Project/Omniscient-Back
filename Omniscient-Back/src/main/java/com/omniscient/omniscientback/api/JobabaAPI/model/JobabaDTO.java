package com.omniscient.omniscientback.api.JobabaAPI.model;

import java.time.LocalDate;

public class JobabaDTO {

    private String jobabaCompanyName; // 회사이름
    private String jobabaInfoTitle; // 채용 정보 제목
    private String jobabaWageType; // 급여 형태(시급, 월급 등)
    private String jobabaSalary; // 급여 금액
    private String jobabaLocation; // 근무 위치 (예: 서울특별시, 경기도)
    private String jobabaEmploymentType; // 고용 형태 (예: 정규직, 계약직)
    private String jobabaCareerCondition; // 경력 조건 (예: 경력 2년 이상, 신입 가능)
    private LocalDate jobabaPostedDate; // 채용 공고 게시일
    private LocalDate jobabaClosingDate; // 채용 공고 마감일
    private String jobabaWebInfoUrl; // 웹에서 공고를 볼 수 있는 URL
    private Integer workRegionCdCont; // 근무 지역 코드 (상세 주소)
    private String workRegionCont; // 근무 지역 명칭 (상세 주소)
    private int recrutFieldCdNm; // 모집 분야 코드 (예: IT, 제조업 등)
    private String recrutFieldNm; // 모집 분야 명칭 (예: 개발자, 디자이너)
    private int emplmntPsncnt; // 모집 인원 수

    public JobabaDTO() {
    }

    public JobabaDTO(String jobabaCompanyName, String jobabaInfoTitle, String jobabaWageType, String jobabaSalary, String jobabaLocation, String jobabaEmploymentType, String jobabaCareerCondition, LocalDate jobabaPostedDate, LocalDate jobabaClosingDate, String jobabaWebInfoUrl, Integer workRegionCdCont, String workRegionCont, int recrutFieldCdNm, String recrutFieldNm, int emplmntPsncnt) {
        this.jobabaCompanyName = jobabaCompanyName;
        this.jobabaInfoTitle = jobabaInfoTitle;
        this.jobabaWageType = jobabaWageType;
        this.jobabaSalary = jobabaSalary;
        this.jobabaLocation = jobabaLocation;
        this.jobabaEmploymentType = jobabaEmploymentType;
        this.jobabaCareerCondition = jobabaCareerCondition;
        this.jobabaPostedDate = jobabaPostedDate;
        this.jobabaClosingDate = jobabaClosingDate;
        this.jobabaWebInfoUrl = jobabaWebInfoUrl;
        this.workRegionCdCont = workRegionCdCont;
        this.workRegionCont = workRegionCont;
        this.recrutFieldCdNm = recrutFieldCdNm;
        this.recrutFieldNm = recrutFieldNm;
        this.emplmntPsncnt = emplmntPsncnt;
    }

    public String getJobabaCompanyName() {
        return jobabaCompanyName;
    }

    public void setJobabaCompanyName(String jobabaCompanyName) {
        this.jobabaCompanyName = jobabaCompanyName;
    }

    public String getJobabaInfoTitle() {
        return jobabaInfoTitle;
    }

    public void setJobabaInfoTitle(String jobabaInfoTitle) {
        this.jobabaInfoTitle = jobabaInfoTitle;
    }

    public String getJobabaWageType() {
        return jobabaWageType;
    }

    public void setJobabaWageType(String jobabaWageType) {
        this.jobabaWageType = jobabaWageType;
    }

    public String getJobabaSalary() {
        return jobabaSalary;
    }

    public void setJobabaSalary(String jobabaSalary) {
        this.jobabaSalary = jobabaSalary;
    }

    public String getJobabaLocation() {
        return jobabaLocation;
    }

    public void setJobabaLocation(String jobabaLocation) {
        this.jobabaLocation = jobabaLocation;
    }

    public String getJobabaEmploymentType() {
        return jobabaEmploymentType;
    }

    public void setJobabaEmploymentType(String jobabaEmploymentType) {
        this.jobabaEmploymentType = jobabaEmploymentType;
    }

    public String getJobabaCareerCondition() {
        return jobabaCareerCondition;
    }

    public void setJobabaCareerCondition(String jobabaCareerCondition) {
        this.jobabaCareerCondition = jobabaCareerCondition;
    }

    public LocalDate getJobabaPostedDate() {
        return jobabaPostedDate;
    }

    public void setJobabaPostedDate(LocalDate jobabaPostedDate) {
        this.jobabaPostedDate = jobabaPostedDate;
    }

    public LocalDate getJobabaClosingDate() {
        return jobabaClosingDate;
    }

    public void setJobabaClosingDate(LocalDate jobabaClosingDate) {
        this.jobabaClosingDate = jobabaClosingDate;
    }

    public String getJobabaWebInfoUrl() {
        return jobabaWebInfoUrl;
    }

    public void setJobabaWebInfoUrl(String jobabaWebInfoUrl) {
        this.jobabaWebInfoUrl = jobabaWebInfoUrl;
    }

    public Integer getWorkRegionCdCont() {
        return workRegionCdCont;
    }

    public void setWorkRegionCdCont(Integer workRegionCdCont) {
        this.workRegionCdCont = workRegionCdCont;
    }

    public String getWorkRegionCont() {
        return workRegionCont;
    }

    public void setWorkRegionCont(String workRegionCont) {
        this.workRegionCont = workRegionCont;
    }

    public int getRecrutFieldCdNm() {
        return recrutFieldCdNm;
    }

    public void setRecrutFieldCdNm(int recrutFieldCdNm) {
        this.recrutFieldCdNm = recrutFieldCdNm;
    }

    public String getRecrutFieldNm() {
        return recrutFieldNm;
    }

    public void setRecrutFieldNm(String recrutFieldNm) {
        this.recrutFieldNm = recrutFieldNm;
    }

    public int getEmplmntPsncnt() {
        return emplmntPsncnt;
    }

    public void setEmplmntPsncnt(int emplmntPsncnt) {
        this.emplmntPsncnt = emplmntPsncnt;
    }

    @Override
    public String toString() {
        return "JobabaDTO{" +
                "jobabaCompanyName='" + jobabaCompanyName + '\'' +
                ", jobabaInfoTitle='" + jobabaInfoTitle + '\'' +
                ", jobabaWageType='" + jobabaWageType + '\'' +
                ", jobabaSalary='" + jobabaSalary + '\'' +
                ", jobabaLocation='" + jobabaLocation + '\'' +
                ", jobabaEmploymentType='" + jobabaEmploymentType + '\'' +
                ", jobabaCareerCondition='" + jobabaCareerCondition + '\'' +
                ", jobabaPostedDate='" + jobabaPostedDate + '\'' +
                ", jobabaClosingDate='" + jobabaClosingDate + '\'' +
                ", jobabaWebInfoUrl='" + jobabaWebInfoUrl + '\'' +
                ", workRegionCdCont=" + workRegionCdCont +
                ", workRegionCont='" + workRegionCont + '\'' +
                ", recrutFieldCdNm='" + recrutFieldCdNm + '\'' +
                ", recrutFieldNm='" + recrutFieldNm + '\'' +
                ", emplmntPsncnt=" + emplmntPsncnt +
                '}';
    }
}
