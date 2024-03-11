package com.tadanoluka.project1.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tadanoluka.project1.controller.controller.ErrorPresentation;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class Custom401EntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
        throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("WWW-Authenticate", "Basic realm=\"Realm\"");
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(responseStream, new ErrorPresentation(
                401,
                "Unauthorized",
                "Unauthorized.",
                request.getServletPath()
        ));
        responseStream.flush();
    }
}
