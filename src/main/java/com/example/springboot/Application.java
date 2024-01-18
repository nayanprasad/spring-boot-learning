package com.example.springboot;

import com.example.springboot.student.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/json")
    public Map<String, Object> json() {
        Map<String, Object> response = new HashMap<>();

        response.put("name", "John");
        response.put("age", 30);

        return response;
    }




}
