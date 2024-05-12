package com.hallymfestival.HallymFestival2024BackEnd.notice.domain;


import com.hallymfestival.HallymFestival2024BackEnd.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface NoticeRepository extends JpaRepository<Notice,Integer> {
}