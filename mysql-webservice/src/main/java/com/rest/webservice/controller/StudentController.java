package com.rest.webservice.controller;

import com.rest.webservice.dto.SearchStudentNameDto;
import com.rest.webservice.entity.Student;
import com.rest.webservice.service.StudentService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
@Validated
public class StudentController {
    private final StudentService studentService;

    /*
        Different end-points to test different types of queries
        Name: string searching types
        Bio: null and not searching
        Id: filtering by comparison, range, collection
        Active: boolean searching
        Dob: filtering by date
     */
    @PostMapping("/name")
    public List<Student> searchByName(@RequestBody SearchStudentNameDto studentNameDto) {
        return studentService.searchName(studentNameDto);
    }

    @GetMapping("/bio")
    public List<Student> searchStudentsByBio(@RequestParam @NotNull String type, @RequestParam @NotBlank String bio) {
        return studentService.searchBio(type, bio);
    }

    @GetMapping("/active")
    public List<Student> searchByActive(@RequestParam @NotBlank String type) {
        return studentService.searchActive(type);
    }

    @GetMapping("/dob")
    public List<Student> filterByDate(@RequestParam @NotNull LocalDate dob, @RequestParam @NotBlank String type) {
        return studentService.filterDate(dob, type);
    }

    @GetMapping("/student-id")
    public List<Student> filterById(@RequestParam @NotNull Integer id, @RequestParam @NotBlank String type) {
        return studentService.filterId(id, type);
    }

    @PostMapping("/student-id/collection")
    public List<Student> filterByIdCollection(@RequestBody List<Integer> idCollection) {
        return studentService.fiterIdCollection(idCollection);
    }

    @GetMapping("/student-id/range")
    public List<Student> filterByIdRange(@RequestParam @NotNull Integer start, @RequestParam @NotNull Integer end) {
        return studentService.filterIdRange(start, end);
    }
}
