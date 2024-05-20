package com.hallymfestival.HallymFestival2024BackEnd.domain.community.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;


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
    @DateTimeFormat(pattern = "dd HH.mm.ss")
    private LocalDateTime date;

    @Column(nullable = false)
    private boolean deleteYn;

<<<<<<< HEAD
    public CommunityEntity() {
        this.date = LocalDateTime.now();
    }
=======
    @Column(nullable = false)
    private String nickname;
>>>>>>> 7191873a7908bc9cb059d6790dc6c2f0f59a58fe
}

