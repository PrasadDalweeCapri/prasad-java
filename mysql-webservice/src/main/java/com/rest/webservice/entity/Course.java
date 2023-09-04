package com.rest.webservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="course")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Course {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
            @Column(name = "id", nullable = false)
    Integer courseId;

    @Column(nullable = false)
    String name;

    @ManyToMany(mappedBy = "courses",fetch = FetchType.LAZY)    //mapped-by name of object in entity having fk
    @JsonIgnoreProperties({"courses"})
    Set<Student> students;
}
