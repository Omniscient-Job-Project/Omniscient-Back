package com.omniscient.omniscientback.curation.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "curation")
public class Curation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curation_id", nullable = false)
    private Integer curationId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "comments_id")
    private Integer commentsId;

    @Column(name = "curation_title", length = 50, nullable = false)
    private String curationTitle;

    @Column(name = "curation_contents", length = 3000, nullable = false)
    private String curationContents;

    @Column(name = "image_path")
    private String imagePath;

    @CreationTimestamp
    @Column(name = "curation_date")
    private String curationDate;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "curation_stauts")
    private boolean curationStatus;

    public Curation() {
    }

    public Curation(Integer curationId, Integer userId, Integer commentsId, String curationTitle, String curationContents, String imagePath, String curationDate, int viewCount, boolean curationStatus) {
        this.curationId = curationId;
        this.userId = userId;
        this.commentsId = commentsId;
        this.curationTitle = curationTitle;
        this.curationContents = curationContents;
        this.imagePath = imagePath;
        this.curationDate = curationDate;
        this.viewCount = viewCount;
        this.curationStatus = curationStatus;
    }

    public Integer getCurationId() {
        return curationId;
    }

    public void setCurationId(Integer curationId) {
        this.curationId = curationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(Integer commentsId) {
        this.commentsId = commentsId;
    }

    public String getCurationTitle() {
        return curationTitle;
    }

    public void setCurationTitle(String curationTitle) {
        this.curationTitle = curationTitle;
    }

    public String getCurationContents() {
        return curationContents;
    }

    public void setCurationContents(String curationContents) {
        this.curationContents = curationContents;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCurationDate() {
        return curationDate;
    }

    public void setCurationDate(String curationDate) {
        this.curationDate = curationDate;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public boolean isCurationStatus() {
        return curationStatus;
    }

    public void setCurationStatus(boolean curationStatus) {
        this.curationStatus = curationStatus;
    }

    @Override
    public String toString() {
        return "Curation{" +
                "curationId=" + curationId +
                ", userId=" + userId +
                ", commentsId=" + commentsId +
                ", curationTitle='" + curationTitle + '\'' +
                ", curationContents='" + curationContents + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", curationDate='" + curationDate + '\'' +
                ", viewCount=" + viewCount +
                ", curationStatus=" + curationStatus +
                '}';
    }
}
