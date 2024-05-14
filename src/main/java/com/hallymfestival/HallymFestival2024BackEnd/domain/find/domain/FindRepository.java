package com.hallymfestival.HallymFestival2024BackEnd.domain.find.domain;


import com.hallymfestival.HallymFestival2024BackEnd.domain.find.entity.FindEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FindRepository extends JpaRepository<FindEntity, Long> {
}


