package com.example.StudentManagement;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Course")
@Table()

public class Course {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    private Long id;
    @Column(
            name = "course_name",
            nullable = false,
            unique = true,
            columnDefinition = "TEXT"
    )

    private String name;


    public Course(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Course() {
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
                ", studentIds=" +
                '}';
    }
}
