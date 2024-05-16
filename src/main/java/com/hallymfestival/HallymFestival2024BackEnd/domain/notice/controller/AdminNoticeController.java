package com.hallymfestival.HallymFestival2024BackEnd.domain.notice.controller;

import com.hallymfestival.HallymFestival2024BackEnd.domain.notice.dto.NoticeDto;
import com.hallymfestival.HallymFestival2024BackEnd.domain.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin/notice")
@RestController
public class AdminNoticeController {
    private final NoticeService noticeService;

    @Autowired
    public AdminNoticeController(NoticeService noticeService){
        this.noticeService=noticeService;
    }

    @PostMapping
    public ResponseEntity<NoticeDto> createNotice(@RequestBody NoticeDto noticeDto){
        NoticeDto newNotice = noticeService.createNotice(noticeDto);
        return ResponseEntity.ok().body(newNotice);
    }

    @GetMapping
    public ResponseEntity<List<NoticeDto>> getNotice(){
        List<NoticeDto> notices = noticeService.getNotice();
        return ResponseEntity.ok().body(notices);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoticeDto> update(@PathVariable Integer id, @RequestBody NoticeDto noticeDto){
        NoticeDto updatedNotice = noticeService.update(id, noticeDto);
        return ResponseEntity.ok().body(updatedNotice);
    }
}