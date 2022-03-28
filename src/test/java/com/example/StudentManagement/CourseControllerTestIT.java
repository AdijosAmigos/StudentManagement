package com.example.StudentManagement;

import com.example.StudentManagement.model.Course;
import com.example.StudentManagement.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

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
    ApplicationContext context;


    @Test
    void should_return_all_courses(){
        Course course = new Course(1L,"math");
        courseRepository.save(course);

        var result = restTemplate.getForEntity("http://localhost:" +port+ "/courses", Course[].class);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.getBody()).containsExactly(course);
    }

    @Test
    void should_add_course(){
        Course course = new Course(1L,"math");

        var result = restTemplate.postForEntity("http://localhost:" +port+ "/addcourse", course, Course.class);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void should_find_course_by_id(){
        Course course1 = new Course(1L,"math");
        Course course2 = new Course(2L,"chemistry");
        courseRepository.save(course1);
        courseRepository.save(course2);

        var result = restTemplate.getForEntity("http://localhost:" +port+ "/course/1", Course.class);

        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();

    }

    @Test
    void should_delete_course_by_id(){
        Course course1 = new Course(1L,"math");
        Course course2 = new Course(2L,"chemistry");
        courseRepository.save(course1);
        courseRepository.save(course2);

        var result = restTemplate.postForEntity("http://localhost:" +port+ "/deletecourse/1", course1, Course.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);

    }

    @Test
    void should_not_be_able_to_find_course_by_id(){
        Course course1 = new Course(1L,"math");

        courseRepository.save(course1);

        var result = restTemplate.getForEntity("http://localhost:" +port+ "/course/-1", Course.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(404);

    }

    @Test
    void should_not_be_able_delete_course_by_id(){
        Course course1 = new Course(1L,"math");

        courseRepository.save(course1);

        var result = restTemplate.postForEntity("http://localhost:" +port+ "/deletecourse/-1", course1, Course.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(404);

    }

    // nie dziala
    @Test
    void should_be_able_to_update_course(){
        Course course = new Course(1L, "math");

        Course editCourse = new Course(1L, "chemistry");

        restTemplate.put("http://localhost:" +port+ "/updateCourse", editCourse, Course.class);

        assertThat(course.getName()).isEqualTo(editCourse.getName());

    }

}