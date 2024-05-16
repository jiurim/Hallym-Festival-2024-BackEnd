package com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.service;

import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.dto.ReservationRequestDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.dto.ReservationSaveDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.entity.ReservationEntity;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class RerservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

//    @Override
//    @Transactional
//    public ReservationEntity insertReservation(ReservationSaveDto reservationSaveDto) {
//        if (getReservationTotalCount() > 100) {
//            throw new RuntimeException("Reservation limit exceeded");
//        }
//
//        ReservationEntity reservation = new ReservationEntity();
//        reservation.setName(reservationSaveDto.getName());
//        reservation.setStudentId(reservationSaveDto.getStudentId());
//        reservation.setPeople_count(reservationSaveDto.getPeople_count());
//        reservation.setPhone_number(reservationSaveDto.getPhone_number());
//        reservation.setReg_date(LocalDateTime.now());
//
//        return reservationRepository.save(reservation);
//    }

    @Override
    @Transactional
    public boolean insertReservation(ReservationSaveDto reservationSaveDto) {
        if (getReservationTotalCount() > 100) {
            throw new RuntimeException("Reservation limit exceeded");
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
            return false;
        }else {
            return true;
        }
    }


    @Override
    @Transactional(readOnly = true)
    public int getReservationTotalCount() {
        return reservationRepository.countBy();
    }

    @Transactional
    public void decrease(Long id){
        ReservationEntity reservationEntity = reservationRepository.getById(id);
        reservationEntity.decrease();
        reservationRepository.saveAndFlush(reservationEntity);
    }

    @Override
    public ReservationRequestDto getReservationInfo(String studentId, String name) {
        ReservationEntity reservation = reservationRepository.findByStudentIdAndName(studentId, name);
        log.info(studentId);
        log.info(name);

        if (studentId != reservation.getStudentId()&& !name.equals(reservation.getName())) {
            log.info("디비에 없음");
            throw new EntityNotFoundException("예약조회가 되지 않습니다.");
        }

        log.info("디비에 있음");
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto();
        reservationRequestDto.setName(reservation.getName());
        reservationRequestDto.setStudentId(reservation.getStudentId());
        reservationRequestDto.setPhone_number(reservation.getPhone_number());
        reservationRequestDto.setPeople_count(reservation.getPeople_count());

        log.info("반환");
        return reservationRequestDto;
    }
}