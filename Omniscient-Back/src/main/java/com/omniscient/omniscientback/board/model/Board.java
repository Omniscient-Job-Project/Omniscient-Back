package com.omniscient.omniscientback.board.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "boards")
@Getter @Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 게시글의 고유 식별자

    @Column(nullable = false, length = 30) // 게시글 제목 최대 30글자
    private String title;

    @Column(nullable = false, length = 2000) // 게시글 내용 최대 2000글자
    private String content;

    /**
     * @Enumerated(EnumType.STRING) 어노테이션은 Java enum 타입을 데이터베이스 컬럼에 매핑할 때 사용됩니다.
     * EnumType.STRING을 사용하면 enum 값의 이름(문자열)이 데이터베이스에 저장됩니다.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category; // 게시글 카테고리 (채용 또는 자격증)

    @Column(nullable = false)
    private Boolean status = true; // true: 활성, false: 비활성(삭제된 상태)

    @Column(nullable = false)
    private String createdAt;

    @Column
    private String updatedAt;

    /**
     * @PrePersist 엔티티가 데이터베이스에 저장되기 전에 실행되는 메소드
     * 생성 시간이 설정되지 않은 경우 현재 시간을 설정합니다.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now().toString();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now().toString();
    }

    public enum Category {
        RECRUITMENT, // 채용 게시글
        CERTIFICATION // 자격증 게시글
    }
}