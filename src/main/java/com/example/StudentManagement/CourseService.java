package com.example.StudentManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void updateCourse(Course course){
        Course editCourse = courseRepository.findById(course.getId()).orElseThrow();
        editCourse.setName(course.getName());
    }


}
