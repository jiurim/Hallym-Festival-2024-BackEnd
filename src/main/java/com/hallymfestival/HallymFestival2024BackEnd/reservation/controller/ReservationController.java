package com.hallymfestival.HallymFestival2024BackEnd.reservation.controller;

import com.hallymfestival.HallymFestival2024BackEnd.reservation.dto.ReservationSaveDto;
import com.hallymfestival.HallymFestival2024BackEnd.reservation.entity.ReservationEntity;
import com.hallymfestival.HallymFestival2024BackEnd.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/reservation")
    public ResponseEntity<Integer> gerReservationTotalCount(){
        int totalCount =reservationService.getReservationTotalCount();
        return ResponseEntity.ok().body(totalCount);
    }

//    @PostMapping("/reservation")
//    public ResponseEntity<ReservationEntity> insertReservation(@RequestBody ReservationSaveDto reservationSaveDto){
//        ReservationEntity newReservation = reservationService.insertReservation(reservationSaveDto);
//
//        if (newReservation == null) {
//            return ResponseEntity.badRequest().body(null);
//        }
//
//        return ResponseEntity.ok().body(newReservation);
//    }

    @PostMapping("/reservation")
    public void insertReservation(@RequestBody ReservationSaveDto reservationSaveDto) {
        reservationService.insertReservation(reservationSaveDto);
    }

}
