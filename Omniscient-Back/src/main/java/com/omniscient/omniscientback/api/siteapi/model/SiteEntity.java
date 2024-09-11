package com.omniscient.omniscientback.api.siteapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "site_api")
public class SiteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 한 페이지 결과 수
    @Column(name = "numOfRows")
    private String numOfRows;

    // 페이지 번호
    @Column(name = "pageNo")
    private String pageNo;

    // 지사코드
    @Column(name = "brchCd")
    private String brchCd;

    // 시행장소 주소
    @Column(name = "address")
    private String address;

    // 지사 명
    @Column(name = "brchNm")
    private String brchNm;

    // 시행장소 구분
    @Column(name = "examAreaGbNm")
    private String examAreaGbNm;

    // 시행장소 명
    @Column(name = "examAreaNm")
    private String examAreaNm;

    // 장소위치 안내
    @Column(name = "plceLoctGid")
    private String plceLoctGid;

    // 전화번호
    @Column(name = "telNo")
    private String telNo;

    // 결과 코드
    @Column(name = "resultCode")
    private String resultCode;

    // 결과 메시지
    @Column(name = "resultMsg")
    private String resultMsg;

    // 데이터 총 개수
    @Column(name = "totalCount")
    private String totalCount;

    public SiteEntity() {
    }

    public SiteEntity(Integer id, String numOfRows, String pageNo, String brchCd, String address, String brchNm, String examAreaGbNm, String examAreaNm, String plceLoctGid, String telNo, String resultCode, String resultMsg, String totalCount) {
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return "SiteEntity{" +
                "id=" + id +
                ", numOfRows='" + numOfRows + '\'' +
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
