package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.controller;

import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.JwtToken;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.ManagerRequestDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.ManagerResponseDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.TokenRequestDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.service.AuthService;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class ManagerController {
    private final AuthService authService;
    private final ManagerService managerService;

    @PostMapping("/login")
    @CrossOrigin(origins = "https://hallym-festival-admin.com", maxAge = 3600)
    public ResponseEntity<JwtToken> login(@RequestBody ManagerRequestDto managerRequestDto, HttpServletResponse response) {
        String username = managerRequestDto.getUsername();
        String password = managerRequestDto.getPassword();
        JwtToken jwtToken = authService.login(managerRequestDto);

        // Set refreshToken as HttpOnly cookie
        Cookie refreshTokenCookie = new Cookie("refreshToken", jwtToken.getRefreshToken());
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // Ensure this is true in production over HTTPS
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // Set expiry time as required

        response.addCookie(refreshTokenCookie);

        log.info("request username = {}, password = {}", username, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/reissue")
    @CrossOrigin(origins = "https://hallym-festival-admin.com", maxAge = 3600)
    public ResponseEntity<Void> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        log.info("재발급 들어옴");
        JwtToken reissuedToken = authService.reissue(tokenRequestDto);
        if (reissuedToken != null) {
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", reissuedToken.getRefreshToken())
                    .httpOnly(true)
                    .build();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, responseCookie.toString());
            headers.add("accessToken", reissuedToken.getAccessToken()); // 새로운 accessToken 추가

            log.info("새로운 토큰 발급");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .build();
        } else {
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
                    .maxAge(0)
                    .path("/")
                    .build();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, responseCookie.toString());

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .headers(headers)
                    .build();
        }
    }


    @PostMapping("/logout")
    @CrossOrigin(origins = "https://hallym-festival-admin.com", maxAge = 3600)
    public ResponseEntity<String> logout(HttpServletRequest request) {
        managerService.logout(request);
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    @PostMapping("/signup")
    @CrossOrigin(origins = "https://hallym-festival-admin.com", maxAge = 3600)
    public ResponseEntity<ManagerResponseDto> signUp (@RequestBody ManagerRequestDto managerRequestDto){
        return ResponseEntity.ok(authService.signup(managerRequestDto));
    }
}