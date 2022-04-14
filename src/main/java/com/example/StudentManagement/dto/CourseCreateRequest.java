package com.example.StudentManagement.dto;

public class CourseCreateRequest {
    private Long id;
    private String name;

    public CourseCreateRequest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
