package com.rest.webservice.controller;

import com.rest.webservice.entity.Course;
import com.rest.webservice.entity.Laptop;
import com.rest.webservice.entity.Project;
import com.rest.webservice.entity.Student;
import com.rest.webservice.repository.CourseRepository;
import com.rest.webservice.repository.LaptopRepository;
import com.rest.webservice.repository.ProjectRepository;
import com.rest.webservice.repository.StudentRepository;
import com.rest.webservice.service.SchoolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/school")
@Slf4j
public class SchoolController {
    private final StudentRepository studentRepository;
    private final LaptopRepository laptopRepository;
    private final ProjectRepository projectRepository;
    private final CourseRepository courseRepository;
    private final SchoolService service;
    //1:1 bidirectional mapping: student->laptop
    //1:many bidirectional mapping: student->projects
    //many:many bidirectional mapping: students->courses

    @PostMapping("/students")
    public Student createStudents(@RequestBody Student student) {
        return service.createStudents(student);
    }

    @GetMapping("/students/all")
    public List<Student> fetchAllStudents() {
        return service.fetchAllStudents();
    }

    @GetMapping("/laptops/all")
    public List<Laptop> fetchAllLaptops() {
        return service.fetchAllLaptops();
    }

    @GetMapping("/projects/all")
    public List<Project> fetchAllProjects() {
        return service.fetchAllProjects();
    }

    @GetMapping("/courses/all")
    public List<Course> fetchAllCourses() {
        return service.fetchAllCourses();
    }

}
