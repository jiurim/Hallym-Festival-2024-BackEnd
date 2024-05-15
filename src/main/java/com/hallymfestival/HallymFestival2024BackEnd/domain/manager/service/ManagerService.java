package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.service;

import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.ManagerResponseDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.repository.ManagerRepository;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.repository.RefreshTokenRepository;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     내 정보 조회
    SecurityUtil.getLoginMemberId()를 통해 현재 로그인 되어있는 유저 정보의 ID를 가져옴
    Member_repository에서 Id로 유저 정보 가져와 dto로 리턴
     **/
    @Transactional(readOnly = true)
     public ManagerResponseDto getMyInfo() {
        return managerRepository.findByUsername(SecurityUtil.getLoginMember())
                .map(ManagerResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    @Transactional
    public void logout(HttpServletRequest request) {

        // refreshToken 삭제
        refreshTokenRepository.deleteByKey(String.valueOf(SecurityUtil.getLoginMember()))
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }


}
