package com.omniscient.omniscientback.api.categoryapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "category_api")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "jmfldnm")
    private String jmfldnm;             // 종목명

    @Column(name = "infogb")
    private String infogb;              // 정보구분

    @Column(name = "contents")
    private String contents;            // 내용

    @Column(name = "obligfldcd")
    private String obligfldcd;          // 대직무 분야코드

    @Column(name = "obligfldnm")
    private String obligfldnm;          // 대직무 분야명

    @Column(name = "mdobligfldcd")
    private String mdobligfldcd;        // 중직무 분야코드

    @Column(name = "mdobligfldnm")
    private String mdobligfldnm;        // 중직무 분야명

    public CategoryEntity() {
    }

    public CategoryEntity(Integer id, String jmfldnm, String infogb, String contents, String obligfldcd, String obligfldnm, String mdobligfldcd, String mdobligfldnm) {
        this.id = id;
        this.jmfldnm = jmfldnm;
        this.infogb = infogb;
        this.contents = contents;
        this.obligfldcd = obligfldcd;
        this.obligfldnm = obligfldnm;
        this.mdobligfldcd = mdobligfldcd;
        this.mdobligfldnm = mdobligfldnm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJmfldnm() {
        return jmfldnm;
    }

    public void setJmfldnm(String jmfldnm) {
        this.jmfldnm = jmfldnm;
    }

    public String getInfogb() {
        return infogb;
    }

    public void setInfogb(String infogb) {
        this.infogb = infogb;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getObligfldcd() {
        return obligfldcd;
    }

    public void setObligfldcd(String obligfldcd) {
        this.obligfldcd = obligfldcd;
    }

    public String getObligfldnm() {
        return obligfldnm;
    }

    public void setObligfldnm(String obligfldnm) {
        this.obligfldnm = obligfldnm;
    }

    public String getMdobligfldcd() {
        return mdobligfldcd;
    }

    public void setMdobligfldcd(String mdobligfldcd) {
        this.mdobligfldcd = mdobligfldcd;
    }

    public String getMdobligfldnm() {
        return mdobligfldnm;
    }

    public void setMdobligfldnm(String mdobligfldnm) {
        this.mdobligfldnm = mdobligfldnm;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", jmfldnm='" + jmfldnm + '\'' +
                ", infogb='" + infogb + '\'' +
                ", contents='" + contents + '\'' +
                ", obligfldcd='" + obligfldcd + '\'' +
                ", obligfldnm='" + obligfldnm + '\'' +
                ", mdobligfldcd='" + mdobligfldcd + '\'' +
                ", mdobligfldnm='" + mdobligfldnm + '\'' +
                '}';
    }
}
