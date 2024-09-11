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

    @Column(name = "user_id", nullable = false)  // 필수 입력 필드
    private Integer userId;

    @Column(name = "notice_title", nullable = false)  // 필수 입력 필드
    private String noticeTitle;

    @Column(name = "notice_content", length = 1000, nullable = false)  // 필수 입력 필드
    private String noticeContent;

    @Column(name = "notice_create_at", nullable = false)  // 날짜 및 시간 필드
    private LocalDateTime noticeCreateAt;

    @Column(name = "notice_update_at", nullable = false)  // 날짜 및 시간 필드
    private LocalDateTime noticeUpdateAt;

    @Column(name = "noticeStatus", nullable = false)  // 상태 필드
    private Boolean noticeStatus = true;  // 기본값은 true로 설정

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
                '}';
    }
}
