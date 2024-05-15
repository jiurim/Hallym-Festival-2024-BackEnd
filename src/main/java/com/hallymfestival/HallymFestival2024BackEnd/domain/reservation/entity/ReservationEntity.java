package com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="reservation")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String studentId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int phone_number;
    @Column(nullable = false)
    private int people_count;
    @Column(nullable = false)
    private LocalDateTime reg_date;
    //데이터가 들어간 시점 즉 예약 신청완료 후 DB저장된 시간
}