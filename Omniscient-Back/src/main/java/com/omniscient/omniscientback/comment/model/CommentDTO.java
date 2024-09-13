package com.omniscient.omniscientback.comment.model;

public class CommentDTO {

    private Integer id;
    private Integer boardId;
    private String content;
    private String author;
    private String createdAt;
    private boolean active;

    // 기본 생성자
    public CommentDTO() {}

    // 모든 필드를 포함한 생성자
    public CommentDTO(Integer id, Integer boardId, String content, String author, String createdAt, boolean active) {
        this.id = id;
        this.boardId = boardId;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.active = active;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", boardId=" + boardId +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", active=" + active +
                '}';
    }
}