package com.example.StudentManagement.service;

import com.example.StudentManagement.controller.StudentRequest.StudentCreateRequest;
import com.example.StudentManagement.controller.StudentRequest.StudentUpdateRequest;
import com.example.StudentManagement.model.Student;
import com.example.StudentManagement.repository.CourseRepository;
import com.example.StudentManagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public Student addStudent(StudentCreateRequest student) {
        Student createdStudent = new Student(student.getFirstName(), student.getLastName());
        return studentRepository.save(createdStudent);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> findStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public void subscribeToCourse(Long studentId, Long courseId) {
        var course = courseRepository.getById(courseId);
        var student = studentRepository.getById(studentId);
        student.addCourse(course);
        studentRepository.save(student);
    }

    @Transactional
    public Student updateStudent(StudentUpdateRequest student, Long id){
        Student editStudent = studentRepository.findById(id).orElseThrow();
        editStudent.setFirstName(student.getFirstName());
        editStudent.setLastName(student.getLastName());
        studentRepository.save(editStudent);
        return editStudent;
    }

}
