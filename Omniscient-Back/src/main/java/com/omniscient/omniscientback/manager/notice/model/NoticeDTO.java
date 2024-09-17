package com.omniscient.omniscientback.manager.notice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class NoticeDTO {

    private Integer userId;
    private String noticeTitle;
    private String noticeContent;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime noticeCreateAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime noticeUpdateAt;

    private Boolean noticeStatus = true;  // 기본값은 true로 설정
    private Integer noticeViews = 0;  // 조회수 필드 추가

    // 기본 생성자
    public NoticeDTO() {
    }

    // Getter 및 Setter
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

    public void setNoticeStatus(Boolean noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public Integer getNoticeViews() {
        return noticeViews;
    }

    public void setNoticeViews(Integer noticeViews) {
        this.noticeViews = noticeViews;
    }

    @Override
    public String toString() {
        return "NoticeDTO{" +
                "userId=" + userId +
                ", noticeTitle='" + noticeTitle + '\'' +
                ", noticeContent='" + noticeContent + '\'' +
                ", noticeCreateAt=" + noticeCreateAt +
                ", noticeUpdateAt=" + noticeUpdateAt +
                ", noticeStatus=" + noticeStatus +
                ", noticeViews=" + noticeViews +
                '}';
    }
}
