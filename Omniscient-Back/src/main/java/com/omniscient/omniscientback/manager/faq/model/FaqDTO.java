package com.omniscient.omniscientback.manager.faq.model;

public class FaqDTO {
    private Integer id;
    private String question;
    private String answer;
    private Boolean status; // 추가된 필드: FAQ의 상태를 나타냅니다.
    private Integer faqViews = 0;  // 조회수 필드 추가

    public FaqDTO() {
    }

    public FaqDTO(Integer id, String question, String answer, Boolean status, Integer faqViews) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.status = status;
        this.faqViews = faqViews;
    }

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

    public Integer getFaqViews() {
        return faqViews;
    }

    public void setFaqViews(Integer faqViews) {
        this.faqViews = faqViews;
    }

    @Override
    public String toString() {
        return "FaqDTO{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", status=" + status +
                ", faqViews=" + faqViews +
                '}';
    }
}