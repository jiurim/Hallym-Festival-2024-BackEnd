package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}