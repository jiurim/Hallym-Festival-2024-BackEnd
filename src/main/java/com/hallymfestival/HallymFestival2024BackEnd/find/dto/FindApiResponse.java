package com.hallymfestival.HallymFestival2024BackEnd.find.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class FindApiResponse {
    private Long id;
    private Long findid;
    private String name;
    private String point;
    private String location;
    private String image_url;
    private LocalDateTime update_time;
    private boolean returnYn;

    @Builder
    public FindApiResponse(Long findid, String name, String point, String location, String image_url
                            , LocalDateTime update_time,boolean returnYn){
        this.findid=findid;
        this.name=name;
        this.point=point;
        this.location=location;
        this.image_url=image_url;
        this.update_time=update_time;
        this.returnYn=returnYn;
    }
}
