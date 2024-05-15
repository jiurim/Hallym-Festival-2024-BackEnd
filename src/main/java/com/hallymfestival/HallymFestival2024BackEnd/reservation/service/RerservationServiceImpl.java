package com.hallymfestival.HallymFestival2024BackEnd.reservation.service;


//import com.hallymfestival.HallymFestival2024BackEnd.reservation.dto.ReservationRequestDto;
import com.hallymfestival.HallymFestival2024BackEnd.reservation.dto.ReservationSaveDto;
import com.hallymfestival.HallymFestival2024BackEnd.reservation.entity.ReservationEntity;
import com.hallymfestival.HallymFestival2024BackEnd.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RerservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    @Transactional(readOnly = true)
    public int getReservationTotalCount() {
        return reservationRepository.countBy();
    }

    @Override
    @Transactional
    public ReservationEntity insertReservation(ReservationSaveDto reservationSaveDto) {
        if (getReservationTotalCount() > 100) {
            throw new RuntimeException("Reservation limit exceeded");
        }

        ReservationEntity reservation = new ReservationEntity();
        reservation.setName(reservationSaveDto.getName());
        reservation.setStudent_id(reservationSaveDto.getStudent_id());
        reservation.setPeople_count(reservationSaveDto.getPeople_count());
        reservation.setPhone_number(reservationSaveDto.getPhone_number());
        reservation.setReg_date(new Date());

        return reservationRepository.save(reservation);
    }

//    @Override
//    public ReservationRequestDto getReservationInfo(String studentId, String name) {
//        ReservationEntity reservation = reservationRepository.findReservationInfo(studentId, name);
//
//        if (!reservation.getId().equals(studentId)) {
//            throw new EntityNotFoundException(studentId + "," + name +"예약조회가 되지 않습니다.");
//        }
//
//        ReservationRequestDto reservationRequestDto = new ReservationRequestDto();
//        reservationRequestDto.setName(reservation.getName());
//        reservationRequestDto.setStudent_id(reservation.getStudent_id());
//        reservationRequestDto.setPhone_number(reservation.getPhone_number());
//        reservationRequestDto.setPeople_count(reservation.getPeople_count());
//
//        return reservationRequestDto;
//    }
}