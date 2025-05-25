package org.springboot.userservice.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springboot.userservice.entity.JWT;
import org.springboot.userservice.entity.Users;
import org.springboot.userservice.repository.JwtRepo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtService jwtService;
    private final UserService userService;
    private final JwtRepo jwtRepo;

    public JwtFilter(JwtService jwtService, UserService userService, JwtRepo jwtRepo) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.jwtRepo = jwtRepo;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt, name;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        name = jwtService.getUsernameFromToken(jwt);
        if (name != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            Users user = userService.getUserByName(name)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            JWT jwtToken = jwtRepo.findByToken(jwt);
            if (jwtToken == null) {
                throw new RuntimeException("Token not found");
            }

            if (!jwtService.isTokenExpired(jwt)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}