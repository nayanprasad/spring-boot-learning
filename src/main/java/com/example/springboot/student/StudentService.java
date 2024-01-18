package com.example.springboot.student;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    public List<Student> student() {
        return  List.of(new Student(
                1L,
                "hades",
                "hades@gmail.com"
        ));
    }
}
