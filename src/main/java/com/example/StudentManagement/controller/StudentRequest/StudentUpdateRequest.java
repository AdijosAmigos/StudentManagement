package com.example.StudentManagement.controller.StudentRequest;


public class StudentUpdateRequest {

    private final String firstName;
    private final String lastName;

    public StudentUpdateRequest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
