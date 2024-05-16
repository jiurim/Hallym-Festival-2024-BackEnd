package com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ReservationRequestDto {
    private Long id;
    private int people_count;
    private String studentId;
    private String name;
    private int phone_number;
}