package com.hallymfestival.HallymFestival2024BackEnd.community.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalTime;


@Entity
@Table(name= "community")
@Data
public class CommunityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime date;

    @Column(nullable = false)
    private boolean deleteYn;
}

