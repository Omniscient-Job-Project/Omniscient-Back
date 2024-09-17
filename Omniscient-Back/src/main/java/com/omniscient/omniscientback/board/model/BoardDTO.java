package com.omniscient.omniscientback.board.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BoardDTO {
    private Integer id;

    @NotBlank(message = "제목은 필수 입력 항목입니다.")
    @Size(max = 30, message = "제목은 30자를 초과할 수 없습니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 항목입니다.")
    @Size(max = 2000, message = "내용은 2000자를 초과할 수 없습니다.")
    private String content;

    @NotNull(message = "카테고리는 필수 선택 항목입니다.")
    private Board.Category category;

    private Boolean status;
    private String createdAt;
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "제목은 필수 입력 항목입니다.") @Size(max = 30, message = "제목은 30자를 초과할 수 없습니다.") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "제목은 필수 입력 항목입니다.") @Size(max = 30, message = "제목은 30자를 초과할 수 없습니다.") String title) {
        this.title = title;
    }

    public @NotBlank(message = "내용은 필수 입력 항목입니다.") @Size(max = 2000, message = "내용은 2000자를 초과할 수 없습니다.") String getContent() {
        return content;
    }

    public void setContent(@NotBlank(message = "내용은 필수 입력 항목입니다.") @Size(max = 2000, message = "내용은 2000자를 초과할 수 없습니다.") String content) {
        this.content = content;
    }

    public @NotNull(message = "카테고리는 필수 선택 항목입니다.") Board.Category getCategory() {
        return category;
    }

    public void setCategory(@NotNull(message = "카테고리는 필수 선택 항목입니다.") Board.Category category) {
        this.category = category;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "BoardDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category=" + category +
                ", status=" + status +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}