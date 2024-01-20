package com.example.springboot.user.config;


import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final static String SECRET_KEY = "C609D6E3F58BDE1AA94A6B7BC7C49FAF9E27DC83B770503C3A74C4D6C6231EB2";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(Long id) {
        return createToken(id.toString());
    }

    public String createToken(String subject) {
        System.out.println("JwtService: createToken: subject: " + subject);
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
        System.out.println("JwtService: extractClaim: token: " + token);

        return Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSigningKey() {
        byte[] keyBytes= Decoders.BASE64.decode(jwtSecrete);
        return Keys.hmacShaKeyFor(jwtSecrete.getBytes());
    }

}
