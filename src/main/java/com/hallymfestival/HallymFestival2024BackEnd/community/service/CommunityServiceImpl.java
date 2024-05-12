package com.hallymfestival.HallymFestival2024BackEnd.community.service;

import com.hallymfestival.HallymFestival2024BackEnd.community.dto.CommunityDto;
import com.hallymfestival.HallymFestival2024BackEnd.community.entity.CommunityEntity;
import com.hallymfestival.HallymFestival2024BackEnd.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService{
    private final CommunityRepository communityRepository;

    @Override
    public CommunityEntity insertCommunity(CommunityDto communityDto) {
        CommunityEntity communityEntity = new CommunityEntity();
        communityEntity.setPassword(communityDto.getPassword());
        communityEntity.setContent(communityDto.getContent());
        communityEntity.setDeleteYn(false);
        communityEntity.setDate(new Date());

        return communityRepository.save(communityEntity);
    }

    @Override
    public List<CommunityEntity> getCommunityList() {
        return communityRepository.getCommunityEntityByDeleteYnIsFalse();
    }

    @Override
    public boolean isCorrectPassword(long id, String password) {
        CommunityEntity community = communityRepository.findById(id).get();
        String communityPassword = community.getPassword();

        if (StringUtils.equals(password, communityPassword)) {
            // 일치하면 true, 실패 시 false 반환
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteCommunity(long id) {
        CommunityEntity originCommunity = communityRepository.findById(id).get();
        originCommunity.setDeleteYn(true);

        CommunityEntity community = communityRepository.save(originCommunity);

        // 삭제로 저장한 커뮤니티가 삭제가 정상적으로 되었을경우 (deleteYn = true) 일경우
        // true 반환, 아닐경우 false반환
        if (community.isDeleteYn()) {
            return true;
        }

        return false;
    }
}
