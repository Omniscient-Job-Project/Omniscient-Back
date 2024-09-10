package com.omniscient.omniscientback.manager.notice.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id", updatable = false, nullable = false)
    private Integer noticeId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "notice_title")
    private String noticeTitle;

    @Column(name = "notice_content")
    private String noticeContent;

    @Column(name = "notice_create_at")
    private LocalDateTime noticeCreateAt;

    @Column(name = "notice_update_at")
    private LocalDateTime noticeUpdateAt;

    // 공지사항의 활성화 상태 (true: 활성, false: 삭제된 상태)
    @Column(name = "status")
    private Boolean status = true;  // 기본값은 true로 설정

    public Notice() {
    }

    public Notice(Integer noticeId, Integer userId, String noticeTitle, String noticeContent, LocalDateTime noticeCreateAt, LocalDateTime noticeUpdateAt, Boolean status) {
        this.noticeId = noticeId;
        this.userId = userId;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.noticeCreateAt = noticeCreateAt;
        this.noticeUpdateAt = noticeUpdateAt;
        this.status = status;  // 상태 필드 추가
    }

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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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
                ", status=" + status +   // 상태 필드 포함
                '}';
    }
}
