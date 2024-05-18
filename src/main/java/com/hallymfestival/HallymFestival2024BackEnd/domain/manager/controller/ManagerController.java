package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.controller;

import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.JwtToken;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.ManagerRequestDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.ManagerResponseDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.TokenRequestDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.service.AuthService;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<JwtToken> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        managerService.logout(request);
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    @PostMapping("/sign_up")
    public ResponseEntity<ManagerResponseDto> signUp (@RequestBody ManagerRequestDto managerRequestDto){
        return ResponseEntity.ok(authService.signUp(managerRequestDto));
    }
}
