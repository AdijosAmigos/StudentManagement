package com.example.StudentManagement;

import com.example.StudentManagement.model.Student;
import com.example.StudentManagement.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("student test")
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
        assertThat(result.hasBody()).isTrue();
    }

    @Test
    void should_add_student(){
        Student student = new Student(1L, "Adrian", "Kowalski");

        var result = restTemplate.postForEntity("http://localhost:" +port+ "/addStudent", student, Student.class);

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

        restTemplate.delete("http://localhost:" +port+ "/deleteStudent/1", student1, Student.class);

        //czy ta asercja jest poprawna?
        assertThat(studentRepository.findById(1L).isPresent()).isFalse();


    }

    @Test
    void should_not_be_able_find_student_when_id_less_than_zero(){
        Student student1 = new Student(1L, "Adrian", "Kowalski");

        studentRepository.save(student1);

        var result = restTemplate.getForEntity("http://localhost:" +port+ "/student/-1", Student.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(404);
    }


    //to bedzie do poprawki
    @Test
    void should_not_be_able_delete_student_when_id_less_than_zero(){
        Student student1 = new Student(1L, "Adrian", "Kowalski");

        studentRepository.save(student1);

        var result = restTemplate.postForEntity("http://localhost:" +port+ "/deleteStudent/-1", student1, Student.class);

        assertThat(result.getStatusCodeValue()).isEqualTo(404);
    }

}