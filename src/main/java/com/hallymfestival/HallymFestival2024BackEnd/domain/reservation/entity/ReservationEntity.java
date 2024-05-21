package com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@Data
@Table(name="reservation")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int studentNum;
    @Column(nullable = false)
    private String studentName;
    @Column(nullable = false)
    private int phone_number;
    @Column(nullable = false)
    private int people_count;
    //데이터가 들어간 시점 즉 예약 신청완료 후 DB저장된 시간
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reg_date;
    // 예약 성공여부
    @Column(nullable = false)
    private boolean success;
}