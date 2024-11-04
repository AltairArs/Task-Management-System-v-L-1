package com.example.taskmanagementsystem.services.impl;

import com.example.taskmanagementsystem.repo.JwtTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutHandler {
    private final JwtTokenRepository jwtTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return;
        final String jwtToken = authHeader.substring(7);
        jwtTokenRepository.deleteByToken(jwtToken);
        SecurityContextHolder.clearContext();
    }
}
