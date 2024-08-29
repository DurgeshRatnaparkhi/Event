package com.rsl.event.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This class handles unauthorized access to secured resources by implementing
 * the AuthenticationEntryPoint interface.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // Logger instance for logging unauthorized access attempts
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    /**
     * This method is triggered whenever an unauthorized user tries to access
     * a secured REST resource.
     *
     * @param request       the HttpServletRequest
     * @param response      the HttpServletResponse
     * @param authException the exception thrown due to unauthorized access
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
        // Log the unauthorized access attempt with the exception message
        logger.error("Unauthorized access error: {}", authException.getMessage());

        // Set the response status to 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Set the response content type to application/json
        response.setContentType("application/json");

        // Write the custom error message to the response body
        response.getWriter().write("{\"error\": \"Access Denied! Unauthorized access.\"}");
    }
}
