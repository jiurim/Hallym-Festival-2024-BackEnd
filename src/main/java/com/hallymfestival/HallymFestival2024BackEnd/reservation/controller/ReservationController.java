package com.hallymfestival.HallymFestival2024BackEnd.reservation.controller;

//import com.hallymfestival.HallymFestival2024BackEnd.reservation.dto.ReservationRequestDto;
import com.hallymfestival.HallymFestival2024BackEnd.reservation.dto.ReservationSaveDto;
import com.hallymfestival.HallymFestival2024BackEnd.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/reservationDetail")
    public ResponseEntity<Integer> getReservationTotalCount(){
        int totalCount =reservationService.getReservationTotalCount();
        return ResponseEntity.ok().body(totalCount);
    }

//    @GetMapping("/reservationInfo")
//    public ResponseEntity<ReservationRequestDto> getReservationInfo(
//            @RequestParam(value = "studentId") String studentId,
//            @RequestParam(value = "name") String name) {
//        ReservationRequestDto reservationInfo = reservationService.getReservationInfo(studentId, name);
//        return ResponseEntity.ok().body(reservationInfo);
//    }


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
