package com.omniscient.omniscientback.manager.admin.visitor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer visitorId;

    private LocalDate visitDate;
    private Integer visitCount;

    public Visitor() {
    }

    public Visitor(Integer visitorId, LocalDate visitDate, Integer visitCount) {
        this.visitorId = visitorId;
        this.visitDate = visitDate;
        this.visitCount = visitCount;
    }

    public Integer getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Integer visitorId) {
        this.visitorId = visitorId;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visitor)) return false;
        Visitor visitor = (Visitor) o;
        return Objects.equals(visitorId, visitor.visitorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitorId);
    }
}
