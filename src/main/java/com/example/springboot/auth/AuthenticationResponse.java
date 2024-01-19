package com.example.springboot.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // generates getters and setters for all fields
@Builder  // produces complex builder APIs for class
@AllArgsConstructor  // generates a constructor with 1 parameter for each field in your class
@NoArgsConstructor  // generates a no argument constructor
public class AuthenticationResponse {
    private String token;
}
