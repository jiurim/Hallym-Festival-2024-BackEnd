package com.hallymfestival.HallymFestival2024BackEnd.domain.community.service;


import com.hallymfestival.HallymFestival2024BackEnd.domain.community.dto.CommunityDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.community.entity.CommunityEntity;
import com.hallymfestival.HallymFestival2024BackEnd.domain.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;

    @Override
    public CommunityEntity insertCommunity(CommunityDto communityDto) {
        CommunityEntity communityEntity = new CommunityEntity();
        log.info("들어옴");
        communityEntity.setPassword(communityDto.getPassword());
        log.info("비밀번호 확인");
        communityEntity.setContent(communityDto.getContent());
        log.info("내용확인");
        communityEntity.setDeleteYn(false);
        log.info("삭제여부 확인");
        communityEntity.setDate(LocalDateTime.now());
        log.info("작성날짜 확인");
        communityEntity.setNickname(communityDto.getNickname());
        return communityRepository.save(communityEntity);
    }

    @Override
    public List<CommunityEntity> getCommunityList() {
        return communityRepository.getCommunityEntityByDeleteYnIsFalse();
    }

    @Override
    public boolean isCorrectPassword(Long id, String password) {
        //nullpointException을 방지하기 위해 null을 다루는 optional 사용
        Optional<CommunityEntity> optionalCommunity = communityRepository.findById(id);
        if (optionalCommunity.isPresent()) {
            CommunityEntity community = optionalCommunity.get();
            //값이 없을 때 nosuchException 발생 할 수 있음 예외처리 필요함
            log.info("입력된 비밀번호: " + password);
            String savedPassword = community.getPassword();
            log.info("저장된 비밀번호: " + savedPassword);
            if (savedPassword.equals(password)) {
                // 일치하면 true 반환
                log.info("일치");
                return true;
            } else {
                log.info("불일치");
                return false;
            }
        }
        return true;
    }

     @Override
    public boolean deleteCommunity(long id, String password) {
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

    @Override
    public boolean delteAdminCommunity(long id) {
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


//        if (optionalCommunity.isPresent()) {
//            CommunityEntity community = optionalCommunity.get();
//            //값이 없을 때 nosuchException 발생 할 수 있음 예외처리 필요함
//            String communityPassword = community.getPassword();
//            log.info("입력된 비밀번호: " + password);
//            log.info("저장된 비밀번호: " + communityPassword);
//            if (password.equalsIgnoreCase(communityPassword)) {
//                // 일치하면 true 반환
//                log.info("일치");
//                return true;
//            }
//            return false;
//        }
//        return false;
