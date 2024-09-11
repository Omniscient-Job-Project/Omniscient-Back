package com.omniscient.omniscientback.api.gradejob.model;

public class GradeDTO {

    // 등급코드
    private String grdCd;

    // 통계 년도
    private String baseYY;

    // 결과코드
    private String resultCode;

    // 결과 메세지
    private String resultMsg;

    // 등급명
    private String grdNm;

    // 검정기관
    private String instiNm;

    // 종목명
    private String jmNm;

    // 전년대비 자격 취득 증가율
    private String preyyAcquQualIncRate;

    // 전년도 자격 취득 수
    private String preyyQualAcquCnt;

    // 자격취득 수
    private String qualAcquCnt;

    // 자격취득 순위
    private String qualAcquRank;

    // 통계년도
    private String statisYy;

    // 집계년도
    private String sumYy;

    // 페이지 당 데이터 수
    private String numOfRows;

    // 페이지 번호
    private String pageNo;

    // 데이터 총 개수
    private String totalCount;

    public GradeDTO() {
    }

    public GradeDTO(String grdCd, String baseYY, String resultCode, String resultMsg, String grdNm, String instiNm, String jmNm, String preyyAcquQualIncRate, String preyyQualAcquCnt, String qualAcquCnt, String qualAcquRank, String statisYy, String sumYy, String numOfRows, String pageNo, String totalCount) {
        this.grdCd = grdCd;
        this.baseYY = baseYY;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.grdNm = grdNm;
        this.instiNm = instiNm;
        this.jmNm = jmNm;
        this.preyyAcquQualIncRate = preyyAcquQualIncRate;
        this.preyyQualAcquCnt = preyyQualAcquCnt;
        this.qualAcquCnt = qualAcquCnt;
        this.qualAcquRank = qualAcquRank;
        this.statisYy = statisYy;
        this.sumYy = sumYy;
        this.numOfRows = numOfRows;
        this.pageNo = pageNo;
        this.totalCount = totalCount;
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
        return "GradeDTO{" +
                "grdCd='" + grdCd + '\'' +
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
