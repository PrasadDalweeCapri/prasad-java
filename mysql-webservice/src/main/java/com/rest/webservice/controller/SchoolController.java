package com.rest.webservice.controller;

import com.rest.webservice.entity.Course;
import com.rest.webservice.entity.Laptop;
import com.rest.webservice.entity.Project;
import com.rest.webservice.entity.Student;
import com.rest.webservice.repository.CourseRepository;
import com.rest.webservice.repository.LaptopRepository;
import com.rest.webservice.repository.ProjectRepository;
import com.rest.webservice.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    //1:1 bidirectional mapping: student->laptop
    //1:many bidirectional mapping: student->projects
    //many:many bidirectional mapping: students->courses

    @PostMapping("/students")
    public ResponseEntity<String> createStudents(@RequestBody Student student) {
        student.addProject(student.getProjects());  //forcefully saving, one:many has some issues
        studentRepository.save(student);
        return ResponseEntity.ok("Student added.");
    }

    @GetMapping("/students/all")
    public List<Student> fetchAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/laptops/all")
    public List<Laptop> getAllLaptops() {
        return laptopRepository.findAll();
    }

    @GetMapping("/projects/all")
    public List<Project> fetchAllProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/courses/all")
    public List<Course> fetchAllCourses() {
        return courseRepository.findAll();
    }

}
