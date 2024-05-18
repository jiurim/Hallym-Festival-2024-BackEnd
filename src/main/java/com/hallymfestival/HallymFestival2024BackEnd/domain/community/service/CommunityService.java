package com.hallymfestival.HallymFestival2024BackEnd.domain.community.service;

import com.hallymfestival.HallymFestival2024BackEnd.domain.community.dto.CommunityDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.community.entity.CommunityEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommunityService {
    CommunityEntity insertCommunity(CommunityDto communityDto);
    List<CommunityEntity> getCommunityList();
    boolean isCorrectPassword(Long id, String password);
    boolean deleteCommunity(long id, String password);
    //관리자도 같은거 써서 삭제하면 됨

    boolean delteAdminCommunity(long id);
}
