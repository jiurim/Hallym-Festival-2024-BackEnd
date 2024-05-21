package com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ReservationSaveDto {
    private int studentNum;
    private String studentName;
    private int phone_number;
    private int people_count;
}
