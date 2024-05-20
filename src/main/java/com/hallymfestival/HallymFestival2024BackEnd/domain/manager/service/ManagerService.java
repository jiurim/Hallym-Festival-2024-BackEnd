package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.service;

import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.ManagerResponseDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.repository.ManagerRepository;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.repository.RefreshTokenRepository;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public ManagerResponseDto findManagerInfoById(Long id){
        return managerRepository.findById(id)
                .map(ManagerResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public ManagerResponseDto findMemberInfoUsername(String username){
        return managerRepository.findByUsername(username)
                .map(ManagerResponseDto::of)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    public void logout(HttpServletRequest request){
        refreshTokenRepository.deleteByKey(String.valueOf(SecurityUtil.getLoginMember()))
                .orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 7191873a7908bc9cb059d6790dc6c2f0f59a58fe
