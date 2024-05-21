package com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.controller;


import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.dto.ReservationRequestDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.dto.ReservationSaveDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;

    // 예약정보 불러오기
    @GetMapping("/reservationdetail")
    public ResponseEntity<Integer> getReservationTotalCount(){
        int totalCount =reservationService.getReservationTotalCount();
        return ResponseEntity.ok().body(totalCount);
    }

    // 예약성공 확인하기
    @PostMapping("/reservationinfo")
    public ResponseEntity<ReservationRequestDto> getReservationInfo(@RequestBody ReservationRequestDto requestDto) {
        ReservationRequestDto reservationInfo;
        reservationInfo = reservationService.getReservationInfo(requestDto.getStudentNum(), requestDto.getStudentName());
        return ResponseEntity.ok().body(reservationInfo);
    }

    // 예약 신청하기
    @PostMapping("/reservationdetail")
    public ResponseEntity<Boolean> insertReservation(@RequestBody ReservationSaveDto reservationSaveDto) {
        boolean result = reservationService.insertReservation(reservationSaveDto);
        Boolean resultObject = Boolean.valueOf(result);
        if (result) {
            return ResponseEntity.ok(resultObject);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultObject);
        }
    }

}
