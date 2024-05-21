package com.hallymfestival.HallymFestival2024BackEnd.domain.community.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommunityDetailDto {
    private long id;
    private String content;
    private LocalDateTime date;
    private String nickname;
}