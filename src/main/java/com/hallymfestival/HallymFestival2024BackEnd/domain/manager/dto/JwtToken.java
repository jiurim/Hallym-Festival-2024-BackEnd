package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtToken{

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;

}