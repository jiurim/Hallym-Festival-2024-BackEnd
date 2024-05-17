package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.service;

import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto.ManagerResponseDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {

    private final ManagerRepository managerRepository;

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
}
