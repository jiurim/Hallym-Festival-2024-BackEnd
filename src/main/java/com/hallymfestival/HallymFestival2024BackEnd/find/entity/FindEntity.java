package com.hallymfestival.HallymFestival2024BackEnd.find.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name="Find")
public class FindEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //물품명
    @Column(nullable = false)
    private String name;
    //발견위치
    private String location;
    //이미지Url
    @Column(nullable = false)
    private String image_url;
    //업로드 시간
    @Column(nullable = false)
    private LocalDateTime upload_time;
    //회수여부
    private boolean is_return;

    @Builder
    public FindEntity(String name, String point, String location,
                      String image_url, LocalDateTime upload_time, boolean is_return) {
        this.name = name;
        this.location = location;
        this.image_url = image_url;
        this.upload_time = upload_time;
        this.is_return = is_return;
    }

    public FindEntity() {
        this.upload_time = LocalDateTime.now();
        this.is_return = false;
    }
}
