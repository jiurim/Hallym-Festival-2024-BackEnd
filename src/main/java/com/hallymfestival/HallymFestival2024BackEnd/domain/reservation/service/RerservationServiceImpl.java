package com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.service;

import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.dto.ReservationSaveDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.entity.ReservationEntity;
import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RerservationServiceImpl implements ReservationService{

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public int getReservationTotalCount() {
        return reservationRepository.countBy();
    }

    @Override
    @Transactional
    public ReservationEntity insertReservation(ReservationSaveDto reservationSaveDto) {
        if(this.getReservationTotalCount() > 100) {
            return null;
        }

        ReservationEntity reservation = new ReservationEntity();
        reservation.setName(reservationSaveDto.getName());
        reservation.setStudent_id(reservationSaveDto.getStudent_id());
        reservation.setPeople_count(reservationSaveDto.getPeople_count());
        reservation.setPhone_number(reservationSaveDto.getPhone_number());
        reservation.setReg_date(new Date());

        return reservationRepository.save(reservation);
    }
}
