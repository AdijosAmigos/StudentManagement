package com.example.StudentManagement;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.jni.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.Date;

@RestController
public class LoginApi {
    /*

    @PostMapping("/login")
    public String login(@RequestBody Student student){
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(student.getFirstName())
                .claim("roles", "user")
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + 30000))
                .signWith(SignatureAlgorithm.HS512, student.getLastName())
                .compact();
    }

     */
}
