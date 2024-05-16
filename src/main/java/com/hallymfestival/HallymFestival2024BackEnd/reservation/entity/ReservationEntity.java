package com.hallymfestival.HallymFestival2024BackEnd.reservation.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private int quantity;

    public ReservationEntity(Long id,int quantity){
        this.id=id;
        this.quantity=quantity;
    }

    public void decrease(){
        if(this.quantity==0){
            throw new RuntimeException("예약이 꽉 찼습니다.");
        }
        this.quantity --;
    }

}