package com.omniscient.omniscientback.api.testapi.model;

public class TestDTO {

    // 응답 데이터 포맷
    private String dataFormat;

    // 시행년도
    private String implYy;

    // 결과 코드
    private String resultCode;

    // 결과 메시지
    private String resultMsg;

    // 시행회차
    private String implSeq;

    // 자격구분 명
    private String qualgbNm;

    // 설명
    private String description;

    // 필기시험 원서접수 시작일자
    private String docRegStartDt;

    // 필기시험 원서접수 종료일자
    private String docRegEndDt;

    // 필기시험 시작일자
    private String docExamStartDt;

    // 필기시험 종료일자
    private String docExamEndDt;

    // 필기시험 합격(예정)자 발표일자
    private String docPassDt;

    // 실기(작업)/면접 시험 원서접수 시작일자
    private String pracRegStartDt;

    // 실기(작업)/면접 시험 원서접수 종료일자
    private String pracRegEndDt;

    // 실기(작업)/면접 시험 시작일자
    private String pracExamStartDt;

    // 실기(작업)/면접 시험 종료일자
    private String pracExamEndDt;

    // 실기(작업)/면접 합격자 발표일자
    private String pracPassDt;

    // 데이터 총 개수
    private String totalCount;

    public TestDTO() {
    }

    public TestDTO(String dataFormat, String implYy, String resultCode, String resultMsg, String implSeq, String qualgbNm, String description, String docRegStartDt, String docRegEndDt, String docExamStartDt, String docExamEndDt, String docPassDt, String pracRegStartDt, String pracRegEndDt, String pracExamStartDt, String pracExamEndDt, String pracPassDt, String totalCount) {
        this.dataFormat = dataFormat;
        this.implYy = implYy;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.implSeq = implSeq;
        this.qualgbNm = qualgbNm;
        this.description = description;
        this.docRegStartDt = docRegStartDt;
        this.docRegEndDt = docRegEndDt;
        this.docExamStartDt = docExamStartDt;
        this.docExamEndDt = docExamEndDt;
        this.docPassDt = docPassDt;
        this.pracRegStartDt = pracRegStartDt;
        this.pracRegEndDt = pracRegEndDt;
        this.pracExamStartDt = pracExamStartDt;
        this.pracExamEndDt = pracExamEndDt;
        this.pracPassDt = pracPassDt;
        this.totalCount = totalCount;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getImplYy() {
        return implYy;
    }

    public void setImplYy(String implYy) {
        this.implYy = implYy;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getImplSeq() {
        return implSeq;
    }

    public void setImplSeq(String implSeq) {
        this.implSeq = implSeq;
    }

    public String getQualgbNm() {
        return qualgbNm;
    }

    public void setQualgbNm(String qualgbNm) {
        this.qualgbNm = qualgbNm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocRegStartDt() {
        return docRegStartDt;
    }

    public void setDocRegStartDt(String docRegStartDt) {
        this.docRegStartDt = docRegStartDt;
    }

    public String getDocRegEndDt() {
        return docRegEndDt;
    }

    public void setDocRegEndDt(String docRegEndDt) {
        this.docRegEndDt = docRegEndDt;
    }

    public String getDocExamStartDt() {
        return docExamStartDt;
    }

    public void setDocExamStartDt(String docExamStartDt) {
        this.docExamStartDt = docExamStartDt;
    }

    public String getDocExamEndDt() {
        return docExamEndDt;
    }

    public void setDocExamEndDt(String docExamEndDt) {
        this.docExamEndDt = docExamEndDt;
    }

    public String getDocPassDt() {
        return docPassDt;
    }

    public void setDocPassDt(String docPassDt) {
        this.docPassDt = docPassDt;
    }

    public String getPracRegStartDt() {
        return pracRegStartDt;
    }

    public void setPracRegStartDt(String pracRegStartDt) {
        this.pracRegStartDt = pracRegStartDt;
    }

    public String getPracRegEndDt() {
        return pracRegEndDt;
    }

    public void setPracRegEndDt(String pracRegEndDt) {
        this.pracRegEndDt = pracRegEndDt;
    }

    public String getPracExamStartDt() {
        return pracExamStartDt;
    }

    public void setPracExamStartDt(String pracExamStartDt) {
        this.pracExamStartDt = pracExamStartDt;
    }

    public String getPracExamEndDt() {
        return pracExamEndDt;
    }

    public void setPracExamEndDt(String pracExamEndDt) {
        this.pracExamEndDt = pracExamEndDt;
    }

    public String getPracPassDt() {
        return pracPassDt;
    }

    public void setPracPassDt(String pracPassDt) {
        this.pracPassDt = pracPassDt;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "TestDTO{" +
                ", dataFormat='" + dataFormat + '\'' +
                ", implYy='" + implYy + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", implSeq='" + implSeq + '\'' +
                ", qualgbNm='" + qualgbNm + '\'' +
                ", description='" + description + '\'' +
                ", docRegStartDt='" + docRegStartDt + '\'' +
                ", docRegEndDt='" + docRegEndDt + '\'' +
                ", docExamStartDt='" + docExamStartDt + '\'' +
                ", docExamEndDt='" + docExamEndDt + '\'' +
                ", docPassDt='" + docPassDt + '\'' +
                ", pracRegStartDt='" + pracRegStartDt + '\'' +
                ", pracRegEndDt='" + pracRegEndDt + '\'' +
                ", pracExamStartDt='" + pracExamStartDt + '\'' +
                ", pracExamEndDt='" + pracExamEndDt + '\'' +
                ", pracPassDt='" + pracPassDt + '\'' +
                ", totalCount='" + totalCount + '\'' +
                '}';
    }
}
