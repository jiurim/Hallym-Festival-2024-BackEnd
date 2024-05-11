package com.hallymfestival.HallymFestival2024BackEnd.domain.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "board")
public class BoardEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String createdBy;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<BoardImageEntity> images;

}
