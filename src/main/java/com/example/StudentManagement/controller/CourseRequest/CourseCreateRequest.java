package com.example.StudentManagement.controller.CourseRequest;

import com.example.StudentManagement.model.Student;

import java.util.Set;

public class CourseCreateRequest {
    private Long id;
    private String name;

    public CourseCreateRequest(Long id, String name) {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
