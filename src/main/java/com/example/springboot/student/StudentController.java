package com.example.springboot.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/students")
public class StudentController {
    @GetMapping("/student")
    public List<Student> student() {
        return  List.of(new Student(
                1L,
                "hades",
                "hades@gmail.com"
        ));
    }
}
