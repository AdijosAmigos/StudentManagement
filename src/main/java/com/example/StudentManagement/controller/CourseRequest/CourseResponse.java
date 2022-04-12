package com.example.StudentManagement.controller.CourseRequest;

import com.example.StudentManagement.model.Student;

import javax.persistence.ManyToMany;
import java.util.Set;

public class CourseResponse {

    private final Long id;
    private final String name;
    private final Set<Student> students;

    public CourseResponse(Long id, String name, Set<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public static class StudentResponse{
        private final Long id;
        private final String firstName;
        private final String lastName;

        public StudentResponse(Long id, String firstName, String lastName) {
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
}
