package com.example.StudentManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students")
    ResponseEntity<List<Student>> all() {
        List<Student> allUsers = studentRepository.findAll();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @PostMapping("/addstudent")
    ResponseEntity<Student> addUser(@RequestBody Student student) {
        studentRepository.save(student);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/student{id}")
    ResponseEntity<Student> findById(@PathVariable String id){
        Optional<Student> student = studentRepository.findById(Long.getLong(id));
        return student.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/deletestudent{id}")
    ResponseEntity<Course> deleteCourse(@PathVariable String id) {
        studentRepository.deleteById(Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
