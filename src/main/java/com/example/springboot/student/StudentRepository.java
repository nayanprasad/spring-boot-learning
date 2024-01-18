package com.example.springboot.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {  // <Student, Long> means that we are using the Student class and the primary key is Long(which is id)

    Optional<Student> findStudentByEmail(String email); // SELECT * FROM student WHERE email = ?  (optional because it may or may not exist)
}
