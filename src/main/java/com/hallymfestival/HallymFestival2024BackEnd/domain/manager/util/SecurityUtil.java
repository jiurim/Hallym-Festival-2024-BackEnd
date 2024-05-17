package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {
    private SecurityUtil(){}

    // SecurityContext에 유저 정보가 저장되는 시점
    // Request가 들어올 때 JWTFilter의 doFilter 에서 저장
    public static String getLoginMember() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No authentication information.");
        }
        return authentication.getName();
    }
}
