package com.omniscient.omniscientback.api.JobabaAPI.model;

public class JobabaDTO {

    private String jobabaCompanyName;
    private String jobabaInfoTitle;
    private String jobabaWageType;
    private String jobabaSalary;
    private String jobabaLocation;
    private String jobabaEmploymentType;
//    private LocalDate jobabaPostedDate;
//    private LocalDate jobabaClosingDate;
    private String jobabaWebInfoUrl;
    private String jobabaMobileInfoUrl;
    private String jobabaCareerCondition;

    public JobabaDTO() {
    }

    public JobabaDTO(String jobabaCompanyName, String jobabaInfoTitle, String jobabaWageType, String jobabaSalary, String jobabaLocation, String jobabaEmploymentType, String jobabaWebInfoUrl, String jobabaMobileInfoUrl, String jobabaCareerCondition) {
        this.jobabaCompanyName = jobabaCompanyName;
        this.jobabaInfoTitle = jobabaInfoTitle;
        this.jobabaWageType = jobabaWageType;
        this.jobabaSalary = jobabaSalary;
        this.jobabaLocation = jobabaLocation;
        this.jobabaEmploymentType = jobabaEmploymentType;
        this.jobabaWebInfoUrl = jobabaWebInfoUrl;
        this.jobabaMobileInfoUrl = jobabaMobileInfoUrl;
        this.jobabaCareerCondition = jobabaCareerCondition;
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

    public String getJobabaCareerCondition() {
        return jobabaCareerCondition;
    }

    public void setJobabaCareerCondition(String jobabaCareerCondition) {
        this.jobabaCareerCondition = jobabaCareerCondition;
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
                ", jobabaWebInfoUrl='" + jobabaWebInfoUrl + '\'' +
                ", jobabaMobileInfoUrl='" + jobabaMobileInfoUrl + '\'' +
                ", jobabaCareerCondition='" + jobabaCareerCondition + '\'' +
                '}';
    }
}
