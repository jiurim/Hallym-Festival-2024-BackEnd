package com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ReservationRequestDto {
    private Long id;
    private int people_count;
    private int studentNum;
    private String StudentName;
    private int phone_number;
}