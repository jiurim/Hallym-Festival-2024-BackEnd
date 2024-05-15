package com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.service;

import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.dto.ReservationRequestDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.dto.ReservationSaveDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.entity.ReservationEntity;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.entity.ReservationLimit;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.repository.ReservationLimitRepository;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.Date;

//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class RerservationServiceImpl implements ReservationService

//    @Autowired
//    private ReservationRepository reservationRepository;
//
//    @Override
//    @Transactional(readOnly = true)
//    public int getReservationTotalCount() {
//        return reservationRepository.countBy();
//    }

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

//    @Override
//    public ReservationRequestDto getReservationInfo(String studentId, String name) {
//        ReservationEntity reservation = reservationRepository.findByStudentIdAndName(studentId, name);
//        log.info(studentId);
//        log.info(name);
//
//        if (studentId != reservation.getStudentId()&& !name.equals(reservation.getName())) {
//            log.info("디비에 없음");
//            throw new EntityNotFoundException("예약조회가 되지 않습니다.");
//        }
//
//        log.info("디비에 있음");
//        ReservationRequestDto reservationRequestDto = new ReservationRequestDto();
//        reservationRequestDto.setName(reservation.getName());
//        reservationRequestDto.setStudentId(reservation.getStudentId());
//        reservationRequestDto.setPhone_number(reservation.getPhone_number());
//        reservationRequestDto.setPeople_count(reservation.getPeople_count());
//
//        log.info("반환");
//        return reservationRequestDto;
//    }
//}
@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationLimitRepository reservationLimitRepository;

    @Override
    @Transactional(readOnly = true)
    public int getReservationTotalCount() {
        return reservationRepository.countBy();
    }

    @Override
    @Transactional
    public ReservationEntity insertReservation(ReservationSaveDto reservationSaveDto) {
        ReservationLimit reservationLimit = reservationLimitRepository.findByIdWithLock(1L);
        if (reservationLimit == null) {
            throw new RuntimeException("Reservation limit not set");
        }

        if (!reservationLimit.canReserve(reservationSaveDto.getPeople_count())) {
            throw new RuntimeException("Reservation limit exceeded");
        }

        reservationLimit.reserve(reservationSaveDto.getPeople_count());
        reservationLimitRepository.save(reservationLimit);

        ReservationEntity reservation = new ReservationEntity();
        reservation.setName(reservationSaveDto.getName());
        reservation.setStudentId(reservationSaveDto.getStudentId());
        reservation.setPeople_count(reservationSaveDto.getPeople_count());
        reservation.setPhone_number(reservationSaveDto.getPhone_number());
        reservation.setReg_date(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }

    @Override
    public ReservationRequestDto getReservationInfo(String studentId, String name) {
        ReservationEntity reservation = reservationRepository.findByStudentIdAndName(studentId, name);
        log.info(studentId);
        log.info(name);

        if (reservation == null || !name.equals(reservation.getName())) {
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