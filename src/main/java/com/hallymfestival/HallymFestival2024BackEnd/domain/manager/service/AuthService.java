package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.service;

import com.hallymfestival.HallymFestival2024BackEnd.domain.jwt.JwtTokenProvider;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.JwtToken;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.ManagerRequestDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.ManagerResponseDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.TokenRequestDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.entity.RefreshToken;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.repository.ManagerRepository;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public JwtToken login(ManagerRequestDto managerRequestDto) {

        // 1. 로그인 시 Id와 Password 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = managerRequestDto.toAuthentication();

        // 2. 실제로 사용자의 비밀번호 확인이 이루어지는 부분
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보 기반으로 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(jwtToken.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return jwtToken;
    }

    @Transactional
    public JwtToken reissue(TokenRequestDto tokenRequestDto){

        // 1. Refresh Token 검증
        if(!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())){
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }

        // 2. Access Token에서 Manager ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Manager ID 기반으로 Refresh Token 값 가져오기
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자 입니다."));

        // 4. Refresh Token 일치하는지 확인
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())){
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateTokenDto(authentication);

        // 6. 저장소에 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(jwtToken.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 7. 토큰 발급
        return jwtToken;
    }
}