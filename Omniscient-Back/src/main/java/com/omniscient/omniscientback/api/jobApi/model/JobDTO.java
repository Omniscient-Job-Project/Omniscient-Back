package com.omniscient.omniscientback.api.jobApi.model;

public class JobDTO {

    private String jobId;
    private String jobCompanyName;
    private String jobInfoTitle;
    private String jobWageType;
    private String jobSalary;
    private String jobLocation;
    private String jobEmploymentType;
//    private LocalDate jobPostedDate;
//    private LocalDate jobClosingDate;
    private String jobWebInfoUrl;
    private String jobMobileInfoUrl;


    public JobDTO() {
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobCompanyName() {
        return jobCompanyName;
    }

    public void setJobCompanyName(String jobCompanyName) {
        this.jobCompanyName = jobCompanyName;
    }

    public String getJobInfoTitle() {
        return jobInfoTitle;
    }

    public void setJobInfoTitle(String jobInfoTitle) {
        this.jobInfoTitle = jobInfoTitle;
    }

    public String getJobWageType() {
        return jobWageType;
    }

    public void setJobWageType(String jobWageType) {
        this.jobWageType = jobWageType;
    }

    public String getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(String jobSalary) {
        this.jobSalary = jobSalary;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getJobEmploymentType() {
        return jobEmploymentType;
    }

    public void setJobEmploymentType(String jobEmploymentType) {
        this.jobEmploymentType = jobEmploymentType;
    }

    public String getJobWebInfoUrl() {
        return jobWebInfoUrl;
    }

    public void setJobWebInfoUrl(String jobWebInfoUrl) {
        this.jobWebInfoUrl = jobWebInfoUrl;
    }

    public String getJobMobileInfoUrl() {
        return jobMobileInfoUrl;
    }

    public void setJobMobileInfoUrl(String jobMobileInfoUrl) {
        this.jobMobileInfoUrl = jobMobileInfoUrl;
    }


    @Override
    public String toString() {
        return "JobDTO{" +
                "jobId='" + jobId + '\'' +
                ", jobCompanyName='" + jobCompanyName + '\'' +
                ", jobInfoTitle='" + jobInfoTitle + '\'' +
                ", jobWageType='" + jobWageType + '\'' +
                ", jobSalary='" + jobSalary + '\'' +
                ", jobLocation='" + jobLocation + '\'' +
                ", jobEmploymentType='" + jobEmploymentType + '\'' +
                ", jobWebInfoUrl='" + jobWebInfoUrl + '\'' +
                ", jobMobileInfoUrl='" + jobMobileInfoUrl + '\'' +
                '}';
    }
}
