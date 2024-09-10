package com.omniscient.omniscientback.api.JobabaAPI.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "jobabaAPI")
public class JobabaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobabaId;

    @Column(name = "jobaba_company_name", nullable = false)
    private String jobabaCompanyName;

    @Column(name = "jobaba_info_title", nullable = false)
    private String jobabaInfoTitle;

    @Column(name = "jobaba_wage_type")
    private String jobabaWageType;

    @Column(name = "jobaba_salary")
    private String jobabaSalary;

    @Column(name = "jobaba_location")
    private String jobabaLocation;

    @Column(name = "jobaba_employment_type")
    private String jobabaEmploymentType;

    @Column(name = "jobaba_career_condition")
    private String jobabaCareerCondition;

    @Column(name = "jobaba_posted_date")
    private LocalDate jobabaPostedDate;

    @Column(name = "jobaba_closing_date")
    private LocalDate jobabaClosingDate;

    @Column(name = "jobaba_web_info_url")
    private String jobabaWebInfoUrl;

    // 추가된 필드들
    @Column(name = "work_region_cd_cont")
    private Integer workRegionCdCont;

    @Column(name = "work_region_cont")
    private String workRegionCont;

    @Column(name = "recrut_field_cd_nm")
    private int recrutFieldCdNm;

    @Column(name = "recrut_field_nm")
    private String recrutFieldNm;

    @Column(name = "emplmnt_psncnt")
    private int emplmntPsncnt;

    public JobabaEntity() {
    }

    public JobabaEntity(Integer jobabaId, String jobabaCompanyName, String jobabaInfoTitle, String jobabaWageType, String jobabaSalary, String jobabaLocation, String jobabaEmploymentType, String jobabaCareerCondition, LocalDate jobabaPostedDate, LocalDate jobabaClosingDate, String jobabaWebInfoUrl, Integer workRegionCdCont, String workRegionCont, int recrutFieldCdNm, String recrutFieldNm, int emplmntPsncnt) {
        this.jobabaId = jobabaId;
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

    public Integer getJobabaId() {
        return jobabaId;
    }

    public void setJobabaId(Integer jobabaId) {
        this.jobabaId = jobabaId;
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
        return "JobabaEntity{" +
                "jobabaId=" + jobabaId +
                ", jobabaCompanyName='" + jobabaCompanyName + '\'' +
                ", jobabaInfoTitle='" + jobabaInfoTitle + '\'' +
                ", jobabaWageType='" + jobabaWageType + '\'' +
                ", jobabaSalary='" + jobabaSalary + '\'' +
                ", jobabaLocation='" + jobabaLocation + '\'' +
                ", jobabaEmploymentType='" + jobabaEmploymentType + '\'' +
                ", jobabaCareerCondition='" + jobabaCareerCondition + '\'' +
                ", jobabaPostedDate='" + jobabaPostedDate + '\'' +
                ", jobabaClosingDate='" + jobabaClosingDate + '\'' +
                ", jobabaWebInfoUrl='" + jobabaWebInfoUrl + '\'' +
                ", workRegionCdCont='" + workRegionCdCont + '\'' +
                ", workRegionCont='" + workRegionCont + '\'' +
                ", recrutFieldCdNm='" + recrutFieldCdNm + '\'' +
                ", recrutFieldNm='" + recrutFieldNm + '\'' +
                ", emplmntPsncnt=" + emplmntPsncnt +
                '}';
    }
}
