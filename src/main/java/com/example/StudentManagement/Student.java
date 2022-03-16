package com.example.StudentManagement;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "Student")
@Table(
        name = "Student",
        uniqueConstraints = {
                @UniqueConstraint(name = "student_pid_unique", columnNames = "PID")
        }

)
public class Student {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String firstName;
    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String lastName;
    @Column(
            name = "PID",
            nullable = false,
            columnDefinition = "TEXT",
            unique = true
    )
    private String PID;
    @Column(
            name = "login",
            nullable = false,
            unique = true,
            updatable = false,
            columnDefinition = "TEXT"
    )
    private String login;
    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password;
//    private List<Course> courses;

    public Student() {

    }

    public Student(
            String firstName,
            String lastName,
            String PID,
            String login,
            String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.PID = PID;
        this.login = login;
        this.password = password;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public String getPID() {
        return PID;
    }


    public String getLogin() {
        return login;
    }


    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public List<Course> getCourses() {
//        return courses;
//    }

//    public void setCourses(List<Course> courses) {
//        this.courses = courses;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName) && Objects.equals(PID, student.PID) && Objects.equals(login, student.login) && Objects.equals(password, student.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, PID, login, password);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", PID='" + PID + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
