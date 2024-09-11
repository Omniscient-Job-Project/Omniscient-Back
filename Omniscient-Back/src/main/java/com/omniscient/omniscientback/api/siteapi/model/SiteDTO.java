package com.omniscient.omniscientback.api.siteapi.model;

public class SiteDTO {

    // 한 페이지 결과 수
    private String numOfRows;

    // 페이지 번호
    private String pageNo;

    // 지사코드
    private String brchCd;

    // 시행장소 주소
    private String address;

    // 지사 명
    private String brchNm;

    // 시행장소 구분
    private String examAreaGbNm;

    // 시행장소 명
    private String examAreaNm;

    // 장소위치 안내
    private String plceLoctGid;

    // 전화번호
    private String telNo;

    // 결과 코드
    private String resultCode;

    // 결과 메시지
    private String resultMsg;

    // 데이터 총 개수
    private String totalCount;

    public SiteDTO() {
    }

    public SiteDTO(String numOfRows, String pageNo, String brchCd, String address, String brchNm, String examAreaGbNm, String examAreaNm, String plceLoctGid, String telNo, String resultCode, String resultMsg, String totalCount) {
        this.numOfRows = numOfRows;
        this.pageNo = pageNo;
        this.brchCd = brchCd;
        this.address = address;
        this.brchNm = brchNm;
        this.examAreaGbNm = examAreaGbNm;
        this.examAreaNm = examAreaNm;
        this.plceLoctGid = plceLoctGid;
        this.telNo = telNo;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.totalCount = totalCount;
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

    public String getBrchCd() {
        return brchCd;
    }

    public void setBrchCd(String brchCd) {
        this.brchCd = brchCd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrchNm() {
        return brchNm;
    }

    public void setBrchNm(String brchNm) {
        this.brchNm = brchNm;
    }

    public String getExamAreaGbNm() {
        return examAreaGbNm;
    }

    public void setExamAreaGbNm(String examAreaGbNm) {
        this.examAreaGbNm = examAreaGbNm;
    }

    public String getExamAreaNm() {
        return examAreaNm;
    }

    public void setExamAreaNm(String examAreaNm) {
        this.examAreaNm = examAreaNm;
    }

    public String getPlceLoctGid() {
        return plceLoctGid;
    }

    public void setPlceLoctGid(String plceLoctGid) {
        this.plceLoctGid = plceLoctGid;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
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

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "SiteDTO{" +
                "numOfRows='" + numOfRows + '\'' +
                ", pageNo='" + pageNo + '\'' +
                ", brchCd='" + brchCd + '\'' +
                ", address='" + address + '\'' +
                ", brchNm='" + brchNm + '\'' +
                ", examAreaGbNm='" + examAreaGbNm + '\'' +
                ", examAreaNm='" + examAreaNm + '\'' +
                ", plceLoctGid='" + plceLoctGid + '\'' +
                ", telNo='" + telNo + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                ", totalCount='" + totalCount + '\'' +
                '}';
    }
}
