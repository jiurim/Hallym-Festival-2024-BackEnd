package com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.repository;

import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    int countBy();

}
