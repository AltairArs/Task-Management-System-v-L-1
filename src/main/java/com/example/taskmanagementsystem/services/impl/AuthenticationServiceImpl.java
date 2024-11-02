package com.example.taskmanagementsystem.services.impl;

import com.example.taskmanagementsystem.domain.dto.requests.RefreshJwtTokenRequest;
import com.example.taskmanagementsystem.domain.dto.requests.UserLoginRequest;
import com.example.taskmanagementsystem.domain.dto.requests.UserRegistrationRequest;
import com.example.taskmanagementsystem.domain.dto.responses.JwtAuthenticationResponse;
import com.example.taskmanagementsystem.domain.models.jpa.UserEntity;
import com.example.taskmanagementsystem.domain.models.redis.JwtTokenRedisHash;
import com.example.taskmanagementsystem.enums.UserRoleEnum;
import com.example.taskmanagementsystem.repo.JwtTokenRepository;
import com.example.taskmanagementsystem.repo.UserRepository;
import com.example.taskmanagementsystem.services.AuthenticationService;
import com.example.taskmanagementsystem.services.JwtService;
import com.example.taskmanagementsystem.services.PasswordEncoderService;
import com.example.taskmanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

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
            throw new AccessDeniedException("User with this email already exists");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setLastLogin(userEntity.getCreatedAt());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(passwordEncoderService.getPasswordEncoder().encode(request.getPassword()));
        if (userRepository.countAll() == 0){
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
        return jwtService.generateTokenResponse(userService.getUser(request.getEmail()));
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshJwtTokenRequest request) {
        String oldRefreshToken = request.getRefreshToken();
        JwtTokenRedisHash tokenRedisHash = jwtTokenRepository.getByToken(oldRefreshToken)
                .orElseThrow(() -> new AccessDeniedException("Token is invalid pr expired"));
        if (!tokenRedisHash.getTokenType().equals("REFRESH")){
            throw new AccessDeniedException("Invalid token type, expected REFRESH, found: " + tokenRedisHash.getTokenType());
        }
        String email = jwtService.extract(tokenRedisHash.getToken(), "email");
        if (email == null){
            throw new AccessDeniedException("Provided token is invalid");
        }
        jwtTokenRepository.deleteAllByUserEmail(email);
        return jwtService.generateTokenResponse(userService.getUser(email));
    }
}
