package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

//회원가입,로그인 요청시 사용되는 dto
public class TokenRequestDto {
    private String accessToken;
    private String refreshToken;
}