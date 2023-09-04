package com.rest.webservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "project")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer courseId;

    @Column(nullable = false)
    private String name;

    private String details; //nullable by default

//    @OneToOne (cascade = CascadeType.ALL)
//    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"projects"})
    private Student student;
}
