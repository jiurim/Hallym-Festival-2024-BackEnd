package com.hallymfestival.HallymFestival2024BackEnd.notice.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Notice")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String title;
}
