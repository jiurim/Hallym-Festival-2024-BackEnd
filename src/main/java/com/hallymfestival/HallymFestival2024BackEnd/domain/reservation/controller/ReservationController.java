package com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.controller;


import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.dto.ReservationRequestDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.dto.ReservationSaveDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;


    @GetMapping("/reservationDetail")
    public ResponseEntity<Integer> getReservationTotalCount(){
        int totalCount =reservationService.getReservationTotalCount();
        return ResponseEntity.ok().body(totalCount);
    }


    @GetMapping("/reservationInfo")

    public ResponseEntity<ReservationRequestDto> getReservationInfo(@RequestBody ReservationRequestDto requestDto) {
        ReservationRequestDto reservationInfo;
//        try {
            reservationInfo = reservationService.getReservationInfo(requestDto.getStudentId(), requestDto.getName());
//        }
//        catch (EntityNotFoundException e) {
//            return ResponseEntity.badRequest().body();
//        }
        return ResponseEntity.ok().body(reservationInfo);
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

    @PostMapping("/reservationDetail")
    public void insertReservation(@RequestBody ReservationSaveDto reservationSaveDto) {
        reservationService.insertReservation(reservationSaveDto);
    }

}