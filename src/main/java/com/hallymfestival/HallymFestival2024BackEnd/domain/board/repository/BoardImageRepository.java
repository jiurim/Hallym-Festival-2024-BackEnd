package com.hallymfestival.HallymFestival2024BackEnd.domain.board.repository;

import com.hallymfestival.HallymFestival2024BackEnd.domain.board.entity.BoardImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardImageRepository extends JpaRepository<BoardImageEntity, Long> {

    BoardImageEntity findByImageUrl(String imageUrl);
}
