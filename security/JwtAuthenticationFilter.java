package com.rsl.event.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JwtAuthenticationFilter is a custom filter that intercepts incoming HTTP requests 
 * to validate the JWT token in the Authorization header.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Logger instance for logging events
    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtHelper jwtHelper; // Helper class for handling JWT operations

    @Autowired
    private UserDetailsService userDetailsService; // Service to load user details

    /**
     * The method filters incoming requests, extracting the JWT token, validating it,
     * and setting the security context if the token is valid.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extract the Authorization header from the request
        String requestHeader = request.getHeader("Authorization");
        logger.info("Header: {}", requestHeader);

        String username = null;
        String token = null;

        // Check if the Authorization header is present and starts with "Bearer "
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7); // Extract the token part

            try {
                // Extract the username from the token
                username = jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.error("Illegal argument while fetching the username !!", e);
            } catch (ExpiredJwtException e) {
                logger.error("JWT token expired !!", e);
            } catch (MalformedJwtException e) {
                logger.error("Invalid JWT token !!", e);
            } catch (Exception e) {
                logger.error("JWT token error: {}", e.getMessage(), e);
            }
        } else {
            logger.warn("Invalid Header Value !!");
        }

        // If the username is extracted and the security context is not set, validate the token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); // Load user details from the username

            // Validate the token
            if (jwtHelper.validateToken(token, userDetails)) {
                // Create an authentication token
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Set additional details
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); // Set the authentication in the security context
            } else {
                logger.warn("Validation fails !!");
            }
        }
         
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
