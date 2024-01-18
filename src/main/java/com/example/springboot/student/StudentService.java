package com.example.springboot.student;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;  // final means that the variable cannot be changed

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> student() {
        return  studentRepository.findAll();
    }

    public Map<String, Object> addNewStudent(Student student) {
//        System.out.println(student.toString());
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if(studentOptional.isPresent())
            throw new IllegalStateException("email already take");

        studentRepository.save(student);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Student added");
        response.put("student", student);

        return response;

    }
}
