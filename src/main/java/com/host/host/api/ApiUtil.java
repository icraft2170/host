package com.host.host.api;

import com.host.host.exception.HostException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class ApiUtil {
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) ip = request.getRemoteAddr();
        return ip;
    }


    public static String getHostname(String clientIp) {
        try {
            InetAddress inetAddress = InetAddress.getByName(clientIp);
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new HostException("알 수 없는 호스트명 입니다.");
        }
    }

    public static boolean isAlive(String clientIp){
        try {
            InetAddress aliveCheck = InetAddress.getByName(clientIp);
            return aliveCheck.isReachable(2000);
        }
        catch (UnknownHostException e) {
            throw new HostException("알 수 없는 호스트명 입니다.");
        } catch (IOException e) {
            throw new HostException("IOException");
        }
    }
}
