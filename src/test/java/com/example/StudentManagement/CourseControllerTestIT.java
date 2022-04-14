package com.example.StudentManagement;
import com.example.StudentManagement.dto.CourseResponse;
import com.example.StudentManagement.dto.CourseUpdateRequest;
import com.example.StudentManagement.dto.StudentUpdateRequest;
import com.example.StudentManagement.model.Course;
import com.example.StudentManagement.model.Student;
import com.example.StudentManagement.repository.CourseRepository;
import com.example.StudentManagement.repository.StudentRepository;
import com.example.StudentManagement.service.CourseService;
import com.example.StudentManagement.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import javax.print.attribute.standard.Media;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("course test")

class CourseControllerTestIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    ApplicationContext context;


    @Test
    void should_return_all_courses(){
        Course course = new Course(1L,"math");
        courseRepository.save(course);

        var result = restTemplate
                .withBasicAuth("admin", "admin")
                .getForEntity("http://localhost:" +port+ "/courses", Course[].class);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(result.getBody()).containsExactly(course);
    }

    @Test
    void should_add_course(){
        Course course = new Course(1L,"math");

        var result = restTemplate
                .withBasicAuth("mode", "mode")
                .postForEntity("http://localhost:" + port + "/courses", course, Course.class);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();

    }

    @Test
    void should_find_course_by_id(){
        Course course1 = new Course(1L,"math");
        Course course2 = new Course(2L,"chemistry");
        courseRepository.save(course1);
        courseRepository.save(course2);

        var result = restTemplate
                .withBasicAuth("user", "user")
                .getForEntity("http://localhost:" +port+ "/course/1", Course.class);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);

    }

    @Test
    void should_delete_course_by_id(){
        Course course = new Course(1L,"math");

        courseRepository.save(course);

        var result = restTemplate
                .withBasicAuth("admin", "admin")
                .exchange("http://localhost:" +port+ "/deleteCourse/1", HttpMethod.DELETE, HttpEntity.EMPTY, Void.TYPE);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(courseRepository.findAll()).isEmpty();

    }

    @Test
    void should_not_be_able_to_find_course_by_id(){
        Course course = new Course(1L,"math");

        courseRepository.save(course);

        var result = restTemplate
                .withBasicAuth("user", "user")
                .getForEntity("http://localhost:" +port+ "/course/-1", Course.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(404);

    }

    @Test
    void should_not_be_able_delete_course_by_id(){
        Course course = new Course(1L,"math");

        courseRepository.save(course);

        var result = restTemplate
                .withBasicAuth("admin", "admin")
                .exchange("http://localhost:" +port+ "/deletecourse/-1", HttpMethod.DELETE, HttpEntity.EMPTY, Void.TYPE);

        assertThat(result.getStatusCodeValue()).isEqualTo(404);

    }

    @Test
    void should_not_be_able_to_delete_course_when_no_authorization () {
        Course course = new Course(1L,"math");

        courseRepository.save(course);

        var result = restTemplate
                .withBasicAuth("mode", "mode")
                .exchange("http://localhost:" +port+ "/deleteCourse/1", HttpMethod.DELETE, HttpEntity.EMPTY, Void.TYPE);

        assertThat(result.getStatusCodeValue()).isEqualTo(403);
    }


    // do dokonczenia dostaje 400 czemu?
    @Test
    void should_be_able_to_update_course(){
        Course course = new Course(1L, "math");
        courseRepository.save(course);

        CourseUpdateRequest courseUpdateRequest = new CourseUpdateRequest("chemistry");
        HttpEntity<CourseUpdateRequest> httpEntity = new HttpEntity<>(courseUpdateRequest);

        var result = restTemplate
                .withBasicAuth("mode", "mode")
                .exchange("http://localhost:" +port+ "/courses/1", HttpMethod.PUT, httpEntity, CourseResponse.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody().getName()).isEqualTo("chemistry");

    }

    @Test
    void should_get_all_student_from_course(){
        Student student = new Student(1L, "Adrian", "Kowalski");
        Course course = new Course(1L, "math");

        studentRepository.save(student);
        courseRepository.save(course);

        studentService.subscribeToCourse(student.getId(), course.getId());

        var result = restTemplate
                .withBasicAuth("admin", "admin")
                .getForEntity("http://localhost:" +port+ "/course/1", Course.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(courseService.getAllStudents(course).isEmpty()).isFalse();





    }

}