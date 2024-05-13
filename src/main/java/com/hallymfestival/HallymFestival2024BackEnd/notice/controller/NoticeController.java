package com.hallymfestival.HallymFestival2024BackEnd.notice.controller;

import com.hallymfestival.HallymFestival2024BackEnd.notice.dto.NoticeDto;
import com.hallymfestival.HallymFestival2024BackEnd.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/notice")
@RestController
public class NoticeController {
    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService){
        this.noticeService=noticeService;
    }

    @PostMapping  // POST 요청을 처리하는 메서드
    public ResponseEntity<NoticeDto> createNotice(@RequestBody NoticeDto noticeDto){
        NoticeDto newNotice = noticeService.createNotice(noticeDto);
        return ResponseEntity.ok().body(newNotice);
    }

    @GetMapping  // GET 요청을 처리하는 메서드
    public ResponseEntity<List<NoticeDto>> getNotice(){
        List<NoticeDto> notices = noticeService.getNotice();
        return ResponseEntity.ok().body(notices);
    }

    @PutMapping("/{id}")  // PUT 요청을 처리하는 메서드
    public ResponseEntity<NoticeDto> update(@PathVariable Integer id, @RequestBody NoticeDto noticeDto){
        NoticeDto updatedNotice = noticeService.update(id, noticeDto);
        return ResponseEntity.ok().body(updatedNotice);
    }
}