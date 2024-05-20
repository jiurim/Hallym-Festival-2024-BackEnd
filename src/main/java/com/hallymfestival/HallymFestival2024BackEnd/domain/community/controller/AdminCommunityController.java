package com.hallymfestival.HallymFestival2024BackEnd.domain.community.controller;

import com.hallymfestival.HallymFestival2024BackEnd.domain.community.dto.CommunityDetailDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.community.dto.CommunityDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.community.dto.CommunityResponseDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.community.entity.CommunityEntity;
import com.hallymfestival.HallymFestival2024BackEnd.domain.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/community")
public class AdminCommunityController {

    private final CommunityService communityService;


    @GetMapping
    @CrossOrigin(origins = "https://hallym-festival-admin.com", maxAge = 3600)
    public ResponseEntity<List<CommunityDetailDto>> getCommunityList() {
        List<CommunityDetailDto> communityList = communityService.getCommunityList();

        return ResponseEntity.ok().body(communityList);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "https://hallym-festival-admin.com", maxAge = 3600)
    public ResponseEntity<CommunityResponseDto> removeCommunity(@PathVariable long id) {
        CommunityResponseDto response = new CommunityResponseDto();

        boolean isDelete = communityService.delteAdminCommunity(id);
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