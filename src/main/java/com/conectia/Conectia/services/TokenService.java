package com.conectia.Conectia.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.conectia.Conectia.Models.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private String secret = "Conectia_2023";

    public String getToken(User user){
        try {
            var algorithm = Algorithm.HMAC256(secret);
            var expiresDate = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
            return JWT.create()
                    .withIssuer("API Conectia")
                    .withSubject(user.getEmail())
                    .withClaim("Name:", user.getName())
                    .withExpiresAt(expiresDate)
                    .sign(algorithm);
        }catch (JWTVerificationException exception){
            throw new RuntimeException("Erro ao gerar o token");
        }

    }

    public String verifyToken(String token){
        var algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("API Conectia")
                .build()
                .verify(token)
                .getSubject();

    }
}