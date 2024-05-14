package com.hallymfestival.HallymFestival2024BackEnd.domain.community.repository;


import com.hallymfestival.HallymFestival2024BackEnd.domain.community.entity.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Long> {
    List<CommunityEntity> getCommunityEntityByDeleteYnIsFalse();
}
