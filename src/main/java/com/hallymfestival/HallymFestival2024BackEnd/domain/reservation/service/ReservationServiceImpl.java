package com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.service;

import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.dto.ReservationRequestDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.dto.ReservationSaveDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.entity.ReservationEntity;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    @Transactional
    public boolean insertReservation(ReservationSaveDto reservationSaveDto) {
        if (getReservationTotalCount() > 100) {
            throw new RuntimeException("예약이 가득 찼습니다");
        }
        ReservationEntity reservation = new ReservationEntity();
        reservation.setName(reservationSaveDto.getName());
        reservation.setStudentId(reservationSaveDto.getStudentId());
        reservation.setPeople_count(reservationSaveDto.getPeople_count());
        reservation.setPhone_number(reservationSaveDto.getPhone_number());
        reservation.setReg_date(LocalDateTime.now());
        reservationRepository.save(reservation);
        Long currentId = reservation.getId();
        if(currentId<=100){
            reservation.setSuccess(true);
            return true;
        }else {
            reservation.setSuccess(false);
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int getReservationTotalCount() {
        return reservationRepository.countBy();
    }

    @Override
    public ReservationRequestDto getReservationInfo(String studentId, String name) {
        ReservationEntity reservation = reservationRepository.findByStudentIdAndName(studentId, name);

        if (studentId != reservation.getStudentId()&& !name.equals(reservation.getName())) {
            throw new EntityNotFoundException("예약조회가 되지 않습니다.");
        }
        if(reservation.getId() > 100){
            throw new EntityNotFoundException("예약조회가 되지 않습니다.");
        }
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto();
        reservationRequestDto.setName(reservation.getName());
        reservationRequestDto.setStudentId(reservation.getStudentId());
        reservationRequestDto.setPhone_number(reservation.getPhone_number());
        reservationRequestDto.setPeople_count(reservation.getPeople_count());
        return reservationRequestDto;
    }
}