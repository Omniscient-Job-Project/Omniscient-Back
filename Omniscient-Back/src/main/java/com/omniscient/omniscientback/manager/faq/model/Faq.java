package com.omniscient.omniscientback.manager.faq.model;

import jakarta.persistence.*;

@Entity
@Table(name = "faq")
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_id", updatable = false, nullable = false)  // ID 필드는 NULL 값이 없어야 합니다.
    private Integer id;

    @Column(name = "faq_question", length = 1000, nullable = false)  // question 필드에 1000자 제한과 NULL 값 불허 설정
    private String question;

    @Column(name = "faq_answer", length = 1000, nullable = false)  // answer 필드에 1000자 제한과 NULL 값 불허 설정
    private String answer;

    @Column(name = "faq_status", nullable = false)  // status 필드에 NULL 값 불허 설정
    private Boolean status; // FAQ 상태를 나타냅니다.

    public Faq() {
    }

    public Faq(Integer id, String question, String answer, Boolean status) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.status = status;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getFaqStatus() {
        return status;
    }

    public void setFaqStatus(Boolean status) {
        this.status = status;
    }
}
