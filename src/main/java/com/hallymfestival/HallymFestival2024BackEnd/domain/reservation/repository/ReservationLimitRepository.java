package com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.repository;

import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.entity.ReservationLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

public interface ReservationLimitRepository extends JpaRepository<ReservationLimit, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM ReservationLimit r WHERE r.id = :id")
    ReservationLimit findByIdWithLock(@Param("id") Long id);
}