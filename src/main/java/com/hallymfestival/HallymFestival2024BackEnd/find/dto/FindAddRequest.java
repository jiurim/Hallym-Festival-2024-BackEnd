package com.hallymfestival.HallymFestival2024BackEnd.find.dto;
import java.time.LocalDateTime;

import com.sun.istack.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindAddRequest {
    private long id;
    private Long findid;
    private String name;
    private String point;
    private String image_url;
    @DateTimeFormat(pattern = "yyyy.MM.dd'T'HH:mm")
    //여기서 T는 날짜와 시간을 더 확실하게 구분짓기 위함
    private LocalDateTime update_time;
    private boolean returnYn;
    private String location;



}
