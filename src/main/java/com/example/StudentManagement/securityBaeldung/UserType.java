package com.example.StudentManagement.securityBaeldung;

public enum UserType {

    USER("USER"),
    MODERATOR("MODERATOR"),
    ADMIN("ADMIN");

    private String name;

    UserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
