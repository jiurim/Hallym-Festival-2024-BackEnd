package com.hallymfestival.HallymFestival2024BackEnd.find.dto;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class FindAddRequest {
    private long id;
    private String name;
    private String location;
    private String delete_image_url;
    private MultipartFile image;
    //여기서 T는 날짜와 시간을 더 확실하게 구분짓기 위함
    @DateTimeFormat(pattern = "yyyy.MM.dd'T'HH:mm")
    private LocalDateTime upload_time;

}
