package com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Data
@Entity
@Table(name = "reservation_limit")
public class ReservationLimit {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int limit;

    @Column(nullable = false)
    private int currentCount;

    @Version
    private Long version;

    public boolean canReserve(int count) {
        return currentCount + count <= limit;
    }

    public void reserve(int count) {
        this.currentCount += count;
    }
}