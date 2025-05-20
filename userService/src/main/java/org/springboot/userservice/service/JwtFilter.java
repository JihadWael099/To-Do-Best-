package org.springboot.userservice.service;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springboot.userservice.entity.Users;
import org.springboot.userservice.exceptions.UserNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    public JwtFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = jwtService.getTokenFromHeader(request);
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);
        try {
            if (jwtService.validateToken(token) && !jwtService.isTokenExpired(token)) {
                String username = jwtService.convertJwtClaims(token).getSubject();
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    Optional<Users> user = userService.getUserByName(username);
                    if (user.isPresent()) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(user, null, user.get().getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                    else throw  new UserNotFoundException("user not found");
                }
            }
        } catch (Exception e) {
            System.out.println("JWT Error: " + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
