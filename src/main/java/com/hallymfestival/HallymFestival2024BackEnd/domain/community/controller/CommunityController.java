package com.hallymfestival.HallymFestival2024BackEnd.domain.community.controller;

import com.hallymfestival.HallymFestival2024BackEnd.domain.community.dto.CommunityDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.community.dto.CommunityResponseDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.community.entity.CommunityEntity;
import com.hallymfestival.HallymFestival2024BackEnd.domain.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping
    public ResponseEntity<List<CommunityEntity>> getCommunityList() {
        List<CommunityEntity> communityList = communityService.getCommunityList();

        return ResponseEntity.ok().body(communityList);
    }

    @PostMapping
    public ResponseEntity<CommunityEntity> insertCommunity(@RequestBody CommunityDto communityDto) {
        CommunityEntity newCommunity = communityService.insertCommunity(communityDto);

        return ResponseEntity.ok().body(newCommunity);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<CommunityResponseDto> removeCommunity(@PathVariable long id, @RequestParam String password) {
//        // 1차 패스워드 일치 여부 확인.
//        boolean isOk = communityService.isCorrectPassword(id, password);
//        CommunityResponseDto response = new CommunityResponseDto();
//
//        if (!isOk) {
//            response.setCode("401");
//            response.setMessage("비밀번호가 일치하지 않습니다.");
//            return ResponseEntity.badRequest().body(response);
//        }
//
//        boolean isDelete = communityService.deleteCommunity(id, password);
//        //boolean isDelete = true;
//
//        if (!isDelete) {
//            response.setCode("400");
//            response.setMessage("게시글 삭제에 실패하였습니다.");
//            return ResponseEntity.badRequest().body(response);
//        }
//
//        response.setCode("200");
//        response.setMessage("성공");
//        return ResponseEntity.ok().body(response);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommunityResponseDto> removeCommunity(@PathVariable long id, @RequestParam String password) {
        // 1차 패스워드 일치 여부 확인.
        boolean isOk = communityService.isCorrectPassword(id, password);
        CommunityResponseDto response = new CommunityResponseDto();

        if (!isOk) {
            response.setCode("401");
            response.setMessage("비밀번호가 일치하지 않습니다.");
            return ResponseEntity.badRequest().body(response);
        }

        boolean isDelete = communityService.deleteCommunity(id, password);
        //boolean isDelete = true;

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
