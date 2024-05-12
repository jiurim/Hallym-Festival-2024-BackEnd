package com.hallymfestival.HallymFestival2024BackEnd.find.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="Find")
@Getter
@Setter
public class FindEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String point;

    private String location;

    @Column(nullable = false)
    private String image_url;

    @Column(nullable = false)
    private LocalDateTime update_time;

    private boolean returnYn;

    @Builder
    public FindEntity(String name, String point, String location,
                      String image_url, LocalDateTime update_time, boolean returnYn) {
    this.name = name;
    this.point = point;
    this.location = location;
    this.image_url = image_url;
    this.update_time = update_time;
    this.returnYn = returnYn;
    }

     public FindEntity() {
        this.update_time = LocalDateTime.now();
        this.returnYn = false;
    }

    public void setReturnYn(boolean returnYn) {
        this.returnYn=returnYn;
    }
}
