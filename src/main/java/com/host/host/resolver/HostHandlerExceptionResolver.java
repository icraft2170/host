package com.host.host.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.host.host.exception.DuplicateHostException;
import com.host.host.exception.HostException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HostHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Map<String, Object> errorResult = new HashMap<>();
            if (ex instanceof HostException || ex instanceof DuplicateHostException) {
                errorResult.put("ex", ex.getClass());
                errorResult.put("message", ex.getMessage());
                String result = objectMapper.writeValueAsString(errorResult);

                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(result);
                return new ModelAndView();
            }

        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        return null;
    }

}
