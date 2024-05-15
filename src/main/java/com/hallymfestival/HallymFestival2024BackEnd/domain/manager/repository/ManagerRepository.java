package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.repository;

import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByUsername(String username);

    boolean existsByUsername(String username);
}
