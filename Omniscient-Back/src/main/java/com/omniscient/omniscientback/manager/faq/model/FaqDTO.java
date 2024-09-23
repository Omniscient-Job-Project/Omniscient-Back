package com.omniscient.omniscientback.manager.faq.model;

public class FaqDTO {
    private Integer faqId;
    private String question;
    private String answer;
    private Boolean status;
    private Integer faqViews = 0;

    public FaqDTO() {
    }

    public FaqDTO(Integer faqId, String question, String answer, Boolean status, Integer faqViews) {
        this.faqId = faqId;
        this.question = question;
        this.answer = answer;
        this.status = status;
        this.faqViews = faqViews;
    }

    public Integer getFaqId() {
        return faqId;
    }

    public void setFaqId(Integer faqId) {
        this.faqId = faqId;
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
}
