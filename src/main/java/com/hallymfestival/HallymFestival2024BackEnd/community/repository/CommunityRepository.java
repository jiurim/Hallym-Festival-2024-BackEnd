package com.hallymfestival.HallymFestival2024BackEnd.community.repository;

import com.hallymfestival.HallymFestival2024BackEnd.community.entity.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity , Long> {
}
