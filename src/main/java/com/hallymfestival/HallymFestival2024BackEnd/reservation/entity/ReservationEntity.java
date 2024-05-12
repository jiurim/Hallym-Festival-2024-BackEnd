package com.hallymfestival.HallymFestival2024BackEnd.reservation.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="reservation")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int student_id;
    private String name;
    private String phone_number;
    private int people_count;
    private Date reg_date;
    //데이터가 들어간 시점 즉 예약 신청완료 후 DB저장된 시간
}
