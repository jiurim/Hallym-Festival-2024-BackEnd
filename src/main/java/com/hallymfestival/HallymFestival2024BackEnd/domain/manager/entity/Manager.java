package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "manager")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public Manager(String username, String password, Authority authority){
        this.username = username;
        this.password = password;
        this.authority = authority;
    }
}