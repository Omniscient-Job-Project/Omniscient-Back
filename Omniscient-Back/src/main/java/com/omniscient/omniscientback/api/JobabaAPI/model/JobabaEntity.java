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


    @Column(name = "jobaba_web_info_url")
    private String jobabaWebInfoUrl;

    @Column(name = "jobaba_mobile_info_url")
    private String jobabaMobileInfoUrl;

    @Column(name = "jobaba_stauts")
    private String jobabaStatus;

    @Column(name = "jobaba_date")
    private LocalDate jobabaDate;

    @Column(name = "jobaba_title")
    private String jobabaTitle;

    @Column(name = "jobaba_carrer_condition")
    private String jobabaCareerCondition;

    public JobabaEntity() {
    }

    public JobabaEntity(Integer jobabaId, String jobabaCompanyName, String jobabaInfoTitle, String jobabaWageType, String jobabaSalary, String jobabaLocation, String jobabaEmploymentType, String jobabaWebInfoUrl, String jobabaMobileInfoUrl, String jobabaStatus, LocalDate jobabaDate, String jobabaTitle, String jobabaCareerCondition) {
        this.jobabaId = jobabaId;
        this.jobabaCompanyName = jobabaCompanyName;
        this.jobabaInfoTitle = jobabaInfoTitle;
        this.jobabaWageType = jobabaWageType;
        this.jobabaSalary = jobabaSalary;
        this.jobabaLocation = jobabaLocation;
        this.jobabaEmploymentType = jobabaEmploymentType;
        this.jobabaWebInfoUrl = jobabaWebInfoUrl;
        this.jobabaMobileInfoUrl = jobabaMobileInfoUrl;
        this.jobabaStatus = jobabaStatus;
        this.jobabaDate = jobabaDate;
        this.jobabaTitle = jobabaTitle;
        this.jobabaCareerCondition = jobabaCareerCondition;
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

    public String getJobabaWebInfoUrl() {
        return jobabaWebInfoUrl;
    }

    public void setJobabaWebInfoUrl(String jobabaWebInfoUrl) {
        this.jobabaWebInfoUrl = jobabaWebInfoUrl;
    }

    public String getJobabaMobileInfoUrl() {
        return jobabaMobileInfoUrl;
    }

    public void setJobabaMobileInfoUrl(String jobabaMobileInfoUrl) {
        this.jobabaMobileInfoUrl = jobabaMobileInfoUrl;
    }

    public String getJobabaStatus() {
        return jobabaStatus;
    }

    public void setJobabaStatus(String jobabaStatus) {
        this.jobabaStatus = jobabaStatus;
    }

    public LocalDate getJobabaDate() {
        return jobabaDate;
    }

    public void setJobabaDate(LocalDate jobabaDate) {
        this.jobabaDate = jobabaDate;
    }

    public String getJobabaTitle() {
        return jobabaTitle;
    }

    public void setJobabaTitle(String jobabaTitle) {
        this.jobabaTitle = jobabaTitle;
    }

    public String getJobabaCareerCondition() {
        return jobabaCareerCondition;
    }

    public void setJobabaCareerCondition(String jobabaCareerCondition) {
        this.jobabaCareerCondition = jobabaCareerCondition;
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
                ", jobabaWebInfoUrl='" + jobabaWebInfoUrl + '\'' +
                ", jobabaMobileInfoUrl='" + jobabaMobileInfoUrl + '\'' +
                ", jobabaStatus='" + jobabaStatus + '\'' +
                ", jobabaDate=" + jobabaDate +
                ", jobabaTitle='" + jobabaTitle + '\'' +
                ", jobabaCareerCondition='" + jobabaCareerCondition + '\'' +
                '}';
    }
}
