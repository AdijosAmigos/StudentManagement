package com.example.StudentManagement.model;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Course")
@Table
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_id")
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;


    public Course(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Course(Long id, String name, Set<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }

    public Course() {
    }

    public Course(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) && Objects.equals(name, course.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
