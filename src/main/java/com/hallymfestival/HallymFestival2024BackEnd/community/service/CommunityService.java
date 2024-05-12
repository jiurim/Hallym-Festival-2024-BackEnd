package com.hallymfestival.HallymFestival2024BackEnd.community.service;

import com.hallymfestival.HallymFestival2024BackEnd.community.entity.CommunityEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommunityService {
    List<CommunityEntity> getCommunityList();





}
