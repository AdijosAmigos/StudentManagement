package com.example.StudentManagement;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class JWTFilter extends BasicAuthenticationFilter {

    public JWTFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        UsernamePasswordAuthenticationToken authResult = getAuthenticationByToken(header);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationByToken(String header) {

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey("7aPc$VED<-Qr8)E".getBytes())
                .parseClaimsJws(header.replace("Bearer ", ""));

        String userName = claimsJws.getBody().get("name").toString();
        String role = claimsJws.getBody().get("role").toString();

        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = Collections.singleton(new SimpleGrantedAuthority(role));

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(userName, null, simpleGrantedAuthorities);

        return usernamePasswordAuthenticationToken;

    }
}
