package com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.service;

import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.dto.ReservationRequestDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.dto.ReservationSaveDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.entity.ReservationEntity;
import org.springframework.stereotype.Service;

@Service
public interface ReservationService {
    int getReservationTotalCount();

    ReservationEntity insertReservation(ReservationSaveDto reservationSaveDto);

    ReservationRequestDto getReservationInfo(String studentId, String name);
}
