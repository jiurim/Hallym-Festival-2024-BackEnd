package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    @ManyToMany
    @JoinTable(
        name = "manager_authority",
        joinColumns = @JoinColumn(name = "manager_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "authority_status", referencedColumnName = "authority_status")
    )
    private Set<Authority> authorities = new HashSet<>();

    @Builder
    public Manager(String username, String password, Set<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
}