package com.omniscient.omniscientback.manager.faq.model;

public class FaqDTO {
    private Integer id;
    private String question;
    private String answer;
    private Boolean status; // 추가된 필드: FAQ의 상태를 나타냅니다.

    public FaqDTO() {
    }

    public FaqDTO(Integer id, String question, String answer, Boolean status) {
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
