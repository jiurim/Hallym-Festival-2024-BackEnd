package com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.repository;

import com.hallymfestival.HallymFestival2024BackEnd.domain.reservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    int countBy();
    ReservationEntity findByStudentIdAndName(String studentId, String name);

    default ReservationEntity getById(Long id){
        return findById(id).orElseThrow(NoSuchElementException::new);
    }
}
