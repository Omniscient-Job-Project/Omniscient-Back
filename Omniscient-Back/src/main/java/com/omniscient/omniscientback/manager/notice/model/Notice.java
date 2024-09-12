package com.omniscient.omniscientback.manager.notice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id", nullable = false)
    private Integer noticeId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "notice_title", nullable = false)
    private String noticeTitle;

    @Column(name = "notice_content", length = 1000, nullable = false)
    private String noticeContent;

    @Column(name = "notice_create_at", nullable = false)
    private LocalDateTime noticeCreateAt;

    @Column(name = "notice_update_at", nullable = false)
    private LocalDateTime noticeUpdateAt;

    @Column(name = "notice_status", nullable = false)
    private Boolean noticeStatus = true;

    @Column(name = "notice_views")  // 조회수 필드 추가
    private Integer noticeViews = 0;

    public Notice() {
    }

    public Notice(Integer noticeId, Integer userId, String noticeTitle, String noticeContent, LocalDateTime noticeCreateAt, LocalDateTime noticeUpdateAt, Boolean noticeStatus, Integer noticeViews) {
        this.noticeId = noticeId;
        this.userId = userId;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.noticeCreateAt = noticeCreateAt;
        this.noticeUpdateAt = noticeUpdateAt;
        this.noticeStatus = noticeStatus;
        this.noticeViews = noticeViews;
    }

    // Getters and Setters
    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public LocalDateTime getNoticeCreateAt() {
        return noticeCreateAt;
    }

    public void setNoticeCreateAt(LocalDateTime noticeCreateAt) {
        this.noticeCreateAt = noticeCreateAt;
    }

    public LocalDateTime getNoticeUpdateAt() {
        return noticeUpdateAt;
    }

    public void setNoticeUpdateAt(LocalDateTime noticeUpdateAt) {
        this.noticeUpdateAt = noticeUpdateAt;
    }

    public Boolean getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(Boolean status) {
        this.noticeStatus = status;
    }

    public Integer getNoticeViews() {
        return noticeViews;
    }

    public void setNoticeViews(Integer noticeViews) {
        this.noticeViews = noticeViews;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "noticeId=" + noticeId +
                ", userId=" + userId +
                ", noticeTitle='" + noticeTitle + '\'' +
                ", noticeContent='" + noticeContent + '\'' +
                ", noticeCreateAt=" + noticeCreateAt +
                ", noticeUpdateAt=" + noticeUpdateAt +
                ", status=" + noticeStatus +
                ", views=" + noticeViews +
                '}';
    }
}
