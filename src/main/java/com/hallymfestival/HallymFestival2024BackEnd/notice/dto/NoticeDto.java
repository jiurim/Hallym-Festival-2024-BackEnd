package com.hallymfestival.HallymFestival2024BackEnd.notice.dto;

import lombok.*;

@Data
public class NoticeDto {
    private Integer id;
    private String title;
    private String content;

    public NoticeDto(Integer id,String title, String content){
        this.id=id;
        this.title = title;
        this.content = content;
    }
}