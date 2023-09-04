package com.rest.webservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "laptop")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Laptop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer laptopId;

    @Column(nullable = false)
    private String brand;

//    One To One mapping: bidirectional (child->parent)
    @OneToOne(mappedBy = "laptop")  //variable name of child entity in parent class
    @JsonIgnoreProperties({"laptop"})
    private Student student;
}
