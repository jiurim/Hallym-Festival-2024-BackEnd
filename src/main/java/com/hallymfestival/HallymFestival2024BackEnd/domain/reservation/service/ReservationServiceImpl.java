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
        // 총 예약 수 가져오기
        long totalReservations = reservationRepository.countBy();

        // 예약 총 수가 130명을 초과하는지 확인
        if (totalReservations > 130) {
                throw new RuntimeException("예약이 가득 찼습니다");
        }
        ReservationEntity reservation = new ReservationEntity();
        reservation.setStudentName(reservationSaveDto.getStudentName());
        reservation.setStudentNum(reservationSaveDto.getStudentNum());
        reservation.setPeople_count(reservationSaveDto.getPeople_count());
        reservation.setPhone_number(reservationSaveDto.getPhone_number());
        reservation.setReg_date(LocalDateTime.now());
        reservationRepository.save(reservation);
        Long currentId = reservation.getId();
        if (currentId <= 100) {
            reservation.setSuccess(true);
        } else {
            reservation.setSuccess(false);
        }
        reservationRepository.save(reservation);
        // 예약 성공 여부 반환
        return reservation.isSuccess();
    }

    @Override
    @Transactional(readOnly = true)
    public int getReservationTotalCount() {
        if(reservationRepository.countBy() > 130){
            return 130;
        }
        else {
            return reservationRepository.countBy();
        }
    }

    @Override
    public ReservationRequestDto getReservationInfo(int studentNum, String studentName) {
        ReservationEntity reservation = reservationRepository.findByStudentNumAndStudentName(studentNum, studentName);
        if (studentNum != reservation.getStudentNum() && !studentName.equals(reservation.getStudentName())) {
            throw new EntityNotFoundException("예약조회가 되지 않습니다.");
        }
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto();
        reservationRequestDto.setStudentName(reservation.getStudentName());
        reservationRequestDto.setStudentNum(reservation.getStudentNum());
        reservationRequestDto.setPhone_number(reservation.getPhone_number());
        reservationRequestDto.setPeople_count(reservation.getPeople_count());
        return reservationRequestDto;
    }
}