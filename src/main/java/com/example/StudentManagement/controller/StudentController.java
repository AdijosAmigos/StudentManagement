package com.example.StudentManagement.controller;

import com.example.StudentManagement.model.Student;
import com.example.StudentManagement.repository.StudentRepository;
import com.example.StudentManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentRepository studentRepository, StudentService studentService) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }

    @GetMapping("/students")
    ResponseEntity<List<StudentResponse>> all() {
        List<Student> allUsers = studentService.getAllStudents();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/students")
    ResponseEntity<StudentCreateRequest> addUser(@RequestBody Student student) {
        studentService.addStudent(student);
        return new ResponseEntity<>(toStudentCreateRequest(student), HttpStatus.CREATED);
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

    @DeleteMapping("/deleteStudent/{id}")
    ResponseEntity<Student> deleteStudent(@PathVariable String id) {
        if (Long.parseLong(id) < 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        studentService.deleteStudent(Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/students/{id}")
    ResponseEntity<StudentResponse> updateStudent(@RequestBody StudentUpdateRequest student, @PathVariable String id){
        Student updatedStudent = studentService.updateStudent(student, Long.parseLong(id));
        return new ResponseEntity<>(toStudentResponse(updatedStudent), HttpStatus.OK);
    }

    @PostMapping("/courses/{studentId}/{courseId}")
    ResponseEntity<Student> assignStudentToCourse(@PathVariable String studentId, @PathVariable String courseId){
        if(Long.parseLong(studentId) < 0 || Long.parseLong(courseId) < 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        studentService.subscribeToCourse(Long.parseLong(studentId), Long.parseLong(courseId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private StudentResponse toStudentResponse(Student student){
        Set<StudentResponse.CourseResponse> courses = student.getCourses().stream()
                .map(course -> new StudentResponse.CourseResponse(course.getId(), course.getName()))
                .collect(Collectors.toSet());
        return new StudentResponse(student.getId(), student.getFirstName(), student.getLastName(), courses);
    }


    private StudentCreateRequest toStudentCreateRequest(Student student){
        return new StudentCreateRequest(student.getId(), student.getFirstName(), student.getLastName());
    }





}
