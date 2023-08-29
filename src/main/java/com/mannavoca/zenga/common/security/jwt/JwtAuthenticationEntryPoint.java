package com.mannavoca.zenga.common.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mannavoca.zenga.common.exception.Error;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        Error error = (Error) request.getAttribute("exception");

        setResponse(response, error);
    }

    private void setResponse(HttpServletResponse response, Error error) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(error.getHttpStatus().value());

        Map<String, Object> responseMap = new LinkedHashMap<>();
        responseMap.put("errorCode", error.getErrorCode());
        responseMap.put("message", error.getMessage());
        responseMap.put("timestamp", LocalDateTime.now());
        responseMap.put("log", ""); // 형식 통일을 위해 넣음

        String responseJson = objectMapper.writeValueAsString(responseMap);
        response.getWriter().print(responseJson);
    }
}
