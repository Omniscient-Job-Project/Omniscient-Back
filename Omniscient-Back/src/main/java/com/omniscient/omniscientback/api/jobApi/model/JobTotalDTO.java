package com.omniscient.omniscientback.api.jobApi.model;

import java.time.LocalDate;

public class JobTotalDTO {

    private String companyName; // 회사 이름 (공통)
    private String infoTitle; // 채용 정보 제목 (공통)
    private String wageType; // 급여 형태 (공통)
    private String salary; // 급여 금액 (공통)
    private String location; // 근무 위치 (공통)
    private String employmentType; // 고용 형태 (공통)
    private String careerCondition; // 경력 조건 (공통)
    private LocalDate postedDate; // 채용 공고 게시일 (공통)
    private LocalDate closingDate; // 채용 공고 마감일 (공통)
    private String webInfoUrl; // 웹에서 공고를 볼 수 있는 URL (공통)

    // Jobaba 전용 필드
    private Integer workRegionCdCont; // 근무 지역 코드 (상세 주소)
    private String workRegionCont; // 근무 지역 명칭 (상세 주소)
    private int recrutFieldCdNm; // 모집 분야 코드
    private String recrutFieldNm; // 모집 분야 명칭
    private int emplmntPsncnt; // 모집 인원 수

    // 서울시 전용 필드
    private String mobileInfoUrl; // 모바일에서 공고를 볼 수 있는 URL

    public JobTotalDTO() {
    }

    public JobTotalDTO(String companyName, String infoTitle, String wageType, String salary, String location, String employmentType, String careerCondition, LocalDate postedDate, LocalDate closingDate, String webInfoUrl, Integer workRegionCdCont, String workRegionCont, int recrutFieldCdNm, String recrutFieldNm, int emplmntPsncnt, String mobileInfoUrl) {
        this.companyName = companyName;
        this.infoTitle = infoTitle;
        this.wageType = wageType;
        this.salary = salary;
        this.location = location;
        this.employmentType = employmentType;
        this.careerCondition = careerCondition;
        this.postedDate = postedDate;
        this.closingDate = closingDate;
        this.webInfoUrl = webInfoUrl;
        this.workRegionCdCont = workRegionCdCont;
        this.workRegionCont = workRegionCont;
        this.recrutFieldCdNm = recrutFieldCdNm;
        this.recrutFieldNm = recrutFieldNm;
        this.emplmntPsncnt = emplmntPsncnt;
        this.mobileInfoUrl = mobileInfoUrl;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public String getWageType() {
        return wageType;
    }

    public void setWageType(String wageType) {
        this.wageType = wageType;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getCareerCondition() {
        return careerCondition;
    }

    public void setCareerCondition(String careerCondition) {
        this.careerCondition = careerCondition;
    }

    public LocalDate getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDate postedDate) {
        this.postedDate = postedDate;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    public String getWebInfoUrl() {
        return webInfoUrl;
    }

    public void setWebInfoUrl(String webInfoUrl) {
        this.webInfoUrl = webInfoUrl;
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

    public String getMobileInfoUrl() {
        return mobileInfoUrl;
    }

    public void setMobileInfoUrl(String mobileInfoUrl) {
        this.mobileInfoUrl = mobileInfoUrl;
    }

    @Override
    public String toString() {
        return "JobTotalDTO{" +
                "companyName='" + companyName + '\'' +
                ", infoTitle='" + infoTitle + '\'' +
                ", wageType='" + wageType + '\'' +
                ", salary='" + salary + '\'' +
                ", location='" + location + '\'' +
                ", employmentType='" + employmentType + '\'' +
                ", careerCondition='" + careerCondition + '\'' +
                ", postedDate=" + postedDate +
                ", closingDate=" + closingDate +
                ", webInfoUrl='" + webInfoUrl + '\'' +
                ", workRegionCdCont=" + workRegionCdCont +
                ", workRegionCont='" + workRegionCont + '\'' +
                ", recrutFieldCdNm=" + recrutFieldCdNm +
                ", recrutFieldNm='" + recrutFieldNm + '\'' +
                ", emplmntPsncnt=" + emplmntPsncnt +
                ", mobileInfoUrl='" + mobileInfoUrl + '\'' +
                '}';
    }
}
