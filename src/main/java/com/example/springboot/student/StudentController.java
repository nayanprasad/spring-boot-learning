package com.example.springboot.student;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, Object>> getStudents() {
        return studentService.student();
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> addNewStudent(@RequestBody Student student) {
        return studentService.addNewStudent(student);
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable("studentId") Long id) {
        return studentService.deleteStudent(id);
    }

    @PutMapping("/update/{studentId}")
    public ResponseEntity<Map<String, Object>> updateStudent(@PathVariable("studentId") Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

}
