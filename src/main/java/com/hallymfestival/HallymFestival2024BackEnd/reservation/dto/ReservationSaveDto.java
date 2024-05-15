package com.hallymfestival.HallymFestival2024BackEnd.reservation.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ReservationSaveDto {
    private String studentId;
    private String name;
    private int phone_number;
    private int people_count;
}
