package com.hallymfestival.HallymFestival2024BackEnd.notice.dto;

import lombok.*;

@Data
public class NoticeDto {
    private String title;
    private String content;

    public NoticeDto(String title, String content){
        this.title = title;
        this.content = content;
    }
}