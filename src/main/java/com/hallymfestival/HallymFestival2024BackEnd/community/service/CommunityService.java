package com.hallymfestival.HallymFestival2024BackEnd.community.service;

import com.hallymfestival.HallymFestival2024BackEnd.community.dto.CommunityDto;
import com.hallymfestival.HallymFestival2024BackEnd.community.entity.CommunityEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommunityService {
    CommunityEntity insertCommunity(CommunityDto communityDto);
    List<CommunityEntity> getCommunityList();
    boolean isCorrectPassword(long id, String password);
    boolean deleteCommunity(long id);
    //관리자도 같은거 써서 삭제하면 됨
}
