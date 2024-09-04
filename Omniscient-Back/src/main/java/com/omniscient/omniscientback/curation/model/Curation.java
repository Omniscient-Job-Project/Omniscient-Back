package com.omniscient.omniscientback.curation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "curation")
public class Curation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curation_id", nullable = false)
    private Integer curationId;

}
