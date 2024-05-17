package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.service;

import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.JwtToken;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.ManagerRequestDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.ManagerResponseDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.TokenRequestDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.entity.Authority;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.entity.AuthorityEnum;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.entity.Manager;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.entity.RefreshToken;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.repository.AuthorityRepository;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.repository.ManagerRepository;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.repository.RefreshTokenRepository;
import com.hallymfestival.HallymFestival2024BackEnd.domain.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final ManagerRepository managerRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public ManagerResponseDto signUp(ManagerRequestDto managerRequestDto) {
        // 이메일 중복 회원 검증
        if (managerRepository.existsByUsername(managerRequestDto.getUsername())) {
            throw new RuntimeException("이미 가입되어 있는 아이디입니다.");
        }
        log.info("회원가입 메소드 들어옴");
        Optional<Authority> authority = authorityRepository.findByAuthorityStatus(AuthorityEnum.ROLE_ADMIN);
        Set<Authority> set = new HashSet<>();
        Manager manager = managerRequestDto.toManager(passwordEncoder, set);
        return ManagerResponseDto.of(managerRepository.save(manager));
    }

    //로그인
    @Transactional
    public JwtToken login(ManagerRequestDto managerRequestDto) {
        // Dto의 email, password를 받고 UsernamePasswordAuthenticationToken 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = managerRequestDto.toAuthentication();
        log.info("1");
        log.info(authenticationToken.toString());

        // authenticate 메서드가 실행이 될 때 Member_details_service 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("2");
        // JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateTokenDto(authentication);
        log.info("3");

        // refreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
            .key(authentication.getName())
            .value(jwtToken.getRefreshToken())
            .build();

        refreshTokenRepository.save(refreshToken);
        log.info("4");

        return jwtToken;
    }

    //토큰 재발급
    @Transactional
    public JwtToken reissue(TokenRequestDto tokenRequestDto) {
        // refresh Token 검증
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // access Token에서 Authentication객체 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // DB에서 member_id를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // refresh Token이 다르면
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 새로운 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateTokenDto(authentication);
        log.info("999");

        // refreshToken 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(jwtToken.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return jwtToken;
    }

//    @Transactional
//    public JwtToken reissue(TokenRequestDto tokenRequestDto) {
//        // refresh Token 검증
//        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
//            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
//        }
//
//        // access Token에서 Authentication 객체 가져오기
//        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
//
//        // DB에서 member_id를 기반으로 Refresh Token 값 가져오기
//        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
//                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));
//
//        // refresh Token이 다르면
//        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
//            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
//        }
//
//        // 액세스 토큰 만료 여부 확인
//        if (jwtTokenProvider.isAccessTokenExpired(tokenRequestDto.getAccessToken())) {
//            throw new RuntimeException("액세스 토큰이 만료되었습니다.");
//        }
//
//        // 새로운 토큰 생성
//        JwtToken jwtToken = jwtTokenProvider.generateTokenDto(authentication);
//
//        // refreshToken 업데이트
//        RefreshToken newRefreshToken = refreshToken.updateValue(jwtToken.getRefreshToken());
//        refreshTokenRepository.save(newRefreshToken);
//
//        // 토큰 발급
//        return jwtToken;
//    }
}