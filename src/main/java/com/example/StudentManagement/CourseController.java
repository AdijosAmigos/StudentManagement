package com.example.StudentManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses")
    ResponseEntity<List<Course>> all() {
        List<Course> allCourses = courseRepository.findAll();
        return new ResponseEntity<>(allCourses, HttpStatus.OK);
    }

    @PostMapping("/addcourse")
    ResponseEntity<Course> addCourse(@RequestBody Course course) {
        courseRepository.save(course);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/course{id}")
    ResponseEntity<Course> findCourseById(@PathVariable String id) {
        Optional<Course> course = courseRepository.findById(Long.parseLong(id));
        return course.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/deletecourse{id}")
    ResponseEntity<Course> deleteCourse(@PathVariable String id) {
        courseRepository.deleteById(Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
