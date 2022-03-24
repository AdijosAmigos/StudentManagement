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

    @GetMapping("/hello1")
    public String sayHello(){
        return "Hello";
    }

    @GetMapping("/hello2")
    public String sayHello2(){
        return "Hello2";
    }

    @GetMapping("/hello3")
    public String sayHello3(){
        return "Hello3";
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

    @GetMapping("/student/{id}")
    ResponseEntity<Student> findById(@PathVariable String id) {
        if (Long.parseLong(id) < 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Student> student = studentRepository.findById(Long.parseLong(id));
        return student.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/deletestudent/{id}")
    ResponseEntity<Student> deleteStudent(@PathVariable String id) {
        if (Long.parseLong(id) < 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        studentRepository.deleteById(Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
