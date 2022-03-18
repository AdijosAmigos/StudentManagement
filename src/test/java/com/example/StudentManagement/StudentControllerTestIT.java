package com.example.StudentManagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class StudentControllerTestIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    ApplicationContext context;

    @Test
    void should_return_all_students(){
        Student student = new Student(1L,"Adrian", "Kowalski");
        studentRepository.save(student);

        var result = restTemplate.getForEntity("http://localhost:" +port+ "/students", Student[].class);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.getBody()).containsExactly(student);
    }

    @Test
    void should_add_student(){
        Student student = new Student(1L, "Adrian", "Kowalski");

        var result = restTemplate.postForEntity("http://localhost:" +port+ "/addstudent", student, Student.class);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void should_find_student_by_id(){
        Student student1 = new Student(1L, "Adrian", "Kowalski");
        Student student2 = new Student(2L, "Maciek", "Nowak");
        studentRepository.save(student1);
        studentRepository.save(student2);

        var result = restTemplate.getForEntity("http://localhost:" +port+ "/student/1", Student.class);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();

    }

    @Test
    void should_delete_student_by_id(){
        Student student1 = new Student(1L, "Adrian", "Kowalski");
        Student student2 = new Student(2L, "Maciek", "Nowak");

        studentRepository.save(student1);
        studentRepository.save(student2);

        var result = restTemplate.postForEntity("http://localhost:" +port+ "/deletestudent/1", student1, Student.class);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();

    }

    @Test
    void should_not_be_able_find_student_when_id_less_than_zero(){
        Student student1 = new Student(1L, "Adrian", "Kowalski");

        studentRepository.save(student1);

        var result = restTemplate.getForEntity("http://localhost:" +port+ "/student/-1", Student.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    void should_not_be_able_delete_student_when_id_less_than_zero(){
        Student student1 = new Student(1L, "Adrian", "Kowalski");

        studentRepository.save(student1);

        var result = restTemplate.postForEntity("http://localhost:" +port+ "/deletestudent/-1", student1, Student.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(404);
    }

}