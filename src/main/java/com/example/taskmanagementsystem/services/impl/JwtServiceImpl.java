package com.example.taskmanagementsystem.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.example.taskmanagementsystem.domain.dto.responses.JwtAuthenticationResponse;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.domain.models.redis.JwtTokenRedisHash;
import com.example.taskmanagementsystem.repo.JwtTokenRepository;
import com.example.taskmanagementsystem.services.JwtService;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final JwtTokenRepository jwtTokenRepository;
    @Value("${token.signing.key}")
    private String jwtSigningKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long jwtRefreshExpiration;

    private String generateToken(UserDetails userDetails, long jwtExpiration, String tokenType){
        Map<String, Object> claims = new HashMap<>();
        Date createdAt = new Date();
        if (userDetails instanceof UserEntity customUserDetails) {
            claims.put("email", customUserDetails.getEmail());
            claims.put("role", customUserDetails.getRole().name());
            claims.put("tokenType", tokenType);
            claims.put("expires", createdAt.toInstant().plus(jwtExpiration, ChronoUnit.SECONDS));
        }
        return tokenise(claims);
    }

    private String tokenise(Map<String, Object> claims){
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

    @Override
    public JwtAuthenticationResponse generateTokenResponse(UserEntity user) {
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);
        jwtTokenRepository.save(
                JwtTokenRedisHash.builder()
                        .token(accessToken)
                        .expiration(jwtExpiration)
                        .userEmail(user.getEmail())
                        .tokenType("ACCESS")
                        .build()
        );
        jwtTokenRepository.save(
                JwtTokenRedisHash.builder()
                        .token(refreshToken)
                        .expiration(jwtRefreshExpiration)
                        .userEmail(user.getEmail())
                        .tokenType("REFRESH")
                        .build()
        );
        return JwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
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
