package com.example.employee_management.security;

import com.example.employee_management.service.CustomerDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomerDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, CustomerDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Doc header Authorization tu request
        String authHeader = request.getHeader("Authorization");

//        Khong có token -> Bo qua de Spring Security xu ly
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
//        Tach token khoi chuoi "Bearer <token>"
        String token = authHeader.substring(7);

//        Xac thuc token hop le va con han
        if(jwtUtil.isTokenValid(token)){
            String username = jwtUtil.extractUsername(token);

//            Load thong tin user tu DB
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            Tao Authentication va dat vao SecurityContext
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}
