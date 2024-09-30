package com.omniscient.omniscientback.api.employmentapi.model;


import jakarta.persistence.*;

@Entity
@Table(name = "employment_api")
public class EmploymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "divNm")
    private String divNm;                // 여성 일자리 종류

    @Column(name = "regionNm")
    private String regionNm;             // 지역 이름

    @Column(name = "instNm")
    private String instNm;               // 기관 이름

    @Column(name = "contctNm")
    private String contctNm;             // 연락처

    @Column(name = "hmpgNm")
    private String hmpgNm;               // 홈페이지 주소

    @Column(name = "refineRoadnmAddr")
    private String refineRoadnmAddr;     // 도로명 주소

    @Column(name = "refineLotnoAddr")
    private String refineLotnoAddr;      // 지번 주소

    @Column(name = "refineZipNo")
    private String refineZipNo;          // 우편번호

    @Column(name = "refineWgs84Lat")
    private String refineWgs84Lat;       // 위도

    @Column(name = "refineWgs84Logt")
    private String refineWgs84Logt;      // 경도

    public EmploymentEntity() {
    }

    public EmploymentEntity(Integer id, String divNm, String regionNm, String instNm, String contctNm, String hmpgNm, String refineRoadnmAddr, String refineLotnoAddr, String refineZipNo, String refineWgs84Lat, String refineWgs84Logt) {
        this.id = id;
        this.divNm = divNm;
        this.regionNm = regionNm;
        this.instNm = instNm;
        this.contctNm = contctNm;
        this.hmpgNm = hmpgNm;
        this.refineRoadnmAddr = refineRoadnmAddr;
        this.refineLotnoAddr = refineLotnoAddr;
        this.refineZipNo = refineZipNo;
        this.refineWgs84Lat = refineWgs84Lat;
        this.refineWgs84Logt = refineWgs84Logt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDivNm() {
        return divNm;
    }

    public void setDivNm(String divNm) {
        this.divNm = divNm;
    }

    public String getRegionNm() {
        return regionNm;
    }

    public void setRegionNm(String regionNm) {
        this.regionNm = regionNm;
    }

    public String getInstNm() {
        return instNm;
    }

    public void setInstNm(String instNm) {
        this.instNm = instNm;
    }

    public String getContctNm() {
        return contctNm;
    }

    public void setContctNm(String contctNm) {
        this.contctNm = contctNm;
    }

    public String getHmpgNm() {
        return hmpgNm;
    }

    public void setHmpgNm(String hmpgNm) {
        this.hmpgNm = hmpgNm;
    }

    public String getRefineRoadnmAddr() {
        return refineRoadnmAddr;
    }

    public void setRefineRoadnmAddr(String refineRoadnmAddr) {
        this.refineRoadnmAddr = refineRoadnmAddr;
    }

    public String getRefineLotnoAddr() {
        return refineLotnoAddr;
    }

    public void setRefineLotnoAddr(String refineLotnoAddr) {
        this.refineLotnoAddr = refineLotnoAddr;
    }

    public String getRefineZipNo() {
        return refineZipNo;
    }

    public void setRefineZipNo(String refineZipNo) {
        this.refineZipNo = refineZipNo;
    }

    public String getRefineWgs84Lat() {
        return refineWgs84Lat;
    }

    public void setRefineWgs84Lat(String refineWgs84Lat) {
        this.refineWgs84Lat = refineWgs84Lat;
    }

    public String getRefineWgs84Logt() {
        return refineWgs84Logt;
    }

    public void setRefineWgs84Logt(String refineWgs84Logt) {
        this.refineWgs84Logt = refineWgs84Logt;
    }

    @Override
    public String toString() {
        return "EmploymentEntity{" +
                "id=" + id +
                ", divNm='" + divNm + '\'' +
                ", regionNm='" + regionNm + '\'' +
                ", instNm='" + instNm + '\'' +
                ", contctNm='" + contctNm + '\'' +
                ", hmpgNm='" + hmpgNm + '\'' +
                ", refineRoadnmAddr='" + refineRoadnmAddr + '\'' +
                ", refineLotnoAddr='" + refineLotnoAddr + '\'' +
                ", refineZipNo='" + refineZipNo + '\'' +
                ", refineWgs84Lat='" + refineWgs84Lat + '\'' +
                ", refineWgs84Logt='" + refineWgs84Logt + '\'' +
                '}';
    }
}
