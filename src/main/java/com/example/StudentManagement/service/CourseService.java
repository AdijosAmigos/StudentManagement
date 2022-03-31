package com.example.StudentManagement.service;

import com.example.StudentManagement.model.Course;
import com.example.StudentManagement.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> findCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Transactional
    public Course updateCourse(Course course){
        Course editCourse = courseRepository.findById(course.getId()).orElseThrow();
        editCourse.setName(course.getName());
        return editCourse;
    }


}
