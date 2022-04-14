package com.example.StudentManagement.service;

import com.example.StudentManagement.dto.CourseCreateRequest;
import com.example.StudentManagement.dto.CourseUpdateRequest;
import com.example.StudentManagement.model.Course;
import com.example.StudentManagement.model.Student;
import com.example.StudentManagement.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course addCourse(CourseCreateRequest course) {
        Course createdCourse = new Course(course.getName());
        return courseRepository.save(createdCourse);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Set<Student> getAllStudents(Course course) {
        return course.getStudents();
    }

    public Optional<Course> findCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course getById(Long id) {
        return courseRepository.getById(id);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Transactional
    public Course updateCourse(CourseUpdateRequest course, Long id) {
        Course editCourse = courseRepository.findById(id).orElseThrow();
        editCourse.setName(course.getName());
        courseRepository.save(editCourse);
        return editCourse;
    }


}
