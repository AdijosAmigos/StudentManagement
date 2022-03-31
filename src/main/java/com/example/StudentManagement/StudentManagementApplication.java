
package com.example.StudentManagement;

//import com.example.StudentManagement.securityBykowski.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
public class StudentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
    }

//    @Bean
//    public FilterRegistrationBean filterRegistrationBean (){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(new JwtFilter());
//        filterRegistrationBean.setUrlPatterns(Collections.singleton("/students*"));
//        return filterRegistrationBean;
//    }


}
