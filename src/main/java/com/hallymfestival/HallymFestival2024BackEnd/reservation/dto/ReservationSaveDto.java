package com.hallymfestival.HallymFestival2024BackEnd.reservation.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ReservationSaveDto {
    private int student_id;
    private String name;
    private String phone_number;
    private int people_count;
}
