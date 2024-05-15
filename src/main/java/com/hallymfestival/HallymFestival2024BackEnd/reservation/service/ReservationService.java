package com.hallymfestival.HallymFestival2024BackEnd.reservation.service;


//import com.hallymfestival.HallymFestival2024BackEnd.reservation.dto.ReservationRequestDto;
import com.hallymfestival.HallymFestival2024BackEnd.reservation.dto.ReservationSaveDto;
import com.hallymfestival.HallymFestival2024BackEnd.reservation.entity.ReservationEntity;
import org.springframework.stereotype.Service;

@Service
public interface ReservationService {
    int getReservationTotalCount();

    ReservationEntity insertReservation(ReservationSaveDto reservationSaveDto);

//    ReservationRequestDto getReservationInfo(String studentId, String name);

}
