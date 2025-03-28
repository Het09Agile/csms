package com.csms.utils.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleException(response, e);
        }
    }
    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error", "Unauthorized");
        errorDetails.put("message", e.getMessage());

        ServletOutputStream out = response.getOutputStream();
        new ObjectMapper().writeValue(out, errorDetails);
        out.flush();
    }

}

