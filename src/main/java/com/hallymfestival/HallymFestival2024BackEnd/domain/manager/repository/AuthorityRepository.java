package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.repository;


import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.entity.Authority;
import com.hallymfestival.HallymFestival2024BackEnd.domain.manager.entity.AuthorityEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, AuthorityEnum> {
    Optional<Authority> findByAuthorityStatus(AuthorityEnum authorityStatus);
}