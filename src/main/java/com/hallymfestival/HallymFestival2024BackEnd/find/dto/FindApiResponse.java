package com.hallymfestival.HallymFestival2024BackEnd.find.dto;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Builder
public class FindApiResponse {
    private Long id;
    private String name;
    private String location;
    private String image_url;
    private LocalDateTime upload_time;
    private boolean is_return;
}
