package com.hallymfestival.HallymFestival2024BackEnd.find.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class Find {
    private long id;
    private Long findid;
    private String name;
    private String point;
    private String location;
    private String image_url;
    private LocalDateTime update_time;
    private boolean returnYn;
}
