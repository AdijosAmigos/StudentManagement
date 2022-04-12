package com.example.StudentManagement;

import com.example.StudentManagement.controller.StudentRequest.StudentResponse;
import com.example.StudentManagement.controller.StudentRequest.StudentUpdateRequest;
import com.example.StudentManagement.model.Student;
import com.example.StudentManagement.repository.StudentRepository;
import com.example.StudentManagement.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentControllerTestIT extends AbctractIntegrationTest{

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    ApplicationContext context;

    @Test
    void should_return_all_students() {
        Student student = new Student(1L, "Adrian", "Kowalski");
        studentRepository.save(student);

        var result = restTemplate
                .withBasicAuth("user", "user")
                .getForEntity("http://localhost:" + port + "/students", Student[].class);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertEquals(MediaType.APPLICATION_JSON, result.getHeaders().getContentType());
    }

    @Test
    void should_add_student() {
        Student student = new Student(1L, "Adrian", "Kowalski");

        var studentResponseEntity = restTemplate
                .withBasicAuth("admin", "admin")
                .postForEntity("http://localhost:" + port + "/students", student, Student.class);

        assertThat(studentResponseEntity.getStatusCode().is2xxSuccessful()).isTrue();

    }

    @Test
    void should_find_student_by_id() {
        Student student1 = new Student(1L, "Adrian", "Kowalski");
        Student student2 = new Student(2L, "Maciek", "Nowak");
        studentRepository.save(student1);
        studentRepository.save(student2);

        var result = restTemplate
                .withBasicAuth("mode", "mode")
                .getForEntity("http://localhost:" + port + "/student/1", Student.class);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertEquals(MediaType.APPLICATION_JSON, result.getHeaders().getContentType());

    }


    @Test
    void should_delete_student_by_id() {
        Student student = new Student(1L, "Adrian", "Kowalski");

        studentRepository.save(student);

        var result = restTemplate
                .withBasicAuth("admin", "admin")
                .exchange("http://localhost:" + port + "/deleteStudent/1", HttpMethod.DELETE, HttpEntity.EMPTY, Void.TYPE);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(studentRepository.findAll()).isEmpty();


    }

    @Test
    void should_not_be_able_find_student_when_id_less_than_zero() {
        Student student1 = new Student(1L, "Adrian", "Kowalski");

        studentRepository.save(student1);

        var result = restTemplate
                .withBasicAuth("user", "user")
                .getForEntity("http://localhost:" + port + "/student/-1", Student.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(404);

    }


    @Test
    void should_not_be_able_delete_student_when_id_less_than_zero() {
        Student student1 = new Student(1L, "Adrian", "Kowalski");

        studentRepository.save(student1);

        var result = restTemplate
                .withBasicAuth("admin", "admin")
                .exchange("http://localhost:" + port + "/deleteStudent/-1", HttpMethod.DELETE, HttpEntity.EMPTY, Void.TYPE);

        assertThat(result.getStatusCodeValue()).isEqualTo(404);

    }

    @Test
    void should_not_be_able_to_delete_student_when_no_authorization () {
        Student student1 = new Student(1L, "Adrian", "Kowalski");

        studentRepository.save(student1);

        var result = restTemplate
                .withBasicAuth("user", "user")
                .exchange("http://localhost:" + port + "/deleteStudent/1",
                        HttpMethod.DELETE, HttpEntity.EMPTY, Void.TYPE);

        assertThat(result.getStatusCodeValue()).isEqualTo(403);

    }

    @Test
    void should_be_able_to_update_student() {
        Student student = new Student(1L, "Maciek", "Malinowski");
        studentRepository.save(student);

        StudentUpdateRequest studentUpdateRequest = new StudentUpdateRequest("Adrian", "Kowalski");
        HttpEntity<StudentUpdateRequest> httpEntity = new HttpEntity<>(studentUpdateRequest);

        var result = restTemplate
                .withBasicAuth("mode", "mode")
                .exchange("http://localhost:" + port + "/students/1", HttpMethod.PUT, httpEntity, StudentResponse.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody().getFirstName()).isEqualTo("Adrian");
        assertThat(result.getBody().getLastName()).isEqualTo("Kowalski");

    }



}