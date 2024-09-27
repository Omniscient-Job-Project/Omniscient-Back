package com.omniscient.omniscientback.mypage.model;

import java.time.LocalDateTime;

public class FileUploadDTO {

    private Integer id;
    private String fileName;
    private String originalFileName;
    private String contentType;
    private Integer size;
    private LocalDateTime uploadDateTime;
    private boolean active;
    private String fileUrl;

    public FileUploadDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public LocalDateTime getUploadDateTime() {
        return uploadDateTime;
    }

    public void setUploadDateTime(LocalDateTime uploadDateTime) {
        this.uploadDateTime = uploadDateTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    // 새로 추가된 생성자
    public FileUploadDTO(Integer id, String fileName, String originalFileName, String contentType, Integer size, LocalDateTime uploadDateTime, boolean active) {
        this.id = id;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.size = size;
        this.uploadDateTime = uploadDateTime;
        this.active = active;
    }

    @Override
    public String toString() {
        return "FileUploadDTO{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", size=" + size +
                ", uploadDateTime=" + uploadDateTime +
                ", active=" + active +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}