package com.example.springboot.user.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final String jwtSecrete = "E98F44FDD11C3B94919D6F6326442E98F44FDD11C3B94919D6F6326442";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(Long id) {
        return createToken(id.toString());
    }

    public String createToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, jwtSecrete)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 days
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecrete).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenValid(String token, String _username) {
        final String username = extractUsername(token);
        return (username.equals(_username.toString()) && !isTokenExpired(token));
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
