package com.omniscient.omniscientback.api.testapi.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "test_api")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 시행년도
    @Column(name = "implYy")
    private String implYy;

    // 결과 코드
    @Column(name = "resultCode")
    private String resultCode;

    // 결과 메시지
    @Column(name = "resultMsg")
    private String resultMsg;

    // 시행회차
    @Column(name = "implSeq")
    private String implSeq;

    // 자격구분 명
    @Column(name = "qualgbNm")
    private String qualgbNm;

    // 설명
    @Column(name = "description")
    private String description;

    // 필기시험 원서접수 시작일자
    @Column(name = "docRegStartDt")
    private LocalDate docRegStartDt;

    // 필기시험 원서접수 종료일자
    @Column(name = "docRegEndDt")
    private LocalDate docRegEndDt;

    // 필기시험 시작일자
    @Column(name = "docExamStartDt")
    private LocalDate docExamStartDt;

    // 필기시험 종료일자
    @Column(name = "docExamEndDt")
    private LocalDate docExamEndDt;

    // 필기시험 합격(예정)자 발표일자
    @Column(name = "docPassDt")
    private LocalDate docPassDt;

    // 실기(작업)/면접 시험 원서접수 시작일자
    @Column(name = "pracRegStartDt")
    private LocalDate pracRegStartDt;

    // 실기(작업)/면접 시험 원서접수 종료일자
    @Column(name = "pracRegEndDt")
    private LocalDate pracRegEndDt;

    // 실기(작업)/면접 시험 시작일자
    @Column(name = "pracExamStartDt")
    private LocalDate pracExamStartDt;

    // 실기(작업)/면접 시험 종료일자
    @Column(name = "pracExamEndDt")
    private LocalDate pracExamEndDt;

    // 실기(작업)/면접 합격자 발표일자
    @Column(name = "pracPassDt")
    private LocalDate pracPassDt;

    // 데이터 총 개수
    @Column(name = "totalCount")
    private String totalCount;

    public TestEntity() {
    }

    public TestEntity(String implYy, String resultCode, String resultMsg, String implSeq, String qualgbNm, String description, LocalDate docRegStartDt, LocalDate docRegEndDt, LocalDate docExamStartDt, LocalDate docExamEndDt, LocalDate docPassDt, LocalDate pracRegStartDt, LocalDate pracRegEndDt, LocalDate pracExamStartDt, LocalDate pracExamEndDt, LocalDate pracPassDt, String totalCount) {
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

    public LocalDate getDocRegStartDt() {
        return docRegStartDt;
    }

    public void setDocRegStartDt(LocalDate docRegStartDt) {
        this.docRegStartDt = docRegStartDt;
    }

    public LocalDate getDocRegEndDt() {
        return docRegEndDt;
    }

    public void setDocRegEndDt(LocalDate docRegEndDt) {
        this.docRegEndDt = docRegEndDt;
    }

    public LocalDate getDocExamStartDt() {
        return docExamStartDt;
    }

    public void setDocExamStartDt(LocalDate docExamStartDt) {
        this.docExamStartDt = docExamStartDt;
    }

    public LocalDate getDocExamEndDt() {
        return docExamEndDt;
    }

    public void setDocExamEndDt(LocalDate docExamEndDt) {
        this.docExamEndDt = docExamEndDt;
    }

    public LocalDate getDocPassDt() {
        return docPassDt;
    }

    public void setDocPassDt(LocalDate docPassDt) {
        this.docPassDt = docPassDt;
    }

    public LocalDate getPracRegStartDt() {
        return pracRegStartDt;
    }

    public void setPracRegStartDt(LocalDate pracRegStartDt) {
        this.pracRegStartDt = pracRegStartDt;
    }

    public LocalDate getPracRegEndDt() {
        return pracRegEndDt;
    }

    public void setPracRegEndDt(LocalDate pracRegEndDt) {
        this.pracRegEndDt = pracRegEndDt;
    }

    public LocalDate getPracExamStartDt() {
        return pracExamStartDt;
    }

    public void setPracExamStartDt(LocalDate pracExamStartDt) {
        this.pracExamStartDt = pracExamStartDt;
    }

    public LocalDate getPracExamEndDt() {
        return pracExamEndDt;
    }

    public void setPracExamEndDt(LocalDate pracExamEndDt) {
        this.pracExamEndDt = pracExamEndDt;
    }

    public LocalDate getPracPassDt() {
        return pracPassDt;
    }

    public void setPracPassDt(LocalDate pracPassDt) {
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
        return "TestEntity{" +
                "implYy='" + implYy + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", implSeq='" + implSeq + '\'' +
                ", qualgbNm='" + qualgbNm + '\'' +
                ", description='" + description + '\'' +
                ", docRegStartDt=" + docRegStartDt +
                ", docRegEndDt=" + docRegEndDt +
                ", docExamStartDt=" + docExamStartDt +
                ", docExamEndDt=" + docExamEndDt +
                ", docPassDt=" + docPassDt +
                ", pracRegStartDt=" + pracRegStartDt +
                ", pracRegEndDt=" + pracRegEndDt +
                ", pracExamStartDt=" + pracExamStartDt +
                ", pracExamEndDt=" + pracExamEndDt +
                ", pracPassDt=" + pracPassDt +
                ", totalCount='" + totalCount + '\'' +
                '}';
    }
}
