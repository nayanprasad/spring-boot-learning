package com.example.springboot.student;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;  // final means that the variable cannot be changed

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ResponseEntity<Map<String, Object>> student() {
        List<Student> students = studentRepository.findAll();

        return ResponseEntity.status(200).body(Map.of(
                "success", true,
                "message", "students fetched",
                "students", students
        ));
    }

    public ResponseEntity<Map<String, Object>> addNewStudent(Student student) {
//        System.out.println(student.toString());
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if (studentOptional.isPresent())
            throw new IllegalStateException("email already take");

        studentRepository.save(student);

       return  ResponseEntity.status(200).body(Map.of(
                "success", true,
                "message", "student added",
                "student", student
        ));

    }

    public ResponseEntity<Map<String, Object>> deleteStudent(Long id) {
        boolean isPresent = studentRepository.existsById(id);
        if (!isPresent)
            throw new IllegalStateException("the student with id: " + id + " doesn't exist");
        studentRepository.deleteById(id);

        return ResponseEntity.status(200).body(Map.of(
                "successs", true,
                "message", "student deleted with id: " + id
        ));
    }

    public ResponseEntity<Map<String, Object>> updateStudent(Long id, Student student) {
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

        return ResponseEntity.status(200).body(Map.of(
                "success", true,
                "message", "student updated",
                "student", StudentToUpdate
        ));
    }
}
