package com.hallymfestival.HallymFestival2024BackEnd.community.controller;

import com.hallymfestival.HallymFestival2024BackEnd.community.dto.CommunityDto;
import com.hallymfestival.HallymFestival2024BackEnd.community.dto.CommunityResponseDto;
import com.hallymfestival.HallymFestival2024BackEnd.community.entity.CommunityEntity;
import com.hallymfestival.HallymFestival2024BackEnd.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;gi
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;
    // 공지사항에서 get 할때 리스츠로 반환 저장 수정 삭제는 하나하나 하게끔

    @GetMapping("/community")
    public ResponseEntity<List<CommunityEntity>> getCommunityList() {
        List<CommunityEntity> communityList = communityService.getCommunityList();

        return ResponseEntity.ok().body(communityList);
    }

    @PostMapping("/community")
    public ResponseEntity<CommunityEntity> insertCommunity(@RequestBody CommunityDto communityDto) {
        CommunityEntity newCommunity = communityService.insertCommunity(communityDto);

        return ResponseEntity.ok().body(newCommunity);
    }

    @DeleteMapping("/community/{id}")
    public ResponseEntity<CommunityResponseDto> removeCommunity(@PathVariable long id, @RequestBody String password) {
        // 1차 패스워드 일치 여부 확인.
        boolean isOk = communityService.isCorrectPassword(id, password);
        CommunityResponseDto response = new CommunityResponseDto();

        if (!isOk) {
            response.setCode("401");
            response.setMessage("비밀번호가 일치하지 않습니다.");
            return ResponseEntity.badRequest().body(response);
        }

        boolean isDelete = communityService.deleteCommunity(id);

        if (!isDelete) {
            response.setCode("400");
            response.setMessage("게시글 삭제에 실패하였습니다.");
            return ResponseEntity.badRequest().body(response);
        }

        response.setCode("200");
        response.setMessage("성공");
        return ResponseEntity.ok().body(response);
    }

}
