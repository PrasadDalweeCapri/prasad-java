package com.rest.webservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Integer studentId;

    @Column(nullable = false)
    private String name;

    private String bio;     //nullable==true is default

    //one to one mapping: unidirectional & bidirectional (parent ->child/associate)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) //default FetchType: EAGER
    @JoinColumn(name = "laptop_id", referencedColumnName = "id") //id column of laptop entity
    @JsonIgnoreProperties({"student"})  //helps avoiding infinite loop in bi-directional mapping
    private Laptop laptop;

    //one to many (student->courses) unidirectional
//    @OneToMany(targetEntity = Projects.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "student_id_fk", referencedColumnName = "id")
//    @JsonIgnoreProperties({"student"})
//    private List<Projects> projects;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "student",
            orphanRemoval = true                //if parent is removed, associate will be automatically removed
    )
    @JsonIgnoreProperties({"student"})
    private List<Project> projects;


    //Many to Many: Bidirectional
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_courses",
            joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id")}
    )
    @JsonIgnoreProperties({"students"})
    private Set<Course> courses;


    public void addProject(List<Project> projects) //adding forcefully
    {
        this.projects=projects;
        projects.forEach(project1 -> project1.setStudent(this));
    }


}

/*
    + cascade: defines behavior of DB operations on associate entities wrt parent (create, update, delete, refresh, etc.)

    + fetch: defines how to fetch associate entities wrt parent
            1. eager: fetch associate entity when parent is accessed (inefficient for large data entities)
            2. lazy: fetch associate entity when parent is accessed for the first time  (inefficient for frequently used entities)

     + without join column additional tables will be created to manage relationships

     + eager-loading loads the associate entity on the spot while the fuzzy loading avoids loading it as long as possible (uses proxy
     objects)

     + lazy-loading adds two fields on the top of associate object, which can give an error if the object is serialized
     (eg. while returning the object through API it will be serialized to json), adding the JsonIgnoreProperties over the
     associate entity will solve the issue.

     + for bidirectional mappings remember whoever owns the foreign key gets @JoinColumn it is not necessary that the parent has
     the f-key in every situation

     +mappedBy attribute specifies the name of object defined in the owner entity for establishing relationship
     */
