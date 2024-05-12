package com.hallymfestival.HallymFestival2024BackEnd.community.dto;

import lombok.Data;

@Data
public class CommunityDto {
    private String content;
    private String password;

    public CommunityDto(String content, String password){
        this.content = content;
        this.password = password;
    }

}
