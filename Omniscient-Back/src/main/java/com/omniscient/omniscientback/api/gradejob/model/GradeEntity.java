package com.omniscient.omniscientback.api.gradejob.model;

import jakarta.persistence.*;

@Entity
@Table(name = "grade-job_api")
public class GradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    // 등급코드
    @Column(name = "grdCd")
    private String grdCd;

    // 통계 년도
    @Column(name = "baseYY")
    private String baseYY;

    // 결과코드
    @Column(name = "resultCode")
    private String resultCode;

    // 결과 메세지
    @Column(name = "resultMsg")
    private String resultMsg;

    // 등급명
    @Column(name = "grdNm")
    private String grdNm;

    // 검정기관
    @Column(name = "instiNm")
    private String instiNm;

    // 종목명
    @Column(name = "jmNm")
    private String jmNm;

    // 전년대비 자격 취득 증가율
    @Column(name = "preyyAcquQualIncRate")
    private String preyyAcquQualIncRate;

    // 전년도 자격 취득 수
    @Column(name = "preyyQualAcquCnt")
    private String preyyQualAcquCnt;

    // 자격취득 수
    @Column(name = "qualAcquCnt")
    private String qualAcquCnt;

    // 자격취득 순위
    @Column(name = "qualAcquRank")
    private String qualAcquRank;

    // 통계년도
    @Column(name = "statisYy")
    private String statisYy;

    // 집계년도
    @Column(name = "sumYy")
    private String sumYy;

    // 페이지 당 데이터 수
    @Column(name = "numOfRows")
    private String numOfRows;

    // 페이지 번호
    @Column(name = "pageNo")
    private String pageNo;

    // 데이터 총 개수
    @Column(name = "totalCount")
    private String totalCount;

    public GradeEntity() {
    }

    public GradeEntity(String totalCount, String pageNo, String numOfRows, String sumYy, String statisYy, String qualAcquRank, String qualAcquCnt, String preyyQualAcquCnt, String preyyAcquQualIncRate, String jmNm, String instiNm, String grdNm, String resultMsg, String resultCode, String baseYY, String grdCd) {
        this.totalCount = totalCount;
        this.pageNo = pageNo;
        this.numOfRows = numOfRows;
        this.sumYy = sumYy;
        this.statisYy = statisYy;
        this.qualAcquRank = qualAcquRank;
        this.qualAcquCnt = qualAcquCnt;
        this.preyyQualAcquCnt = preyyQualAcquCnt;
        this.preyyAcquQualIncRate = preyyAcquQualIncRate;
        this.jmNm = jmNm;
        this.instiNm = instiNm;
        this.grdNm = grdNm;
        this.resultMsg = resultMsg;
        this.resultCode = resultCode;
        this.baseYY = baseYY;
        this.grdCd = grdCd;
    }

    public String getGrdCd() {
        return grdCd;
    }

    public void setGrdCd(String grdCd) {
        this.grdCd = grdCd;
    }

    public String getBaseYY() {
        return baseYY;
    }

    public void setBaseYY(String baseYY) {
        this.baseYY = baseYY;
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

    public String getGrdNm() {
        return grdNm;
    }

    public void setGrdNm(String grdNm) {
        this.grdNm = grdNm;
    }

    public String getInstiNm() {
        return instiNm;
    }

    public void setInstiNm(String instiNm) {
        this.instiNm = instiNm;
    }

    public String getJmNm() {
        return jmNm;
    }

    public void setJmNm(String jmNm) {
        this.jmNm = jmNm;
    }

    public String getPreyyAcquQualIncRate() {
        return preyyAcquQualIncRate;
    }

    public void setPreyyAcquQualIncRate(String preyyAcquQualIncRate) {
        this.preyyAcquQualIncRate = preyyAcquQualIncRate;
    }

    public String getPreyyQualAcquCnt() {
        return preyyQualAcquCnt;
    }

    public void setPreyyQualAcquCnt(String preyyQualAcquCnt) {
        this.preyyQualAcquCnt = preyyQualAcquCnt;
    }

    public String getQualAcquCnt() {
        return qualAcquCnt;
    }

    public void setQualAcquCnt(String qualAcquCnt) {
        this.qualAcquCnt = qualAcquCnt;
    }

    public String getQualAcquRank() {
        return qualAcquRank;
    }

    public void setQualAcquRank(String qualAcquRank) {
        this.qualAcquRank = qualAcquRank;
    }

    public String getStatisYy() {
        return statisYy;
    }

    public void setStatisYy(String statisYy) {
        this.statisYy = statisYy;
    }

    public String getSumYy() {
        return sumYy;
    }

    public void setSumYy(String sumYy) {
        this.sumYy = sumYy;
    }

    public String getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(String numOfRows) {
        this.numOfRows = numOfRows;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "GradeEntity{" +
                "id=" + id +
                ", grdCd='" + grdCd + '\'' +
                ", baseYY='" + baseYY + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", grdNm='" + grdNm + '\'' +
                ", instiNm='" + instiNm + '\'' +
                ", jmNm='" + jmNm + '\'' +
                ", preyyAcquQualIncRate='" + preyyAcquQualIncRate + '\'' +
                ", preyyQualAcquCnt='" + preyyQualAcquCnt + '\'' +
                ", qualAcquCnt='" + qualAcquCnt + '\'' +
                ", qualAcquRank='" + qualAcquRank + '\'' +
                ", statisYy='" + statisYy + '\'' +
                ", sumYy='" + sumYy + '\'' +
                ", numOfRows='" + numOfRows + '\'' +
                ", pageNo='" + pageNo + '\'' +
                ", totalCount='" + totalCount + '\'' +
                '}';
    }
}
