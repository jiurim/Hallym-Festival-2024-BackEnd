package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.repository;

import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken , String> {
    Optional<RefreshToken> findByKey(String key);
    Optional<RefreshToken> deleteByKey(String key);
}