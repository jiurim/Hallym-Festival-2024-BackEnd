package com.hallymfestival.HallymFestival2024BackEnd.domain.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    // 실제 필터링 로직은 doFilterInternal 에 들어감
    // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
    //
     @Override
     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
         try {
            // 1. Request Header 에서 토큰을 꺼냄
            String jwt = resolveToken(request);
            log.info("1234");

            // 2. validateToken 으로 토큰 유효성 검사
            // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                Date expirationDate = jwtTokenProvider.getExpirationDate(jwt);
                long currentTimeMillis = System.currentTimeMillis();
                long expirationTimeMillis = expirationDate.getTime();
                long timeUntilExpiration = expirationTimeMillis - currentTimeMillis;

                // 토큰 만료까지 1분 이하로 남았을 때 경고 메시지 설정 (60,000 밀리초 = 1분)
                if (timeUntilExpiration < 60000) {
                    log.info("만료전");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.getWriter().write("만료전입니다 다시 로그인하세요");
                    response.getWriter().flush();
                    return;
                }

                Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } else {
                // 토큰이 유효하지 않은 경우 에러 응답을 반환
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write("유효하지 않은 메세지");
                response.getWriter().flush();
            }
         } catch (ExpiredJwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("토큰이 만료됨");
            response.getWriter().flush();
         } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("서버오류");
            response.getWriter().flush();
         }
     }

    // Request Header 에서 토큰 정보를 꺼내오기
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}



//@Slf4j
//@RequiredArgsConstructor
//
//public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//    public static final String AUTHORIZATION_HEADER = "Authorization";
//    public static final String BEARER_PREFIX = "Bearer ";
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    // 실제 필터링 로직은 doFilterInternal 에 들어감
//    // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
//
//    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
//        this.setAuthenticationManager(authenticationManager);
//        this.jwtTokenProvider = jwtTokenProvider;
//        this.setFilterProcessesUrl("/api/admin/login"); // 원하는 URL로 설정해야 작동합니다.
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        String jwt = resolveToken(request);
//
//        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
//            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
//            return getAuthenticationManager().authenticate(authentication);
//        } else {
//            throw new JwtException("유효하지 않은 JWT 토큰입니다.");
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        SecurityContextHolder.getContext().setAuthentication(authResult);
//        chain.doFilter(request, response);
//    }
//
//    // Request Header 에서 토큰 정보를 꺼내오기
//    public String resolveToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//}




