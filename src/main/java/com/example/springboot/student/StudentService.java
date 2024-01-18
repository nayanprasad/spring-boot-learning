package com.example.springboot.student;

import jakarta.websocket.OnClose;
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
        return studentRepository.findAll();
    }

    public Map<String, Object> addNewStudent(Student student) {
//        System.out.println(student.toString());
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if (studentOptional.isPresent())
            throw new IllegalStateException("email already take");

        studentRepository.save(student);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "student added");
        response.put("student", student);

        return response;

    }

    public Map<String, Object> deleteStudent(Long id) {
        boolean isPresent = studentRepository.existsById(id);
        if (!isPresent)
            throw new IllegalStateException("the student with id: " + id + " doesn't exist");
        studentRepository.deleteById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "student deleted");

        return response;
    }

    public Map<String, Object> updateStudent(Long id, Student student) {
        boolean isExists = studentRepository.existsById(id);
        if (!isExists)
            throw new IllegalStateException("the student with id: " + id + " doesn't exist");

        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent())
            throw new IllegalStateException("email already taken");

//        Student StudentToUpdate = studentRepository.findById(id).isPresent() ? studentRepository.findById(id).get() : null;
        Student StudentToUpdate = studentRepository.findById(id).get();
        StudentToUpdate.setName(student.getName());
        StudentToUpdate.setEmail(student.getEmail());

        studentRepository.save(StudentToUpdate);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "student updated");
        response.put("student", StudentToUpdate);

        return response;
    }
}
