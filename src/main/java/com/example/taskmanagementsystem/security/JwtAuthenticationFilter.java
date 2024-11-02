package com.example.taskmanagementsystem.security;

import com.example.taskmanagementsystem.repo.JwtTokenRepository;
import com.example.taskmanagementsystem.services.JwtService;
import com.example.taskmanagementsystem.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final JwtService jwtService;
    private final JwtTokenRepository jwtTokenRepository;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(HEADER_NAME);
        if (authHeader == null || !StringUtils.startsWith(authHeader, BEARER_PREFIX)){
            filterChain.doFilter(request, response);
            return;
        }
        String jwtToken = authHeader.substring(BEARER_PREFIX.length());
        String email = jwtService.extract(jwtToken, "email");
        String tokenType = jwtService.extract(jwtToken, "tokenType");
        if (StringUtils.isEmpty(tokenType) || tokenType.equals("REFRESH")){
            throw new AccessDeniedException("Can not authorize user with refresh token");
        }
        jwtTokenRepository.getByToken(jwtToken)
                .orElseThrow(() -> new AccessDeniedException("Token has expired or invalid"));
        UserDetails userDetails = userService.getUser(email);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        );
        filterChain.doFilter(request, response);
    }
}
