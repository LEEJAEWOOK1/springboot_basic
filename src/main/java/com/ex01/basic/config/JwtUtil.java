package com.ex01.basic.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("test1234")
    private String secretKey;
    private final long expirationMs = 1000 * 60;
    public String generateToken(String username){
        System.out.println("jwt secretKey : " + secretKey);
        Claims claims = Jwts.claims();
        claims.put("username", username);
        claims.put("name", "추가 이름");
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expirationMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}
