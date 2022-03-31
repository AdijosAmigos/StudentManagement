package com.example.StudentManagement.securityBaeldung;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails USER = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("USER")
                .build();

        UserDetails ADMIN = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        UserDetails MODERATOR = User.withDefaultPasswordEncoder()
                .username("mode")
                .password("mode")
                .roles("MODERATOR")
                .build();

        return new InMemoryUserDetailsManager(USER, ADMIN, MODERATOR);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/students", "/courses", "/student/{id}", "/course/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/addStudent", "/addCourse", "/assignToCourse/{studentId}/{courseId}").hasAnyRole("MODERATOR", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/students/{id}", "/courses/{id}").hasAnyRole("MODERATOR", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/deleteStudent/{id}", "/deleteCourse/{id}").hasRole("ADMIN")
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable();
    }
}
