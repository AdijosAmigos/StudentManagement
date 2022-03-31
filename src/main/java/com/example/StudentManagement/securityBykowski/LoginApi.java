//package com.example.StudentManagement.securityBykowski;
//
//import com.example.StudentManagement.model.Student;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Date;
//
//@RestController
//public class LoginApi {
//
//    @PostMapping("/login")
//    public String login(@RequestBody Student student){
//
//        long currentTimeMillis = System.currentTimeMillis();
//        return Jwts.builder()
//                .setSubject(student.getFirstName())
//                .claim("roles", "student")
//                .setIssuedAt(new Date(currentTimeMillis))
//                .setExpiration(new Date(currentTimeMillis + 1000 * 60))
//                .signWith(SignatureAlgorithm.HS512, student.getLastName())
//                .compact();
//
//    }
//}
