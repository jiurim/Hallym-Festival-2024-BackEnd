package com.hallymfestival.HallymFestival2024BackEnd.community.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table("community")
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
    private Date date;

    @Column(nullable = false)
    private boolean deleteYn;
}

}
