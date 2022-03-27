package com.example.StudentManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseService courseService;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
    }

    public void addStudent(Student student) {
        studentRepository.save(student);
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
        var course = courseService.findCourseById(courseId);
        var student = findStudentById(studentId);
        student.get().addCourse(course.get());
        studentRepository.save(student.get());
    }

    public Student updateStudent(Student student){
        return studentRepository.save(student);
    }

}
