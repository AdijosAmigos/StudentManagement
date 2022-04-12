package com.example.StudentManagement.controller;

import com.example.StudentManagement.controller.CourseRequest.CourseResponse;
import com.example.StudentManagement.model.Course;
import com.example.StudentManagement.repository.CourseRepository;
import com.example.StudentManagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class CourseController {

    private final CourseRepository courseRepository;
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseRepository courseRepository, CourseService courseService) {
        this.courseRepository = courseRepository;
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    ResponseEntity<List<CourseResponse>> all() {
        List<CourseResponse> allCourses = courseService.getAllCourses()
                .stream()
                .map(this::toCourseResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(allCourses, HttpStatus.OK);
    }

    @PostMapping("/courses")
    ResponseEntity<Course> addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/course/{id}")
    ResponseEntity<Course> findCourseById(@PathVariable String id) {
        if (Long.parseLong(id) < 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Course> course = courseService.findCourseById(Long.parseLong(id));
        return course.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/deleteCourse/{id}")
    ResponseEntity<Course> deleteCourse(@PathVariable String id) {
        if (Long.parseLong(id) < 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        courseService.deleteCourse(Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/courses/{id}")
    ResponseEntity<Course> updateCourse(@RequestBody Course course){
        courseService.updateCourse(course);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private CourseResponse toCourseResponse (Course course){
        Set<CourseResponse.StudentResponse> students = course.getStudents()
                .stream()
                .map(student -> new CourseResponse.StudentResponse(
                        student.getId(),
                        student.getFirstName(),
                        student.getLastName()))
                .collect(Collectors.toSet());
        return new CourseResponse(course.getId(), course.getName(), course.getStudents());
    }


}
