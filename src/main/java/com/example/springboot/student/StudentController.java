package com.example.springboot.student;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/students")
public class StudentController {

    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/all")
    public List<Student> getStudents() {
        return studentService.student();
    }

    @PostMapping("/new")
    public Map<String, Object> addNewStudent(@RequestBody Student student) {
        return studentService.addNewStudent(student);
    }
}
