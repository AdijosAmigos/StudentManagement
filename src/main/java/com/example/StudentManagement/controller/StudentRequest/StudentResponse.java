package com.example.StudentManagement.controller.StudentRequest;

import com.example.StudentManagement.model.Course;
import com.example.StudentManagement.model.Student;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

public class StudentResponse {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final Set<CourseResponse> courseRe;


    public StudentResponse(Long id, String firstName, String lastName, Set<CourseResponse> courseRe) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courseRe = courseRe;
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

    public Set<CourseResponse> getCourseRe() {
        return courseRe;
    }

    public static class CourseResponse {
        private final Long id;
        private final String name;

        public CourseResponse(Long id, String name) {
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
}
