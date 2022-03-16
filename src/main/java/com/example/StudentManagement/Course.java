package com.example.StudentManagement;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Course")
@Table(
        name = "Course",
        uniqueConstraints = {
                @UniqueConstraint(name = "course_name_unique", columnNames = "course_name")
        }
)

public class Course {
    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "course_name",
            nullable = false,
            unique = true,
            columnDefinition = "TEXT"
    )
    private String name;
//    private List<Long> studentIds;

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

//    public List<Long> getStudentIds() {
//        return studentIds;
//    }

//    public void setStudentIds(List<Long> studentIds) {
//        this.studentIds = studentIds;
//    }

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
