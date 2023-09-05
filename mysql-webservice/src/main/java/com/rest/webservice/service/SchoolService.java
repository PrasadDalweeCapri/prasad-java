package com.rest.webservice.service;

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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchoolService {
    private final StudentRepository studentRepository;
    private final LaptopRepository laptopRepository;
    private final ProjectRepository projectRepository;
    private final CourseRepository courseRepository;

    public Student createStudents(@RequestBody Student student) {
        student.addProject(student.getProjects());  //forcefully saving project entity as one:many has some issues
        Student newStudent;
        try {
            newStudent = studentRepository.save(student);
            log.info("New Student added.");
        } catch (Throwable t) {
            log.error("Error occurred while saving new student. Student:{}, Error:{}", student, t.getMessage());
            throw new RuntimeException("Error occurred.");
        }
        return newStudent;
    }

    public List<Student> fetchAllStudents() {
        List<Student> students;
        try {
            students = studentRepository.findAll();
        } catch (Throwable t) {
            log.error("Error occurred while retrieving all students. Error:{}", t.getMessage());
            throw new RuntimeException("Error occurred.");
        }
        return students;
    }

    public List<Laptop> fetchAllLaptops() {
        List<Laptop> laptops;
        try {
            laptops = laptopRepository.findAll();
        } catch (Throwable t) {
            log.error("Error occurred while retrieving all laptops. Error:{}", t.getMessage());
            throw new RuntimeException("Error occurred.");
        }
        return laptops;
    }

    public List<Project> fetchAllProjects() {
        List<Project> projects;
        try {
            projects = projectRepository.findAll();
        } catch (Throwable t) {
            log.error("Error occurred while retrieving all projects. Error:{}", t.getMessage());
            throw new RuntimeException("Error occurred.");
        }
        return projects;
    }

    public List<Course> fetchAllCourses() {
        List<Course> courses;
        try {
            courses = courseRepository.findAll();
        } catch (Throwable t) {
            log.error("Error occurred while retrieving all projects. Error:{}", t.getMessage());
            throw new RuntimeException("Error occurred.");
        }
        return courses;
    }
}
