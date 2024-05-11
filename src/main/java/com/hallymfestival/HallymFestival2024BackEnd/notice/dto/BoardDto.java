package com.hallymfestival.HallymFestival2024BackEnd.notice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto {
    private String content;
    private String title;

    public BoardDto(String content, String title) {
        this.content=content;
        this.title=title;
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
