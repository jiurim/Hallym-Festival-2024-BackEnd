package com.hallymfestival.HallymFestival2024BackEnd.find.domain;

import com.hallymfestival.HallymFestival2024BackEnd.find.entity.FindEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FindRepository extends JpaRepository<FindEntity, Long> {
}
