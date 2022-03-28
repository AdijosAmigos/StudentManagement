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
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentRepository studentRepository, StudentService studentService) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
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
        studentService.addStudent(student);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/student/{id}")
    ResponseEntity<Student> findById(@PathVariable String id) {
        if (Long.parseLong(id) < 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Student> student = studentService.findStudentById(Long.parseLong(id));
        return student.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/deletestudent/{id}")
    ResponseEntity<Student> deleteStudent(@PathVariable String id) {
        if (Long.parseLong(id) < 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        studentService.deleteStudent(Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/updateStudent")
    ResponseEntity<Student> updateStudent(@RequestBody Student student){
        studentService.updateStudent(student);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/assignToCourse/{studentId}/{courseId}")
    ResponseEntity<Student> assignStudentToCourse(@PathVariable String studentId, @PathVariable String courseId){
        if(Long.parseLong(studentId) < 0 || Long.parseLong(courseId) < 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        studentService.subscribeToCourse(Long.parseLong(studentId), Long.parseLong(courseId));
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
