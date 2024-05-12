package com.hallymfestival.HallymFestival2024BackEnd.notice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class NoticeDto {
    private String content;
    private String title;

    public NoticeDto(String title,String content) {
        this.title=title;
        this.content=content;
    }

    public void setContent(String content){
        this.content=content;
    }

    public String getContent(){
        return content;
    }

    public String getTitle(){return title;}
    public void setTitle(String title){this.title=title;}
}
