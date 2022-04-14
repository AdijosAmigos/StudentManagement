package com.example.StudentManagement.dto;

public class CourseUpdateRequest {
    private final String name;


    public CourseUpdateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
