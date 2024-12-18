package com.example.taskmanagementsystem.services.impl;

import com.example.taskmanagementsystem.domain.dto.requests.RefreshJwtTokenRequest;
import com.example.taskmanagementsystem.domain.dto.requests.UserLoginRequest;
import com.example.taskmanagementsystem.domain.dto.requests.UserRegistrationRequest;
import com.example.taskmanagementsystem.domain.dto.responses.JwtAuthenticationResponse;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.domain.models.redis.JwtTokenRedisHash;
import com.example.taskmanagementsystem.enums.UserRoleEnum;
import com.example.taskmanagementsystem.exceptions.TaskManagementException;
import com.example.taskmanagementsystem.repo.JwtTokenRepository;
import com.example.taskmanagementsystem.repo.UserRepository;
import com.example.taskmanagementsystem.services.AuthenticationService;
import com.example.taskmanagementsystem.services.JwtService;
import com.example.taskmanagementsystem.services.PasswordEncoderService;
import com.example.taskmanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoderService passwordEncoderService;
    private final JwtService jwtService;
    private final UserService userService;
    private final JwtTokenRepository jwtTokenRepository;


    @Override
    public JwtAuthenticationResponse register(UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())){
            throw new TaskManagementException("User with this email already exists: " + request.getEmail());
        }
        UserEntity userEntity = UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoderService.getPasswordEncoder().encode(request.getPassword()))
                .build();
        if (userRepository.count() == 0){
            userEntity.setRole(UserRoleEnum.ADMIN);
        }
        userRepository.save(userEntity);
        return jwtService.generateTokenResponse(userEntity);
    }

    @Override
    public JwtAuthenticationResponse authenticate(UserLoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        UserEntity user = userService.getUser(request.getEmail());
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        return jwtService.generateTokenResponse(user);
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshJwtTokenRequest request) {
        String oldRefreshToken = request.getRefreshToken();
        JwtTokenRedisHash tokenRedisHash = jwtTokenRepository.getByToken(oldRefreshToken)
                .orElseThrow(() -> new TaskManagementException("Token is invalid pr expired"));
        if (!tokenRedisHash.getTokenType().equals("REFRESH")){
            throw new TaskManagementException("Invalid token type, expected REFRESH, found: " + tokenRedisHash.getTokenType());
        }
        String email = jwtService.extract(tokenRedisHash.getToken(), "email");
        if (email == null){
            throw new TaskManagementException("Provided token is invalid");
        }
        jwtTokenRepository.deleteAllByUserEmail(email);
        return jwtService.generateTokenResponse(userService.getUser(email));
    }
}
