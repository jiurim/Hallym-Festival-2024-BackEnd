package com.hallymfestival.HallymFestival2024BackEnd.notice.domain;


import com.hallymfestival.HallymFestival2024BackEnd.notice.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Integer> {
}
