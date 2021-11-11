package com.host.host.api;

import javax.servlet.http.HttpServletRequest;

public class ApiUtil {
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) ip = request.getRemoteAddr();
        return ip;
    }
}
