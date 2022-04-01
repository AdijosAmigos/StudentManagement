package com.example.StudentManagement.controller;

import com.example.StudentManagement.model.Student;

import javax.persistence.ManyToMany;
import java.util.Set;

public class StudentCreateRequest {

    private final Long id;
    private final String firstName;
    private final String lastName;

    public StudentCreateRequest(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
