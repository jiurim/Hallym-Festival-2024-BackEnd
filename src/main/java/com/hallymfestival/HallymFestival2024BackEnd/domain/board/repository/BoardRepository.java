package com.hallymfestival.HallymFestival2024BackEnd.domain.board.repository;

import com.hallymfestival.HallymFestival2024BackEnd.domain.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
