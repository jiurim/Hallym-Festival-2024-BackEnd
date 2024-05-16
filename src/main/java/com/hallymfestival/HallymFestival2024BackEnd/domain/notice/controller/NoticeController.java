package com.hallymfestival.HallymFestival2024BackEnd.domain.notice.controller;

import com.hallymfestival.HallymFestival2024BackEnd.domain.notice.dto.NoticeDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NoticeController {
    private final NoticeService noticeService;
    @Autowired
    public NoticeController(NoticeService noticeService){
        this.noticeService=noticeService;
    }

    @GetMapping("/api/notice")
    public ResponseEntity<List<NoticeDto>> getNotice(){
        List<NoticeDto> notices = noticeService.getNotice();
        return ResponseEntity.ok().body(notices);
    }
}