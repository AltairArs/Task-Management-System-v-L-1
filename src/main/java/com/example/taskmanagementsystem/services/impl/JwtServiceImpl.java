package com.example.taskmanagementsystem.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.services.JwtService;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${token.signing.key}")
    private String jwtSigningKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long jwtRefreshExpiration;

    private String generateToken(UserDetails userDetails, long jwtExpiration, String tokenType){
        HashMap<String, Object> claims = new HashMap<>();
        Date createdAt = new Date();
        if (userDetails instanceof UserEntity customUserDetails) {
            claims.put("email", customUserDetails.getEmail());
            claims.put("role", customUserDetails.getRole());
            claims.put("tokenType", tokenType);
            claims.put("expires", createdAt.toInstant().plus(jwtExpiration, ChronoUnit.SECONDS));
        }
        return tokenise(claims);
    }

    private String tokenise(HashMap<String, Object> claims){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSigningKey);
            return JWT.create().withPayload(claims).sign(algorithm);
        } catch (JWTCreationException exception){
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, jwtExpiration, "ACCESS");
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails, jwtRefreshExpiration, "REFRESH");
    }

    @Override
    public String extract(String token, String value) {
        return getPayload(token).get(value).asString();
    }

    private Map<String, Claim> getPayload(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSigningKey);
            return JWT.require(algorithm).build().verify(token).getClaims();
        } catch (JWTVerificationException exception){
            exception.printStackTrace();
            return null;
        }
    }
}
