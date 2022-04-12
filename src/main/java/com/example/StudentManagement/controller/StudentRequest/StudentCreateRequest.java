package com.example.StudentManagement.controller.StudentRequest;

import com.example.StudentManagement.model.Course;
import com.example.StudentManagement.model.Student;

import javax.persistence.ManyToMany;
import java.util.Set;

public class StudentCreateRequest {

    private Long id;
    private String firstName;
    private String lastName;
    private Set<Course> courses;

    public StudentCreateRequest(Long id, String firstName, String lastName, Set<Course> courses ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = courses;
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

    public Set<Course> getCourses() {
        return courses;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
