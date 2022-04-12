package com.example.StudentManagement.controller;

import com.example.StudentManagement.controller.CourseRequest.CourseCreateRequest;
import com.example.StudentManagement.controller.CourseRequest.CourseResponse;
import com.example.StudentManagement.controller.CourseRequest.CourseUpdateRequest;
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
    ResponseEntity<CourseResponse> addCourse(@RequestBody CourseCreateRequest course) {
        Course createdCourse = courseService.addCourse(course);
        return new ResponseEntity<>(toCourseResponse(createdCourse), HttpStatus.CREATED);

    }

    @GetMapping("/course/{id}")
    ResponseEntity<CourseResponse> findCourseById(@PathVariable String id) {
        if (Long.parseLong(id) < 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return courseService.findCourseById(Long.parseLong(id))
                .map(this::toCourseResponse)
                .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/deleteCourse/{id}")
    ResponseEntity<CourseResponse> deleteCourse(@PathVariable String id) {
        if (Long.parseLong(id) < 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        courseService.deleteCourse(Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/courses/{id}")
    ResponseEntity<CourseResponse> updateCourse(@RequestBody CourseUpdateRequest course, @PathVariable String id) {
        Course editedCourse = courseService.updateCourse(course, Long.parseLong(id));
        return new ResponseEntity<>(toCourseResponse(editedCourse), HttpStatus.NO_CONTENT);
    }

    private CourseResponse toCourseResponse(Course course) {
        Set<CourseResponse.StudentResponse> students = course.getStudents()
                .stream()
                .map(student -> new CourseResponse.StudentResponse(
                        student.getId(),
                        student.getFirstName(),
                        student.getLastName()))
                .collect(Collectors.toSet());
        return new CourseResponse(course.getId(), course.getName(), course.getStudents());
    }

    private CourseCreateRequest toCourseCreateRequest(Course course) {
        return new CourseCreateRequest(
                course.getId(),
                course.getName());
    }


}
